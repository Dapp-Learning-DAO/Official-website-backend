package com.dl.officialsite.contract.ilendingpooladdressesprovider;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.9.8.
 */
@SuppressWarnings("rawtypes")
public class ILendingPoolAddressesProvider extends Contract {
    public static final String BINARY = "";

    public static final String FUNC_GETADDRESS = "getAddress";

    public static final String FUNC_GETEMERGENCYADMIN = "getEmergencyAdmin";

    public static final String FUNC_GETLENDINGPOOL = "getLendingPool";

    public static final String FUNC_GETLENDINGPOOLCOLLATERALMANAGER = "getLendingPoolCollateralManager";

    public static final String FUNC_GETLENDINGPOOLCONFIGURATOR = "getLendingPoolConfigurator";

    public static final String FUNC_GETLENDINGRATEORACLE = "getLendingRateOracle";

    public static final String FUNC_GETMARKETID = "getMarketId";

    public static final String FUNC_GETPOOLADMIN = "getPoolAdmin";

    public static final String FUNC_GETPRICEORACLE = "getPriceOracle";

    public static final String FUNC_SETADDRESS = "setAddress";

    public static final String FUNC_SETADDRESSASPROXY = "setAddressAsProxy";

    public static final String FUNC_SETEMERGENCYADMIN = "setEmergencyAdmin";

    public static final String FUNC_SETLENDINGPOOLCOLLATERALMANAGER = "setLendingPoolCollateralManager";

    public static final String FUNC_SETLENDINGPOOLCONFIGURATORIMPL = "setLendingPoolConfiguratorImpl";

    public static final String FUNC_SETLENDINGPOOLIMPL = "setLendingPoolImpl";

    public static final String FUNC_SETLENDINGRATEORACLE = "setLendingRateOracle";

    public static final String FUNC_SETMARKETID = "setMarketId";

    public static final String FUNC_SETPOOLADMIN = "setPoolAdmin";

    public static final String FUNC_SETPRICEORACLE = "setPriceOracle";

