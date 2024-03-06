package com.dl.officialsite.aave;

import static org.web3j.tx.gas.DefaultGasProvider.GAS_LIMIT;
import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

import com.dl.officialsite.contract.iaaveoracle.IAaveOracle;
import com.dl.officialsite.contract.ipool.IPool;
import com.dl.officialsite.contract.ipooladdressesprovider.IPoolAddressesProvider;
import java.math.BigInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

@Service
@Slf4j
public class AaveService {
    public static ContractGasProvider GAS_PROVIDER = new StaticGasProvider(GAS_PRICE, GAS_LIMIT);

    BigInteger e16 = new BigInteger("10000000000000000");
    BigInteger e8 = new BigInteger("100000000");
    BigInteger e23 = new BigInteger("100000000000000000000000");


   @Autowired
   Web3j web3j;

   @Autowired
   Credentials credentials;

   @Autowired
   IPoolAddressesProvider poolAddressesProvider;





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

    public TokenInfoList getVar() throws Exception {

        // get Interest
        String usdc=  "0x2791Bca1f2de4661ED88A30C99A7a9449Aa84174";
        String usdt = "0xc2132D05D31c914a87C6611C10748AEb04B58e8F";
        String dai=  "0x8f3Cf7ad23Cd3CaDbD9735AFf958023239c6A063";
        String eth = "0x7ceB23fD6bC0adD59E62ac25578270cFf1b9f619";
        String btc = "0x1BFD67037B42Cf73acF2047067bd4F2C47D9BfD6";

        String poolAddress = poolAddressesProvider.getPool().send();
        log.info("poolAddress is : " + poolAddress);
        IPool pool = IPool.load(poolAddress, web3j, credentials, GAS_PROVIDER);
        IPool.ReserveData reserveData = pool.getReserveData(usdc).send();

        TokenInfo USDC = TokenInfo.builder()
            .borrow(getBorrow("usdc", reserveData))
            .deposit(getDeposit("usdc", reserveData))
            .build();

        reserveData =  pool.getReserveData(dai).send();
        TokenInfo DAI = TokenInfo.builder()
            .borrow(getBorrow("dai", reserveData))
            .deposit(getDeposit("dai", reserveData))
            .build();

        reserveData =  pool.getReserveData(usdt).send();
        TokenInfo USDT = TokenInfo.builder()
            .borrow(getBorrow("usdt", reserveData))
            .deposit(getDeposit("usdt", reserveData))
            .build();

        reserveData =  pool.getReserveData(eth).send();
        TokenInfo ETH = TokenInfo.builder()
            .borrow(getBorrow("eth", reserveData))
            .deposit(getDeposit("eth", reserveData))
            .price(String.valueOf(getTokenPrice(eth)))
            .build();

        reserveData =  pool.getReserveData(btc).send();
        TokenInfo BTC = TokenInfo.builder()
            .borrow(getBorrow("btc", reserveData))
            .deposit(getDeposit("btc", reserveData))
            .price(String.valueOf(getTokenPrice(btc)))
            .build();

        TokenInfoList tokenInfoList = new TokenInfoList();
        tokenInfoList.setUSDC(USDC);
        tokenInfoList.setDAI(DAI);
        tokenInfoList.setUSDT(USDT);
        tokenInfoList.setETH(ETH);
        tokenInfoList.setBTC(BTC);
        return tokenInfoList;
    }

    private String getBorrow(String tokenName, IPool.ReserveData reserveData) {
        log.info("{} variable borrow interest:" + reserveData.currentVariableBorrowRate.divide(e23).floatValue()/100, tokenName);
        return reserveData.currentVariableBorrowRate.divide(e23).floatValue()/100 + "%";
    }

    private String getDeposit(String tokenName, IPool.ReserveData reserveData) {
        log.info("{} variable deposit interest:" + reserveData.currentVariableBorrowRate.divide(e23).floatValue()/100, tokenName);
        return reserveData.currentLiquidityRate.divide(e23).floatValue()/100 + "%";
    }

    private float getTokenPrice(String tokenAddress) throws Exception {
        String iAaveOracleAddress = "0xb023e699F5a33916Ea823A16485e259257cA8Bd1";
        IAaveOracle iAaveOracle = IAaveOracle.load(iAaveOracleAddress, web3j, credentials, GAS_PROVIDER);
        float tokenPrice =
            iAaveOracle.getAssetPrice(tokenAddress).send().divide(e8).floatValue();
        return tokenPrice;
    }


}
