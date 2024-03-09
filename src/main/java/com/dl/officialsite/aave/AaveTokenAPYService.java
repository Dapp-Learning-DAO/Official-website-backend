package com.dl.officialsite.aave;

import static org.web3j.tx.gas.DefaultGasProvider.GAS_LIMIT;
import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

import com.dl.officialsite.contract.ipool.IPool;
import com.dl.officialsite.contract.ipooladdressesprovider.IPoolAddressesProvider;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
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
    static Map<String, Map<String, String>> chainWithToken = new HashMap<>();
    static {
        Map<String, String> optimismTokenMap = new HashMap<>();
        optimismTokenMap.put("usdc", "0x2791Bca1f2de4661ED88A30C99A7a9449Aa84174");
        optimismTokenMap.put("usdt", "0xc2132D05D31c914a87C6611C10748AEb04B58e8F");
        optimismTokenMap.put("dai", "0x8f3Cf7ad23Cd3CaDbD9735AFf958023239c6A063");
        optimismTokenMap.put("eth", "0x7ceB23fD6bC0adD59E62ac25578270cFf1b9f619");
        optimismTokenMap.put("btc", "0x1BFD67037B42Cf73acF2047067bd4F2C47D9BfD6");
        chainWithToken.put("optimism", optimismTokenMap);
    }


    private final Web3j web3j;

    private final Credentials credentials;

    private final IPoolAddressesProvider poolAddressesProvider;

    public AaveTokenAPYService(Web3j web3j, Credentials credentials,
        IPoolAddressesProvider poolAddressesProvider) {
        this.web3j = web3j;
        this.credentials = credentials;
        this.poolAddressesProvider = poolAddressesProvider;
    }

    @Override
    public List<TokenAPYInfo> queryTokenApy() {
        List<TokenAPYInfo> tokenAPYInfoList = new ArrayList<>();
        try {
            String poolAddress = poolAddressesProvider.getPool().send();
            log.info("poolAddress is : {}", poolAddress);
            IPool pool = IPool.load(poolAddress, web3j, credentials, GAS_PROVIDER);
            chainWithToken.forEach((chain, tokenMap) -> {
                tokenMap.forEach((tokenName, tokenAddress) -> {
                    log.info("chain: {} tokenName: {} tokenAddress: {}", chain, tokenName, tokenAddress);
                    try {
                        IPool.ReserveData reserveData = pool.getReserveData(tokenAddress).send();
                        log.info("{} variable deposit interest: {}", tokenName, reserveData.currentVariableBorrowRate.divide(E23).floatValue() / 100);
                        String tokenApy = reserveData.currentLiquidityRate.divide(E23).floatValue() / 100 + "%";
                        TokenAPYInfo tokenAPYInfo = new TokenAPYInfo();
                        tokenAPYInfo.setTokenName(tokenName);
                        tokenAPYInfo.setTokenAddress(tokenAddress);
                        tokenAPYInfo.setChainName(chain);
                        tokenAPYInfo.setTokenApy(tokenApy);
                        tokenAPYInfoList.add(tokenAPYInfo);
                    } catch (Exception e) {
                        log.error("get reserve data error: {}", e.getMessage());
                        throw new RuntimeException("get token apy error", e);
                    }
                });
            });
        } catch (Exception e) {
            log.error("get pool address error: {}", e.getMessage());
            throw new RuntimeException("get token apy error", e);
        }
        return tokenAPYInfoList;
    }

    @Override
    public HealthInfo getHealthInfo(String address) {
        try {
            String poolAddress = poolAddressesProvider.getPool().send();
            log.info("poolAddress is : " + poolAddress);
            IPool pool = IPool.load(poolAddress, web3j, credentials, GAS_PROVIDER);
            Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> info = pool.getUserAccountData(
                address).send();
            BigInteger totalCollateralBase = info.component1();
            BigInteger totalDebtBase = info.component2();
            BigInteger ltv = info.component5();
            BigInteger healthFactor = info.component6();
            HealthInfo healthInfo = HealthInfo.builder()
                .healthFactor(healthFactor)
                .totalBorrows(totalDebtBase.toString())
                .totalCollateralETH(totalCollateralBase.toString())
                .totalLiquidity(ltv.toString())
                .build();
            return healthInfo;
        } catch (Exception e) {
            log.error("getHealthInfo error: " + e.getMessage());
            throw new RuntimeException("get health Info error");
        }
    }

    public List<String> queryChainList() {
        return new ArrayList<>(chainWithToken.keySet());
    }
}