    public static final Event ADDRESSSET_EVENT = new Event("AddressSet", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Address>(true) {}, new TypeReference<Bool>() {}));
    ;

    public static final Event CONFIGURATIONADMINUPDATED_EVENT = new Event("ConfigurationAdminUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event EMERGENCYADMINUPDATED_EVENT = new Event("EmergencyAdminUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event LENDINGPOOLCOLLATERALMANAGERUPDATED_EVENT = new Event("LendingPoolCollateralManagerUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event LENDINGPOOLCONFIGURATORUPDATED_EVENT = new Event("LendingPoolConfiguratorUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event LENDINGPOOLUPDATED_EVENT = new Event("LendingPoolUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event LENDINGRATEORACLEUPDATED_EVENT = new Event("LendingRateOracleUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event MARKETIDSET_EVENT = new Event("MarketIdSet", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event PRICEORACLEUPDATED_EVENT = new Event("PriceOracleUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event PROXYCREATED_EVENT = new Event("ProxyCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected ILendingPoolAddressesProvider(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ILendingPoolAddressesProvider(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ILendingPoolAddressesProvider(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ILendingPoolAddressesProvider(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<AddressSetEventResponse> getAddressSetEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ADDRESSSET_EVENT, transactionReceipt);
        ArrayList<AddressSetEventResponse> responses = new ArrayList<AddressSetEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AddressSetEventResponse typedResponse = new AddressSetEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.newAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.id = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.hasProxy = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static AddressSetEventResponse getAddressSetEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ADDRESSSET_EVENT, log);
        AddressSetEventResponse typedResponse = new AddressSetEventResponse();
        typedResponse.log = log;
        typedResponse.newAddress = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.id = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.hasProxy = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<AddressSetEventResponse> addressSetEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getAddressSetEventFromLog(log));
    }

    public Flowable<AddressSetEventResponse> addressSetEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ADDRESSSET_EVENT));
        return addressSetEventFlowable(filter);
    }

    public static List<ConfigurationAdminUpdatedEventResponse> getConfigurationAdminUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CONFIGURATIONADMINUPDATED_EVENT, transactionReceipt);
        ArrayList<ConfigurationAdminUpdatedEventResponse> responses = new ArrayList<ConfigurationAdminUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ConfigurationAdminUpdatedEventResponse typedResponse = new ConfigurationAdminUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.newAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ConfigurationAdminUpdatedEventResponse getConfigurationAdminUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CONFIGURATIONADMINUPDATED_EVENT, log);
        ConfigurationAdminUpdatedEventResponse typedResponse = new ConfigurationAdminUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.newAddress = (String) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ConfigurationAdminUpdatedEventResponse> configurationAdminUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getConfigurationAdminUpdatedEventFromLog(log));
    }

    public Flowable<ConfigurationAdminUpdatedEventResponse> configurationAdminUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CONFIGURATIONADMINUPDATED_EVENT));
        return configurationAdminUpdatedEventFlowable(filter);
    }

    public static List<EmergencyAdminUpdatedEventResponse> getEmergencyAdminUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(EMERGENCYADMINUPDATED_EVENT, transactionReceipt);
        ArrayList<EmergencyAdminUpdatedEventResponse> responses = new ArrayList<EmergencyAdminUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            EmergencyAdminUpdatedEventResponse typedResponse = new EmergencyAdminUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.newAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static EmergencyAdminUpdatedEventResponse getEmergencyAdminUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(EMERGENCYADMINUPDATED_EVENT, log);
        EmergencyAdminUpdatedEventResponse typedResponse = new EmergencyAdminUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.newAddress = (String) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<EmergencyAdminUpdatedEventResponse> emergencyAdminUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getEmergencyAdminUpdatedEventFromLog(log));
    }

    public Flowable<EmergencyAdminUpdatedEventResponse> emergencyAdminUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EMERGENCYADMINUPDATED_EVENT));
        return emergencyAdminUpdatedEventFlowable(filter);
    }

    public static List<LendingPoolCollateralManagerUpdatedEventResponse> getLendingPoolCollateralManagerUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LENDINGPOOLCOLLATERALMANAGERUPDATED_EVENT, transactionReceipt);
        ArrayList<LendingPoolCollateralManagerUpdatedEventResponse> responses = new ArrayList<LendingPoolCollateralManagerUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LendingPoolCollateralManagerUpdatedEventResponse typedResponse = new LendingPoolCollateralManagerUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.newAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static LendingPoolCollateralManagerUpdatedEventResponse getLendingPoolCollateralManagerUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LENDINGPOOLCOLLATERALMANAGERUPDATED_EVENT, log);
        LendingPoolCollateralManagerUpdatedEventResponse typedResponse = new LendingPoolCollateralManagerUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.newAddress = (String) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<LendingPoolCollateralManagerUpdatedEventResponse> lendingPoolCollateralManagerUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLendingPoolCollateralManagerUpdatedEventFromLog(log));
    }

    public Flowable<LendingPoolCollateralManagerUpdatedEventResponse> lendingPoolCollateralManagerUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LENDINGPOOLCOLLATERALMANAGERUPDATED_EVENT));
        return lendingPoolCollateralManagerUpdatedEventFlowable(filter);
    }

    public static List<LendingPoolConfiguratorUpdatedEventResponse> getLendingPoolConfiguratorUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LENDINGPOOLCONFIGURATORUPDATED_EVENT, transactionReceipt);
        ArrayList<LendingPoolConfiguratorUpdatedEventResponse> responses = new ArrayList<LendingPoolConfiguratorUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LendingPoolConfiguratorUpdatedEventResponse typedResponse = new LendingPoolConfiguratorUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.newAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static LendingPoolConfiguratorUpdatedEventResponse getLendingPoolConfiguratorUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LENDINGPOOLCONFIGURATORUPDATED_EVENT, log);
        LendingPoolConfiguratorUpdatedEventResponse typedResponse = new LendingPoolConfiguratorUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.newAddress = (String) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<LendingPoolConfiguratorUpdatedEventResponse> lendingPoolConfiguratorUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLendingPoolConfiguratorUpdatedEventFromLog(log));
    }

    public Flowable<LendingPoolConfiguratorUpdatedEventResponse> lendingPoolConfiguratorUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LENDINGPOOLCONFIGURATORUPDATED_EVENT));
        return lendingPoolConfiguratorUpdatedEventFlowable(filter);
    }

    public static List<LendingPoolUpdatedEventResponse> getLendingPoolUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LENDINGPOOLUPDATED_EVENT, transactionReceipt);
        ArrayList<LendingPoolUpdatedEventResponse> responses = new ArrayList<LendingPoolUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LendingPoolUpdatedEventResponse typedResponse = new LendingPoolUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.newAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static LendingPoolUpdatedEventResponse getLendingPoolUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LENDINGPOOLUPDATED_EVENT, log);
        LendingPoolUpdatedEventResponse typedResponse = new LendingPoolUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.newAddress = (String) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<LendingPoolUpdatedEventResponse> lendingPoolUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLendingPoolUpdatedEventFromLog(log));
    }

    public Flowable<LendingPoolUpdatedEventResponse> lendingPoolUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LENDINGPOOLUPDATED_EVENT));
        return lendingPoolUpdatedEventFlowable(filter);
    }

    public static List<LendingRateOracleUpdatedEventResponse> getLendingRateOracleUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LENDINGRATEORACLEUPDATED_EVENT, transactionReceipt);
        ArrayList<LendingRateOracleUpdatedEventResponse> responses = new ArrayList<LendingRateOracleUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LendingRateOracleUpdatedEventResponse typedResponse = new LendingRateOracleUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.newAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static LendingRateOracleUpdatedEventResponse getLendingRateOracleUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LENDINGRATEORACLEUPDATED_EVENT, log);
        LendingRateOracleUpdatedEventResponse typedResponse = new LendingRateOracleUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.newAddress = (String) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<LendingRateOracleUpdatedEventResponse> lendingRateOracleUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLendingRateOracleUpdatedEventFromLog(log));
    }

    public Flowable<LendingRateOracleUpdatedEventResponse> lendingRateOracleUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LENDINGRATEORACLEUPDATED_EVENT));
        return lendingRateOracleUpdatedEventFlowable(filter);
    }

    public static List<MarketIdSetEventResponse> getMarketIdSetEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(MARKETIDSET_EVENT, transactionReceipt);
        ArrayList<MarketIdSetEventResponse> responses = new ArrayList<MarketIdSetEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MarketIdSetEventResponse typedResponse = new MarketIdSetEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.newMarketId = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static MarketIdSetEventResponse getMarketIdSetEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(MARKETIDSET_EVENT, log);
        MarketIdSetEventResponse typedResponse = new MarketIdSetEventResponse();
        typedResponse.log = log;
        typedResponse.newMarketId = (String) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<MarketIdSetEventResponse> marketIdSetEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getMarketIdSetEventFromLog(log));
    }

    public Flowable<MarketIdSetEventResponse> marketIdSetEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MARKETIDSET_EVENT));
        return marketIdSetEventFlowable(filter);
    }

    public static List<PriceOracleUpdatedEventResponse> getPriceOracleUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(PRICEORACLEUPDATED_EVENT, transactionReceipt);
        ArrayList<PriceOracleUpdatedEventResponse> responses = new ArrayList<PriceOracleUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PriceOracleUpdatedEventResponse typedResponse = new PriceOracleUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.newAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static PriceOracleUpdatedEventResponse getPriceOracleUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(PRICEORACLEUPDATED_EVENT, log);
        PriceOracleUpdatedEventResponse typedResponse = new PriceOracleUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.newAddress = (String) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<PriceOracleUpdatedEventResponse> priceOracleUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getPriceOracleUpdatedEventFromLog(log));
    }

    public Flowable<PriceOracleUpdatedEventResponse> priceOracleUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PRICEORACLEUPDATED_EVENT));
        return priceOracleUpdatedEventFlowable(filter);
    }

    public static List<ProxyCreatedEventResponse> getProxyCreatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(PROXYCREATED_EVENT, transactionReceipt);
        ArrayList<ProxyCreatedEventResponse> responses = new ArrayList<ProxyCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ProxyCreatedEventResponse typedResponse = new ProxyCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.newAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.id = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ProxyCreatedEventResponse getProxyCreatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(PROXYCREATED_EVENT, log);
        ProxyCreatedEventResponse typedResponse = new ProxyCreatedEventResponse();
        typedResponse.log = log;
        typedResponse.newAddress = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.id = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ProxyCreatedEventResponse> proxyCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getProxyCreatedEventFromLog(log));
    }

    public Flowable<ProxyCreatedEventResponse> proxyCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PROXYCREATED_EVENT));
        return proxyCreatedEventFlowable(filter);
    }

    public RemoteFunctionCall<String> getAddress(byte[] id) {
        final Function function = new Function(FUNC_GETADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getEmergencyAdmin() {
        final Function function = new Function(FUNC_GETEMERGENCYADMIN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getLendingPool() {
        final Function function = new Function(FUNC_GETLENDINGPOOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getLendingPoolCollateralManager() {
        final Function function = new Function(FUNC_GETLENDINGPOOLCOLLATERALMANAGER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getLendingPoolConfigurator() {
        final Function function = new Function(FUNC_GETLENDINGPOOLCONFIGURATOR, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getLendingRateOracle() {
        final Function function = new Function(FUNC_GETLENDINGRATEORACLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getMarketId() {
        final Function function = new Function(FUNC_GETMARKETID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getPoolAdmin() {
        final Function function = new Function(FUNC_GETPOOLADMIN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getPriceOracle() {
        final Function function = new Function(FUNC_GETPRICEORACLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setAddress(byte[] id, String newAddress) {
        final Function function = new Function(
                FUNC_SETADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(id), 
                new org.web3j.abi.datatypes.Address(160, newAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setAddressAsProxy(byte[] id, String impl) {
        final Function function = new Function(
                FUNC_SETADDRESSASPROXY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(id), 
                new org.web3j.abi.datatypes.Address(160, impl)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setEmergencyAdmin(String admin) {
        final Function function = new Function(
                FUNC_SETEMERGENCYADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, admin)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setLendingPoolCollateralManager(String manager) {
        final Function function = new Function(
                FUNC_SETLENDINGPOOLCOLLATERALMANAGER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, manager)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setLendingPoolConfiguratorImpl(String configurator) {
        final Function function = new Function(
                FUNC_SETLENDINGPOOLCONFIGURATORIMPL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, configurator)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setLendingPoolImpl(String pool) {
        final Function function = new Function(
                FUNC_SETLENDINGPOOLIMPL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, pool)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setLendingRateOracle(String lendingRateOracle) {
        final Function function = new Function(
                FUNC_SETLENDINGRATEORACLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, lendingRateOracle)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setMarketId(String marketId) {
        final Function function = new Function(
                FUNC_SETMARKETID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(marketId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setPoolAdmin(String admin) {
        final Function function = new Function(
                FUNC_SETPOOLADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, admin)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setPriceOracle(String priceOracle) {
        final Function function = new Function(
                FUNC_SETPRICEORACLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, priceOracle)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static ILendingPoolAddressesProvider load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ILendingPoolAddressesProvider(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ILendingPoolAddressesProvider load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ILendingPoolAddressesProvider(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ILendingPoolAddressesProvider load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ILendingPoolAddressesProvider(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ILendingPoolAddressesProvider load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ILendingPoolAddressesProvider(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ILendingPoolAddressesProvider> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ILendingPoolAddressesProvider.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ILendingPoolAddressesProvider> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ILendingPoolAddressesProvider.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<ILendingPoolAddressesProvider> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ILendingPoolAddressesProvider.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ILendingPoolAddressesProvider> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ILendingPoolAddressesProvider.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class AddressSetEventResponse extends BaseEventResponse {
        public String newAddress;

        public byte[] id;

        public Boolean hasProxy;
    }

    public static class ConfigurationAdminUpdatedEventResponse extends BaseEventResponse {
        public String newAddress;
    }

    public static class EmergencyAdminUpdatedEventResponse extends BaseEventResponse {
        public String newAddress;
    }

    public static class LendingPoolCollateralManagerUpdatedEventResponse extends BaseEventResponse {
        public String newAddress;
    }

    public static class LendingPoolConfiguratorUpdatedEventResponse extends BaseEventResponse {
        public String newAddress;
    }

    public static class LendingPoolUpdatedEventResponse extends BaseEventResponse {
        public String newAddress;
    }

    public static class LendingRateOracleUpdatedEventResponse extends BaseEventResponse {
        public String newAddress;
    }

    public static class MarketIdSetEventResponse extends BaseEventResponse {
        public String newMarketId;
    }

    public static class PriceOracleUpdatedEventResponse extends BaseEventResponse {
        public String newAddress;
    }

    public static class ProxyCreatedEventResponse extends BaseEventResponse {
        public String newAddress;

        public byte[] id;
    }
}
