package com.dl.officialsite.contract.ipooladdressesprovider;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
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
public class IPoolAddressesProvider extends Contract {
    public static final String BINARY = "";

    public static final String FUNC_GETACLADMIN = "getACLAdmin";

    public static final String FUNC_GETACLMANAGER = "getACLManager";

    public static final String FUNC_GETADDRESS = "getAddress";

    public static final String FUNC_GETMARKETID = "getMarketId";

    public static final String FUNC_GETPOOL = "getPool";

    public static final String FUNC_GETPOOLCONFIGURATOR = "getPoolConfigurator";

    public static final String FUNC_GETPOOLDATAPROVIDER = "getPoolDataProvider";

    public static final String FUNC_GETPRICEORACLE = "getPriceOracle";

    public static final String FUNC_GETPRICEORACLESENTINEL = "getPriceOracleSentinel";

    public static final String FUNC_SETACLADMIN = "setACLAdmin";

    public static final String FUNC_SETACLMANAGER = "setACLManager";

    public static final String FUNC_SETADDRESS = "setAddress";

    public static final String FUNC_SETADDRESSASPROXY = "setAddressAsProxy";

    public static final String FUNC_SETMARKETID = "setMarketId";

    public static final String FUNC_SETPOOLCONFIGURATORIMPL = "setPoolConfiguratorImpl";

    public static final String FUNC_SETPOOLDATAPROVIDER = "setPoolDataProvider";

    public static final String FUNC_SETPOOLIMPL = "setPoolImpl";

    public static final String FUNC_SETPRICEORACLE = "setPriceOracle";

    public static final String FUNC_SETPRICEORACLESENTINEL = "setPriceOracleSentinel";

