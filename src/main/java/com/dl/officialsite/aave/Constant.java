package com.dl.officialsite.aave;

import static org.web3j.tx.gas.DefaultGasProvider.GAS_LIMIT;
import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

import com.dl.officialsite.contract.ipooladdressesprovider.IPoolAddressesProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

/**
 * @ClassName Compent
 * @Author jackchen
 * @Date 2023/11/5 14:30
 * @Description Constant
 **/
@Component
public class Constant {

    public static ContractGasProvider GAS_PROVIDER = new StaticGasProvider(GAS_PRICE, GAS_LIMIT);

    @Autowired
    Web3j web3j;

    @Autowired
    Credentials credentials;

    @Bean
    IPoolAddressesProvider IPoolAddressesProvider() {
        String poolAddressesProviderAddress = "0xa97684ead0e402dC232d5A977953DF7ECBaB3CDb";
        IPoolAddressesProvider poolAddressesProvider = IPoolAddressesProvider.load(poolAddressesProviderAddress,
            web3j, credentials, GAS_PROVIDER);
        return poolAddressesProvider;
    }

}
