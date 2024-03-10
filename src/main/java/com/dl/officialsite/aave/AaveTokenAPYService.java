package com.dl.officialsite.aave;

import static org.web3j.tx.gas.DefaultGasProvider.GAS_LIMIT;
import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

import com.dl.officialsite.config.ChainInfo;
import com.dl.officialsite.config.Web3jAutoConfiguration;
import com.dl.officialsite.contract.ipool.IPool;
import com.dl.officialsite.contract.ipooladdressesprovider.IPoolAddressesProvider;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

/**
 * @ClassName AaveTokenAPYInfo
 * @Author jackchen
 * @Date 2024/3/6 20:38
 * @Description get aave token info apy
 **/
@Slf4j(topic = "Aave")
@Service
public class AaveTokenAPYService extends AbstractTokenAPY {

    public static ContractGasProvider GAS_PROVIDER = new StaticGasProvider(GAS_PRICE, GAS_LIMIT);

    BigInteger e16 = new BigInteger("10000000000000000");

    BigInteger e8 = new BigInteger("100000000");

    BigInteger E23 = new BigInteger("100000000000000000000000");
    static ConcurrentHashMap<ChainInfo, Web3j> chainWithWeb3j = Web3jAutoConfiguration.web3jMap;

    private final TokenAPYInfoRepository tokenAPYInfoRepository;

    public AaveTokenAPYService(TokenAPYInfoRepository tokenAPYInfoRepository) {
        this.tokenAPYInfoRepository = tokenAPYInfoRepository;
    }

    @Override
    public List<TokenAPYInfo> queryTokenApy() {
        return tokenAPYInfoRepository.findAll();
    }

    @Override
    public HealthInfo getHealthInfo(ChainInfo chainInfo, String address) {
        try {
            AtomicReference<HealthInfo> healthInfo = new AtomicReference<>();
            chainWithWeb3j.forEach((chain, web3j) -> {
                if (chain.getId().equals(chainInfo.getId())) {
                    log.info("chain: {}", chain.getName());
                    IPoolAddressesProvider addressesProvider = IPoolAddressesProvider.load(
                        chain.getLendingPoolAddressProviderV3Address(), web3j, getCredentials(), GAS_PROVIDER);
                    String poolAddress = null;
                    try {
                        poolAddress = addressesProvider.getPool().send();
                    } catch (Exception e) {
                        log.error("get pool address error: {}, IPoolAddressesProviderAddress{}",
                            chain.getLendingPoolAddressProviderV3Address(), e.getMessage());
                        throw new RuntimeException(e);
                    }
                    log.info("poolAddress is : {}", poolAddress);
                    IPool pool = IPool.load(poolAddress, web3j, getCredentials(), GAS_PROVIDER);
                    try {
                        Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> info =
                            pool.getUserAccountData(address).send();
                        BigInteger totalCollateralBase = info.component1();
                        BigInteger totalDebtBase = info.component2();
                        BigInteger ltv = info.component5();
                        BigInteger healthFactor = info.component6();
                        healthInfo.set(HealthInfo.builder()
                            .healthFactor(healthFactor)
                            .totalBorrows(totalDebtBase.toString())
                            .totalCollateralETH(totalCollateralBase.toString())
                            .totalLiquidity(ltv.toString())
                            .build());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return healthInfo.get();
        } catch (Exception e) {
            log.error("getHealthInfo error: " + e.getMessage());
            throw new RuntimeException("get health Info error");
        }
    }

    public List<ChainInfo> queryChainList() {
        return new ArrayList<>(Web3jAutoConfiguration.web3jMap.keySet());
    }

    @Scheduled(cron =  "${jobs.defi.corn: 0 30 * * * * ?}")
    public void updateTokenAPYInfo()  {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        log.info("update token info task begin --------------------- ");
        log.info("now date {}", LocalDateTime.now().format(formatter));
        tokenAPYInfoRepository.deleteAll();
        List<TokenAPYInfo> tokenAPYInfoList = queryTokenApyOnChain();
        tokenAPYInfoRepository.saveAll(tokenAPYInfoList);
    }

    public List<TokenAPYInfo> queryTokenApyOnChain() {
        List<TokenAPYInfo> tokenAPYInfoList = new ArrayList<>();
        try {
            chainWithWeb3j.forEach((chain, web3j) -> {
                log.info("chain: {}", chain.getName());
                IPoolAddressesProvider addressesProvider =
                    IPoolAddressesProvider.load(chain.getLendingPoolAddressProviderV3Address(), web3j,
                    getCredentials(), GAS_PROVIDER);
                String poolAddress = null;
                try {
                    poolAddress = addressesProvider.getPool().send();
                } catch (Exception e) {
                    log.error("get pool address error: {}, IPoolAddressesProviderAddress{}",
                        chain.getLendingPoolAddressProviderV3Address(), e.getMessage());
                    throw new RuntimeException(e);
                }
                log.info("poolAddress is : {}", poolAddress);
                IPool pool = IPool.load(poolAddress, web3j, getCredentials(), GAS_PROVIDER);
                chain.getTokens().forEach(token -> {
                    log.info("tokenName: {} tokenAddress: {}", token.getName(), token.getAddress());
                    try {
                        IPool.ReserveData reserveData = pool.getReserveData(token.getAddress()).send();
                        log.info("{} variable deposit interest: {}", token.getName(), reserveData.currentVariableBorrowRate.divide(E23).floatValue() / 100);
                        String tokenApy = reserveData.currentLiquidityRate.divide(E23).floatValue() / 100 + "%";
                        TokenAPYInfo tokenAPYInfo = new TokenAPYInfo();
                        tokenAPYInfo.setTokenName(token.getName());
                        tokenAPYInfo.setTokenAddress(token.getAddress());
                        tokenAPYInfo.setChainName(chain.getName());
                        tokenAPYInfo.setTokenApy(tokenApy);
                        tokenAPYInfo.setProtocol("Aave");
                        tokenAPYInfoList.add(tokenAPYInfo);
                    } catch (Exception e) {
                        log.error("get reserve data error: {}", e.getMessage());
                        throw new RuntimeException("get token apy error", e);
                    }
                });
            });
        } catch (Exception e) {
            log.error("queryTokenApyOnChain error: {}", e.getMessage());
            throw new RuntimeException("query token apy error", e);
        }
        return tokenAPYInfoList;
    }

    private Credentials getCredentials()   {
        return Credentials.create("1");
    }
}