    public static final Event ACLADMINUPDATED_EVENT = new Event("ACLAdminUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event ACLMANAGERUPDATED_EVENT = new Event("ACLManagerUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event ADDRESSSET_EVENT = new Event("AddressSet", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event ADDRESSSETASPROXY_EVENT = new Event("AddressSetAsProxy", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>() {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event MARKETIDSET_EVENT = new Event("MarketIdSet", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>(true) {}, new TypeReference<Utf8String>(true) {}));
    ;

    public static final Event POOLCONFIGURATORUPDATED_EVENT = new Event("PoolConfiguratorUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event POOLDATAPROVIDERUPDATED_EVENT = new Event("PoolDataProviderUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event POOLUPDATED_EVENT = new Event("PoolUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event PRICEORACLESENTINELUPDATED_EVENT = new Event("PriceOracleSentinelUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event PRICEORACLEUPDATED_EVENT = new Event("PriceOracleUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event PROXYCREATED_EVENT = new Event("ProxyCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected IPoolAddressesProvider(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IPoolAddressesProvider(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IPoolAddressesProvider(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IPoolAddressesProvider(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ACLAdminUpdatedEventResponse> getACLAdminUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ACLADMINUPDATED_EVENT, transactionReceipt);
        ArrayList<ACLAdminUpdatedEventResponse> responses = new ArrayList<ACLAdminUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ACLAdminUpdatedEventResponse typedResponse = new ACLAdminUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newAddress = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ACLAdminUpdatedEventResponse getACLAdminUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ACLADMINUPDATED_EVENT, log);
        ACLAdminUpdatedEventResponse typedResponse = new ACLAdminUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.oldAddress = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newAddress = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<ACLAdminUpdatedEventResponse> aCLAdminUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getACLAdminUpdatedEventFromLog(log));
    }

    public Flowable<ACLAdminUpdatedEventResponse> aCLAdminUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ACLADMINUPDATED_EVENT));
        return aCLAdminUpdatedEventFlowable(filter);
    }

    public static List<ACLManagerUpdatedEventResponse> getACLManagerUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ACLMANAGERUPDATED_EVENT, transactionReceipt);
        ArrayList<ACLManagerUpdatedEventResponse> responses = new ArrayList<ACLManagerUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ACLManagerUpdatedEventResponse typedResponse = new ACLManagerUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newAddress = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ACLManagerUpdatedEventResponse getACLManagerUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ACLMANAGERUPDATED_EVENT, log);
        ACLManagerUpdatedEventResponse typedResponse = new ACLManagerUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.oldAddress = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newAddress = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<ACLManagerUpdatedEventResponse> aCLManagerUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getACLManagerUpdatedEventFromLog(log));
    }

    public Flowable<ACLManagerUpdatedEventResponse> aCLManagerUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ACLMANAGERUPDATED_EVENT));
        return aCLManagerUpdatedEventFlowable(filter);
    }

    public static List<AddressSetEventResponse> getAddressSetEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ADDRESSSET_EVENT, transactionReceipt);
        ArrayList<AddressSetEventResponse> responses = new ArrayList<AddressSetEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AddressSetEventResponse typedResponse = new AddressSetEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.oldAddress = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.newAddress = (String) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static AddressSetEventResponse getAddressSetEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ADDRESSSET_EVENT, log);
        AddressSetEventResponse typedResponse = new AddressSetEventResponse();
        typedResponse.log = log;
        typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.oldAddress = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.newAddress = (String) eventValues.getIndexedValues().get(2).getValue();
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

    public static List<AddressSetAsProxyEventResponse> getAddressSetAsProxyEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ADDRESSSETASPROXY_EVENT, transactionReceipt);
        ArrayList<AddressSetAsProxyEventResponse> responses = new ArrayList<AddressSetAsProxyEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AddressSetAsProxyEventResponse typedResponse = new AddressSetAsProxyEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.proxyAddress = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.newImplementationAddress = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.oldImplementationAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static AddressSetAsProxyEventResponse getAddressSetAsProxyEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ADDRESSSETASPROXY_EVENT, log);
        AddressSetAsProxyEventResponse typedResponse = new AddressSetAsProxyEventResponse();
        typedResponse.log = log;
        typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.proxyAddress = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.newImplementationAddress = (String) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.oldImplementationAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<AddressSetAsProxyEventResponse> addressSetAsProxyEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getAddressSetAsProxyEventFromLog(log));
    }

    public Flowable<AddressSetAsProxyEventResponse> addressSetAsProxyEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ADDRESSSETASPROXY_EVENT));
        return addressSetAsProxyEventFlowable(filter);
    }

    public static List<MarketIdSetEventResponse> getMarketIdSetEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(MARKETIDSET_EVENT, transactionReceipt);
        ArrayList<MarketIdSetEventResponse> responses = new ArrayList<MarketIdSetEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MarketIdSetEventResponse typedResponse = new MarketIdSetEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldMarketId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newMarketId = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static MarketIdSetEventResponse getMarketIdSetEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(MARKETIDSET_EVENT, log);
        MarketIdSetEventResponse typedResponse = new MarketIdSetEventResponse();
        typedResponse.log = log;
        typedResponse.oldMarketId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newMarketId = (byte[]) eventValues.getIndexedValues().get(1).getValue();
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

    public static List<PoolConfiguratorUpdatedEventResponse> getPoolConfiguratorUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(POOLCONFIGURATORUPDATED_EVENT, transactionReceipt);
        ArrayList<PoolConfiguratorUpdatedEventResponse> responses = new ArrayList<PoolConfiguratorUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PoolConfiguratorUpdatedEventResponse typedResponse = new PoolConfiguratorUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newAddress = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static PoolConfiguratorUpdatedEventResponse getPoolConfiguratorUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(POOLCONFIGURATORUPDATED_EVENT, log);
        PoolConfiguratorUpdatedEventResponse typedResponse = new PoolConfiguratorUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.oldAddress = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newAddress = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<PoolConfiguratorUpdatedEventResponse> poolConfiguratorUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getPoolConfiguratorUpdatedEventFromLog(log));
    }

    public Flowable<PoolConfiguratorUpdatedEventResponse> poolConfiguratorUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(POOLCONFIGURATORUPDATED_EVENT));
        return poolConfiguratorUpdatedEventFlowable(filter);
    }

    public static List<PoolDataProviderUpdatedEventResponse> getPoolDataProviderUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(POOLDATAPROVIDERUPDATED_EVENT, transactionReceipt);
        ArrayList<PoolDataProviderUpdatedEventResponse> responses = new ArrayList<PoolDataProviderUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PoolDataProviderUpdatedEventResponse typedResponse = new PoolDataProviderUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newAddress = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static PoolDataProviderUpdatedEventResponse getPoolDataProviderUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(POOLDATAPROVIDERUPDATED_EVENT, log);
        PoolDataProviderUpdatedEventResponse typedResponse = new PoolDataProviderUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.oldAddress = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newAddress = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<PoolDataProviderUpdatedEventResponse> poolDataProviderUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getPoolDataProviderUpdatedEventFromLog(log));
    }

    public Flowable<PoolDataProviderUpdatedEventResponse> poolDataProviderUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(POOLDATAPROVIDERUPDATED_EVENT));
        return poolDataProviderUpdatedEventFlowable(filter);
    }

    public static List<PoolUpdatedEventResponse> getPoolUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(POOLUPDATED_EVENT, transactionReceipt);
        ArrayList<PoolUpdatedEventResponse> responses = new ArrayList<PoolUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PoolUpdatedEventResponse typedResponse = new PoolUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newAddress = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static PoolUpdatedEventResponse getPoolUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(POOLUPDATED_EVENT, log);
        PoolUpdatedEventResponse typedResponse = new PoolUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.oldAddress = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newAddress = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<PoolUpdatedEventResponse> poolUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getPoolUpdatedEventFromLog(log));
    }

    public Flowable<PoolUpdatedEventResponse> poolUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(POOLUPDATED_EVENT));
        return poolUpdatedEventFlowable(filter);
    }

    public static List<PriceOracleSentinelUpdatedEventResponse> getPriceOracleSentinelUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(PRICEORACLESENTINELUPDATED_EVENT, transactionReceipt);
        ArrayList<PriceOracleSentinelUpdatedEventResponse> responses = new ArrayList<PriceOracleSentinelUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PriceOracleSentinelUpdatedEventResponse typedResponse = new PriceOracleSentinelUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newAddress = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static PriceOracleSentinelUpdatedEventResponse getPriceOracleSentinelUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(PRICEORACLESENTINELUPDATED_EVENT, log);
        PriceOracleSentinelUpdatedEventResponse typedResponse = new PriceOracleSentinelUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.oldAddress = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newAddress = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<PriceOracleSentinelUpdatedEventResponse> priceOracleSentinelUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getPriceOracleSentinelUpdatedEventFromLog(log));
    }

    public Flowable<PriceOracleSentinelUpdatedEventResponse> priceOracleSentinelUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PRICEORACLESENTINELUPDATED_EVENT));
        return priceOracleSentinelUpdatedEventFlowable(filter);
    }

    public static List<PriceOracleUpdatedEventResponse> getPriceOracleUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(PRICEORACLEUPDATED_EVENT, transactionReceipt);
        ArrayList<PriceOracleUpdatedEventResponse> responses = new ArrayList<PriceOracleUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PriceOracleUpdatedEventResponse typedResponse = new PriceOracleUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newAddress = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static PriceOracleUpdatedEventResponse getPriceOracleUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(PRICEORACLEUPDATED_EVENT, log);
        PriceOracleUpdatedEventResponse typedResponse = new PriceOracleUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.oldAddress = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newAddress = (String) eventValues.getIndexedValues().get(1).getValue();
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
            typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.proxyAddress = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.implementationAddress = (String) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ProxyCreatedEventResponse getProxyCreatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(PROXYCREATED_EVENT, log);
        ProxyCreatedEventResponse typedResponse = new ProxyCreatedEventResponse();
        typedResponse.log = log;
        typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.proxyAddress = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.implementationAddress = (String) eventValues.getIndexedValues().get(2).getValue();
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

    public RemoteFunctionCall<String> getACLAdmin() {
        final Function function = new Function(FUNC_GETACLADMIN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getACLManager() {
        final Function function = new Function(FUNC_GETACLMANAGER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getAddress(byte[] id) {
        final Function function = new Function(FUNC_GETADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getMarketId() {
        final Function function = new Function(FUNC_GETMARKETID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getPool() {
        final Function function = new Function(FUNC_GETPOOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getPoolConfigurator() {
        final Function function = new Function(FUNC_GETPOOLCONFIGURATOR, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getPoolDataProvider() {
        final Function function = new Function(FUNC_GETPOOLDATAPROVIDER, 
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

    public RemoteFunctionCall<String> getPriceOracleSentinel() {
        final Function function = new Function(FUNC_GETPRICEORACLESENTINEL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setACLAdmin(String newAclAdmin) {
        final Function function = new Function(
                FUNC_SETACLADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newAclAdmin)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setACLManager(String newAclManager) {
        final Function function = new Function(
                FUNC_SETACLMANAGER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newAclManager)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setAddress(byte[] id, String newAddress) {
        final Function function = new Function(
                FUNC_SETADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(id), 
                new org.web3j.abi.datatypes.Address(160, newAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setAddressAsProxy(byte[] id, String newImplementationAddress) {
        final Function function = new Function(
                FUNC_SETADDRESSASPROXY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(id), 
                new org.web3j.abi.datatypes.Address(160, newImplementationAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setMarketId(String newMarketId) {
        final Function function = new Function(
                FUNC_SETMARKETID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(newMarketId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setPoolConfiguratorImpl(String newPoolConfiguratorImpl) {
        final Function function = new Function(
                FUNC_SETPOOLCONFIGURATORIMPL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newPoolConfiguratorImpl)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setPoolDataProvider(String newDataProvider) {
        final Function function = new Function(
                FUNC_SETPOOLDATAPROVIDER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newDataProvider)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setPoolImpl(String newPoolImpl) {
        final Function function = new Function(
                FUNC_SETPOOLIMPL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newPoolImpl)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setPriceOracle(String newPriceOracle) {
        final Function function = new Function(
                FUNC_SETPRICEORACLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newPriceOracle)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setPriceOracleSentinel(String newPriceOracleSentinel) {
        final Function function = new Function(
                FUNC_SETPRICEORACLESENTINEL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newPriceOracleSentinel)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static IPoolAddressesProvider load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IPoolAddressesProvider(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IPoolAddressesProvider load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IPoolAddressesProvider(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IPoolAddressesProvider load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new IPoolAddressesProvider(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IPoolAddressesProvider load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IPoolAddressesProvider(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<IPoolAddressesProvider> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IPoolAddressesProvider.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<IPoolAddressesProvider> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IPoolAddressesProvider.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<IPoolAddressesProvider> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IPoolAddressesProvider.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<IPoolAddressesProvider> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IPoolAddressesProvider.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class ACLAdminUpdatedEventResponse extends BaseEventResponse {
        public String oldAddress;

        public String newAddress;
    }

    public static class ACLManagerUpdatedEventResponse extends BaseEventResponse {
        public String oldAddress;

        public String newAddress;
    }

    public static class AddressSetEventResponse extends BaseEventResponse {
        public byte[] id;

        public String oldAddress;

        public String newAddress;
    }

    public static class AddressSetAsProxyEventResponse extends BaseEventResponse {
        public byte[] id;

        public String proxyAddress;

        public String newImplementationAddress;

        public String oldImplementationAddress;
    }

    public static class MarketIdSetEventResponse extends BaseEventResponse {
        public byte[] oldMarketId;

        public byte[] newMarketId;
    }

    public static class PoolConfiguratorUpdatedEventResponse extends BaseEventResponse {
        public String oldAddress;

        public String newAddress;
    }

    public static class PoolDataProviderUpdatedEventResponse extends BaseEventResponse {
        public String oldAddress;

        public String newAddress;
    }

    public static class PoolUpdatedEventResponse extends BaseEventResponse {
        public String oldAddress;

        public String newAddress;
    }

    public static class PriceOracleSentinelUpdatedEventResponse extends BaseEventResponse {
        public String oldAddress;

        public String newAddress;
    }

    public static class PriceOracleUpdatedEventResponse extends BaseEventResponse {
        public String oldAddress;

        public String newAddress;
    }

    public static class ProxyCreatedEventResponse extends BaseEventResponse {
        public byte[] id;

        public String proxyAddress;

        public String implementationAddress;
    }
}
