package com.dl.officialsite.aave;

import com.dl.officialsite.contract.ilendingpool.ILendingPool;
import com.dl.officialsite.contract.ilendingpooladdressesprovider.ILendingPoolAddressesProvider;
import com.dl.officialsite.contract.ipool.IPool;
import com.dl.officialsite.contract.ipooladdressesprovider.IPoolAddressesProvider;
import com.dl.officialsite.member.MemberController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.web3j.tx.gas.DefaultGasProvider.GAS_LIMIT;
import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

@Service
public class AaveService {
    public static ContractGasProvider GAS_PROVIDER = new StaticGasProvider(GAS_PRICE, GAS_LIMIT);
    public static final Logger logger = LoggerFactory.getLogger(AaveService.class);

   @Autowired
   Web3j web3j;

   @Autowired
   Credentials credentials;


//  1 完成自己地址的健康系数，总借款，总存款， LTV数据获取
//  2  完成USDC、DAI、USDT,LUSD 借贷利率的获取
// 3 完成ETH 、BTC借贷利率 以及价格获取。
// 4 用DL的官网邮箱 发送team0 成员，这些数据。 每天发送一次。
// 5  半小时调一次接口 ，如果HF< 1.2 。立即发送邮件告警；

    public  float getHF(String whale) throws Exception {

       // EthGetBalance ethGetBalance = web3j.ethGetBalance(fish, DefaultBlockParameterName.LATEST).send();
      //  logger.info("balance: " + ethGetBalance.getBalance());
        // polygonv3
        String lendingPoolAddressProviderV3 = "0xa97684ead0e402dC232d5A977953DF7ECBaB3CDb";
        IPoolAddressesProvider addrProV3 = IPoolAddressesProvider.load(lendingPoolAddressProviderV3, web3j, credentials, GAS_PROVIDER);

        String s = addrProV3.getPool().send();
        logger.info("v3 pool: "  +  s);
        IPool lendingPool = IPool.load(s, web3j, credentials, GAS_PROVIDER);
        BigInteger hf = lendingPool.getUserAccountData(whale).send().getValue6();

        BigInteger e16 = new BigInteger("10000000000000000");

        logger.info("*******: " +   hf.divide(e16).floatValue()/100);

        return hf.divide(e16).floatValue()/100;

    }

    //udsc , usdc , DAI borrow  and supply interest.
    public List<Float> getInterestOfSupplyAndBorrow(String address) {
        // 返回 USDC
        return  new ArrayList<>() ;
    }
}
