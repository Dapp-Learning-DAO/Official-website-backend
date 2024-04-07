package com.dl.officialsite.config;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;

import com.dl.officialsite.defi.constants.Constant;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.ipc.UnixIpcService;
import org.web3j.protocol.ipc.WindowsIpcService;

@Configuration
@ConditionalOnClass(Web3j.class)
@EnableConfigurationProperties({ Web3jProperties.class, NetworkProperties.class })
public class Web3jAutoConfiguration {

    private static Log log = LogFactory.getLog(Web3jAutoConfiguration.class);

    @Autowired
    private Web3jProperties properties;
    @Autowired
    private NetworkProperties networkProperties;

    public static final ConcurrentHashMap<ChainInfo, Web3j> web3jMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        for (int i = 0; i < networkProperties.getConfigs().length; i++) {
            NetworkProperties.NetworkDetailProperties detail = networkProperties.getConfigs()[i];
            Web3jService web3jService = buildService(detail.getRpc());
            ChainInfo chainInfo = new ChainInfo();
//            chainInfo.setWeb3j(Web3j.build(web3jService));
            chainInfo.setId(detail.getId());
            chainInfo.setName(detail.getName());
            chainInfo.setTokens(Constant.tokenConfigListMap.get(detail.getId()));
            chainInfo.setLendingPoolAddressProviderV3Address(Constant.aaveLendingPoolProviderV3AddressMap.get(detail.getId()));

            web3jMap.put(chainInfo,Web3j.build(web3jService) );
            log.info("initWeb3jMap.current chain:" + detail.getName() + " rpc:" + detail.getRpc());
        }
        // 执行初始化操作
        System.out.println("Configuration initialized. Executing init method.");
    }

    @Bean
    @ConditionalOnProperty(prefix = Web3jProperties.WEB3J_PREFIX, name = "admin-client", havingValue = "true")
    public Admin admin() {
        Web3jService web3jService = buildService(properties.getClientAddress());
        log.info("Building admin service for endpoint: " +
                properties.getClientAddress());
        return Admin.build(web3jService);
    }

    private Web3jService buildService(String clientAddress) {
        Web3jService web3jService;

        if (clientAddress == null || clientAddress.equals("")) {
            web3jService = new HttpService(createOkHttpClient());
        } else if (clientAddress.startsWith("http")) {
            web3jService = new HttpService(clientAddress, createOkHttpClient(), false);
        } else if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
            web3jService = new WindowsIpcService(clientAddress);
        } else {
            web3jService = new UnixIpcService(clientAddress);
        }

        return web3jService;
    }

    private OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        configureLogging(builder);
        configureTimeouts(builder);
        return builder.build();
    }

    private void configureTimeouts(OkHttpClient.Builder builder) {
        Long tos = properties.getHttpTimeoutSeconds();
        if (tos != null) {
            builder.connectTimeout(tos, TimeUnit.SECONDS);
            builder.readTimeout(tos, TimeUnit.SECONDS); // Sets the socket timeout too
            builder.writeTimeout(tos, TimeUnit.SECONDS);
        }
    }

    private static void configureLogging(OkHttpClient.Builder builder) {
        if (log.isDebugEnabled()) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(log::debug);
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }
    }

}
