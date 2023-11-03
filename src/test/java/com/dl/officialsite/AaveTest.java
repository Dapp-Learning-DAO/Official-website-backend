package com.dl.officialsite;

import com.dl.officialsite.aave.AaveService;
import com.dl.officialsite.contract.ilendingpool.ILendingPool;
import com.dl.officialsite.contract.ilendingpooladdressesprovider.ILendingPoolAddressesProvider;
import com.dl.officialsite.contract.ipool.IPool;
import com.dl.officialsite.contract.ipooladdressesprovider.IPoolAddressesProvider;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.Tuple;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

import static org.web3j.tx.gas.DefaultGasProvider.GAS_LIMIT;
import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

public class AaveTest {

    public static ContractGasProvider GAS_PROVIDER = new StaticGasProvider(GAS_PRICE, GAS_LIMIT);
    public static final Logger logger = LoggerFactory.getLogger(AaveTest.class);

    BigInteger e16 = new BigInteger("10000000000000000");
    BigInteger e8 = new BigInteger("100000000");
    BigInteger e23 = new BigInteger("100000000000000000000000");

    Web3j web3j = Web3j.build(new HttpService("https://polygon-rpc.com"));
    String whale = "0xEf53a1797Df32c238F46f1037C8a30Ac884E178C";

    public Credentials getCredentials()   {
        return Credentials.create("1");
    }




    @Test
    public  void getHFV3() throws Exception {

        // polygonv3
        String lendingPoolAddressProviderV3 = "0xa97684ead0e402dC232d5A977953DF7ECBaB3CDb";
        IPoolAddressesProvider addrProV3 = IPoolAddressesProvider.load(lendingPoolAddressProviderV3, web3j, getCredentials(), GAS_PROVIDER);
        String s = addrProV3.getPool().send();
        logger.info("v3 pool: " + s);

        IPool lendingPoolv3 = IPool.load(s, web3j, getCredentials(), GAS_PROVIDER);
        Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> accountData = lendingPoolv3.getUserAccountData(whale).send();
        logger.info("Restult:" + accountData.toString());
        BigInteger supply = accountData.getValue1().divide(e8);
        BigInteger borrow = accountData.getValue2().divide(e8);


        BigInteger LTV = accountData.getValue5().divide(BigInteger.valueOf(100));

        BigInteger hf1 = accountData.getValue6();
        logger.info("*******hf: " + hf1.divide(e16).floatValue() / 100);
        logger.info("*******borrow: " + borrow);
        logger.info("*******supply: " + supply);
        logger.info("LTV:" + LTV + "%" );


        // get Interest
        String usdc=  "0x2791Bca1f2de4661ED88A30C99A7a9449Aa84174";
        String dai=  "0x8f3Cf7ad23Cd3CaDbD9735AFf958023239c6A063";

        IPool.ReserveData reserveData =  lendingPoolv3.getReserveData(usdc).send();

        logger.info( "USDC  variable deposit interest: "  +  reserveData.currentLiquidityRate.divide(e23).floatValue()/100) ;
        logger.info( "USDC  variable borrow interest: "  +  reserveData.currentVariableBorrowRate.divide(e23).floatValue()/100) ;

       reserveData =  lendingPoolv3.getReserveData(dai).send();
        logger.info( "DAI  variable deposit interest: "  +  reserveData.currentLiquidityRate.divide(e23).floatValue()/100) ;
        logger.info( "DAI  variable borrow interest: "  +  reserveData.currentVariableBorrowRate.divide(e23).floatValue()/100) ;
    }


    @Test
    public void getHFV2() throws Exception {
        //polygon V2
        String lendingPoolAddressProviderV2 = "0xd05e3E715d945B59290df0ae8eF85c1BdB684744";
        ILendingPoolAddressesProvider addrPro = ILendingPoolAddressesProvider.load(lendingPoolAddressProviderV2, web3j, getCredentials(), GAS_PROVIDER);
        logger.info("addrPro:" + addrPro.getContractAddress());
        String lendingPoolAddress = addrPro.getLendingPool().send();
        ILendingPool lendingPool = ILendingPool.load(lendingPoolAddress, web3j, getCredentials(), GAS_PROVIDER);
        BigInteger hf = lendingPool.getUserAccountData(whale).send().getValue6();
        logger.info("*******hf: " + hf.divide(e16).floatValue() / 100);
        //todo  the same logic

    }
}
