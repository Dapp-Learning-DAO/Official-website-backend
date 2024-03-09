package com.dl.officialsite.config;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Configuration
@ConditionalOnClass(Web3j.class)
@EnableConfigurationProperties({Web3jProperties.class,NetworkProperties.class})
public class Web3jAutoConfiguration {

    private static Log log = LogFactory.getLog(Web3jAutoConfiguration.class);

    @Autowired
    private Web3jProperties properties;
    @Autowired
    private NetworkProperties networkProperties;

    public static final ConcurrentHashMap<String,Web3j> web3jMap = new ConcurrentHashMap<String,Web3j>();


    @Bean
    @ConditionalOnMissingBean
    public Web3j web3j() {
        //init map of web3j  map(chainID,web3j)
        initWeb3jMap();

        Web3jService web3jService = buildService(properties.getClientAddress());
        log.info("Building service for endpoint: " + properties.getClientAddress());
        return Web3j.build(web3jService);
    }

    @Bean
    @ConditionalOnProperty(
    prefix = Web3jProperties.WEB3J_PREFIX, name = "admin-client", havingValue =
    "true")
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

    private void initWeb3jMap(){
        for(int i=0;i<networkProperties.getConfigs().length;i++){
            NetworkProperties.NetworkDetailProperties detail = networkProperties.getConfigs()[i];
            Web3jService web3jService = buildService(detail.getRpc());
            web3jMap.put(detail.getId(),Web3j.build(web3jService));
            log.info("initWeb3jMap.current chain:"+detail.getName()+" rpc:"+detail.getRpc());
        }

    }

}
