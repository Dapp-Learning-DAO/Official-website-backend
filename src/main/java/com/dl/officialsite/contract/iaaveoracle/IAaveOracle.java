package com.dl.officialsite.contract.iaaveoracle;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
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
public class IAaveOracle extends Contract {
    public static final String BINARY = "";

    public static final String FUNC_ADDRESSES_PROVIDER = "ADDRESSES_PROVIDER";

    public static final String FUNC_BASE_CURRENCY = "BASE_CURRENCY";

    public static final String FUNC_BASE_CURRENCY_UNIT = "BASE_CURRENCY_UNIT";

    public static final String FUNC_GETASSETPRICE = "getAssetPrice";

    public static final String FUNC_GETASSETSPRICES = "getAssetsPrices";

    public static final String FUNC_GETFALLBACKORACLE = "getFallbackOracle";

    public static final String FUNC_GETSOURCEOFASSET = "getSourceOfAsset";

    public static final String FUNC_SETASSETSOURCES = "setAssetSources";

    public static final String FUNC_SETFALLBACKORACLE = "setFallbackOracle";

    public static final Event ASSETSOURCEUPDATED_EVENT = new Event("AssetSourceUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event BASECURRENCYSET_EVENT = new Event("BaseCurrencySet", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event FALLBACKORACLEUPDATED_EVENT = new Event("FallbackOracleUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected IAaveOracle(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IAaveOracle(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IAaveOracle(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IAaveOracle(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<AssetSourceUpdatedEventResponse> getAssetSourceUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ASSETSOURCEUPDATED_EVENT, transactionReceipt);
        ArrayList<AssetSourceUpdatedEventResponse> responses = new ArrayList<AssetSourceUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AssetSourceUpdatedEventResponse typedResponse = new AssetSourceUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.asset = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.source = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static AssetSourceUpdatedEventResponse getAssetSourceUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ASSETSOURCEUPDATED_EVENT, log);
        AssetSourceUpdatedEventResponse typedResponse = new AssetSourceUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.asset = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.source = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<AssetSourceUpdatedEventResponse> assetSourceUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getAssetSourceUpdatedEventFromLog(log));
    }

    public Flowable<AssetSourceUpdatedEventResponse> assetSourceUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ASSETSOURCEUPDATED_EVENT));
        return assetSourceUpdatedEventFlowable(filter);
    }

    public static List<BaseCurrencySetEventResponse> getBaseCurrencySetEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(BASECURRENCYSET_EVENT, transactionReceipt);
        ArrayList<BaseCurrencySetEventResponse> responses = new ArrayList<BaseCurrencySetEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BaseCurrencySetEventResponse typedResponse = new BaseCurrencySetEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.baseCurrency = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.baseCurrencyUnit = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static BaseCurrencySetEventResponse getBaseCurrencySetEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(BASECURRENCYSET_EVENT, log);
        BaseCurrencySetEventResponse typedResponse = new BaseCurrencySetEventResponse();
        typedResponse.log = log;
        typedResponse.baseCurrency = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.baseCurrencyUnit = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<BaseCurrencySetEventResponse> baseCurrencySetEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getBaseCurrencySetEventFromLog(log));
    }

    public Flowable<BaseCurrencySetEventResponse> baseCurrencySetEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BASECURRENCYSET_EVENT));
        return baseCurrencySetEventFlowable(filter);
    }

    public static List<FallbackOracleUpdatedEventResponse> getFallbackOracleUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(FALLBACKORACLEUPDATED_EVENT, transactionReceipt);
        ArrayList<FallbackOracleUpdatedEventResponse> responses = new ArrayList<FallbackOracleUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FallbackOracleUpdatedEventResponse typedResponse = new FallbackOracleUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.fallbackOracle = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static FallbackOracleUpdatedEventResponse getFallbackOracleUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(FALLBACKORACLEUPDATED_EVENT, log);
        FallbackOracleUpdatedEventResponse typedResponse = new FallbackOracleUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.fallbackOracle = (String) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<FallbackOracleUpdatedEventResponse> fallbackOracleUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getFallbackOracleUpdatedEventFromLog(log));
    }

    public Flowable<FallbackOracleUpdatedEventResponse> fallbackOracleUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FALLBACKORACLEUPDATED_EVENT));
        return fallbackOracleUpdatedEventFlowable(filter);
    }

    public RemoteFunctionCall<String> ADDRESSES_PROVIDER() {
        final Function function = new Function(FUNC_ADDRESSES_PROVIDER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> BASE_CURRENCY() {
        final Function function = new Function(FUNC_BASE_CURRENCY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> BASE_CURRENCY_UNIT() {
        final Function function = new Function(FUNC_BASE_CURRENCY_UNIT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getAssetPrice(String asset) {
        final Function function = new Function(FUNC_GETASSETPRICE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<List> getAssetsPrices(List<String> assets) {
        final Function function = new Function(FUNC_GETASSETSPRICES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(assets, org.web3j.abi.datatypes.Address.class))), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<String> getFallbackOracle() {
        final Function function = new Function(FUNC_GETFALLBACKORACLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getSourceOfAsset(String asset) {
        final Function function = new Function(FUNC_GETSOURCEOFASSET, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setAssetSources(List<String> assets, List<String> sources) {
        final Function function = new Function(
                FUNC_SETASSETSOURCES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(assets, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(sources, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setFallbackOracle(String fallbackOracle) {
        final Function function = new Function(
                FUNC_SETFALLBACKORACLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, fallbackOracle)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static IAaveOracle load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IAaveOracle(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IAaveOracle load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IAaveOracle(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IAaveOracle load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new IAaveOracle(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IAaveOracle load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IAaveOracle(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<IAaveOracle> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IAaveOracle.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<IAaveOracle> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IAaveOracle.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<IAaveOracle> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IAaveOracle.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<IAaveOracle> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IAaveOracle.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class AssetSourceUpdatedEventResponse extends BaseEventResponse {
        public String asset;

        public String source;
    }

    public static class BaseCurrencySetEventResponse extends BaseEventResponse {
        public String baseCurrency;

        public BigInteger baseCurrencyUnit;
    }

    public static class FallbackOracleUpdatedEventResponse extends BaseEventResponse {
        public String fallbackOracle;
    }
}
