package com.dl.officialsite.defi.constants;

import static org.web3j.tx.gas.DefaultGasProvider.GAS_LIMIT;
import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

import com.dl.officialsite.common.constants.Constants;
import com.dl.officialsite.config.TokenConfig;
import com.dl.officialsite.contract.ipooladdressesprovider.IPoolAddressesProvider;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
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

    public static final TokenConfig POLYGON_DAI =TokenConfig.builder().name("DAI").address("0x8f3Cf7ad23Cd3CaDbD9735AFf958023239c6A063").build();
    public static final TokenConfig POLYGON_USDC =TokenConfig.builder().name("USDC").address("0x3c499c542cEF5E3811e1192ce70d8cC03d5c3359").build();
    public static final TokenConfig POLYGON_USDT =TokenConfig.builder().name("USDT").address("0xc2132d05d31c914a87c6611c10748aeb04b58e8f").build();
    public static final TokenConfig POLYGON_WETH =TokenConfig.builder().name("WETH").address("0x7ceb23fd6bc0add59e62ac25578270cff1b9f619").build();
    public static final TokenConfig POLYGON_WBTC =TokenConfig.builder().name("WBTC").address("0x1bfd67037b42cf73acf2047067bd4f2c47d9bfd6").build();

    public static final TokenConfig POLYGON_USDC_E =TokenConfig.builder().name("USDC.e").address("0x2791bca1f2de4661ed88a30c99a7a9449aa84174").build();
    public static final List<TokenConfig> POLYGON_TOKEN_LIST =Arrays.asList(POLYGON_DAI,
        POLYGON_USDC,POLYGON_USDT,POLYGON_WETH,POLYGON_WBTC,POLYGON_USDC_E);

    public static final TokenConfig OP_DAI =TokenConfig.builder().name("DAI").address("0xda10009cbd5d07dd0cecc66161fc93d7c9000da1").build();
    public static final TokenConfig OP_USDC =TokenConfig.builder().name("USDC").address("0x0b2C639c533813f4Aa9D7837CAf62653d097Ff85").build();
    public static final TokenConfig OP_USDT =TokenConfig.builder().name("USDT").address("0x94b008aa00579c1307b0ef2c499ad98a8ce58e58").build();
    public static final TokenConfig OP_WETH =TokenConfig.builder().name("WETH").address("0x4200000000000000000000000000000000000006").build();
    public static final TokenConfig OP_WBTC =TokenConfig.builder().name("WBTC").address("0x68f180fcce6836688e9084f035309e29bf0a2095").build();

    public static final TokenConfig OP_USDC_E =TokenConfig.builder().name("USDC.e").address("0x7f5c764cbc14f9669b88837ca1490cca17c31607").build();
    public static final TokenConfig OP_USDC_LUSD =TokenConfig.builder().name("LUSD").address(
        "0xc40F949F8a4e094D1b49a23ea9241D289B7b2819").build();
    public static final List<TokenConfig> OP_TOKEN_LIST =Arrays.asList(OP_DAI,OP_USDC,OP_USDT,
        OP_WETH,OP_WBTC,OP_USDC_E,OP_USDC_LUSD);



//    public static final TokenConfig SCROLL_DAI =TokenConfig.builder().name("DAI").address("123").build();
    public static final TokenConfig SCROLL_USDC =TokenConfig.builder().name("USDC").address("0x06eFdBFf2a14a7c8E15944D1F4A48F9F95F663A4").build();
    public static final TokenConfig SCROLL_USDT =TokenConfig.builder().name("USDT").address("0xf55BEC9cafDbE8730f096Aa55dad6D22d44099Df").build();
    public static final TokenConfig SCROLL_WETH =TokenConfig.builder().name("WETH").address("0x5300000000000000000000000000000000000004").build();
//    public static final TokenConfig SCROLL_WBTC =TokenConfig.builder().name("WBTC").address("123").build();
    public static final List<TokenConfig> SCROLL_TOKEN_LIST =Arrays.asList(SCROLL_USDC,SCROLL_WETH);


    public static final TokenConfig ARB_DAI =TokenConfig.builder().name("DAI").address("0xDA10009cBd5D07dd0CeCc66161FC93D7c9000da1").build();
    public static final TokenConfig ARB_USDC =TokenConfig.builder().name("USDC").address("0xaf88d065e77c8cC2239327C5EDb3A432268e5831").build();
    public static final TokenConfig ARB_USDT =TokenConfig.builder().name("USDT").address("0xFd086bC7CD5C481DCC9C85ebE478A1C0b69FCbb9").build();
    public static final TokenConfig ARB_WETH =TokenConfig.builder().name("WETH").address("0x82af49447d8a07e3bd95bd0d56f35241523fbab1").build();
    public static final TokenConfig ARB_WBTC =TokenConfig.builder().name("WBTC").address("0x2f2a2543B76A4166549F7aaB2e75Bef0aefC5B0f").build();
    public static final TokenConfig ARB_USDC_E =TokenConfig.builder().name("USDC.e").address("0xFF970A61A04b1cA14834A43f5dE4533eBDDB5CC8").build();

    public static final TokenConfig ARB_USDC_LUSD =TokenConfig.builder().name("LUSD").address(
        "0x93b346b6BC2548dA6A1E7d98E9a421B42541425b").build();
    public static final List<TokenConfig> ARB_TOKEN_LIST =Arrays.asList(ARB_DAI,ARB_USDC,
        ARB_USDT,ARB_WETH,ARB_WBTC,ARB_USDC_E,ARB_USDC_LUSD);





    // public static final TokenConfig BASE_DAI =TokenConfig.builder().name("DAI").address("").build();
    public static final TokenConfig BASE_USDC =TokenConfig.builder().name("USDC").address("0x833589fCD6eDb6E08f4c7C32D4f71b54bdA02913").build();
    // public static final TokenConfig BASE_USDT =TokenConfig.builder().name("USDT").address("").build();
    public static final TokenConfig BASE_WETH =TokenConfig.builder().name("WETH").address("0x4200000000000000000000000000000000000006").build();
    // public static final TokenConfig BASE_WBTC =TokenConfig.builder().name("WBTC").address("").build();
    public static final List<TokenConfig> BASE_TOKEN_LIST =Arrays.asList(BASE_USDC,BASE_WETH);




    /*public static final TokenConfig GNOSIS_DAI =TokenConfig.builder().name("DAI").address("0x44fA8E6f47987339850636F88629646662444217").build();*/
    public static final TokenConfig GNOSIS_USDC =TokenConfig.builder().name("USDC").address("0xDDAfbb505ad214D7b80b1f830fcCc89B60fb7A83").build();
    /*public static final TokenConfig GNOSIS_USDT =TokenConfig.builder().name("USDT").address("0x4ECaBa5870353805a9F068101A40E0f32ed605C6").build();*/
    public static final TokenConfig GNOSIS_WETH =TokenConfig.builder().name("WETH").address("0x6a023ccd1ff6f2045c3309768ead9e68f978f6e1").build();
    // public static final TokenConfig GNOSIS_WBTC =TokenConfig.builder().name("WBTC").address("").build();
    public static final List<TokenConfig> GNOSIS_TOKEN_LIST =Arrays.asList(GNOSIS_USDC,GNOSIS_WETH);


    public static ConcurrentHashMap<String,List<TokenConfig>> tokenConfigListMap = new ConcurrentHashMap<String, List<TokenConfig>>() {{
        put( Constants.CHAIN_ID_POLYGON, POLYGON_TOKEN_LIST);
        put( Constants.CHAIN_ID_OP, OP_TOKEN_LIST);
        put( Constants.CHAIN_ID_SCROLL, SCROLL_TOKEN_LIST);
        put( Constants.CHAIN_ID_ARBITRUM, ARB_TOKEN_LIST);
        put( Constants.CHAIN_ID_BASE, BASE_TOKEN_LIST);
        put( Constants.CHAIN_ID_GNOSIS, GNOSIS_TOKEN_LIST);
    }};


    public static ConcurrentHashMap<String,String> aaveLendingPoolProviderV3AddressMap = new ConcurrentHashMap<String, String>() {{
                put( Constants.CHAIN_ID_POLYGON, "0xa97684ead0e402dC232d5A977953DF7ECBaB3CDb");
                put( Constants.CHAIN_ID_OP, "0xa97684ead0e402dC232d5A977953DF7ECBaB3CDb");
                put( Constants.CHAIN_ID_SCROLL, "0x69850D0B276776781C063771b161bd8894BCdD04");
                put( Constants.CHAIN_ID_ARBITRUM, "0xa97684ead0e402dC232d5A977953DF7ECBaB3CDb");
                put( Constants.CHAIN_ID_BASE, "0xe20fCBdBfFC4Dd138cE8b2E6FBb6CB49777ad64D");
                put( Constants.CHAIN_ID_GNOSIS, "0x36616cf17557639614c1cdDb356b1B83fc0B2132");
            }};



    public static ContractGasProvider GAS_PROVIDER = new StaticGasProvider(GAS_PRICE, GAS_LIMIT);

//    @Autowired
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
