package com.dl.officialsite.contract.ipool;

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
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint128;
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint40;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple6;
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
public class IPool extends Contract {
    public static final String BINARY = "";

    public static final String FUNC_ADDRESSES_PROVIDER = "ADDRESSES_PROVIDER";

    public static final String FUNC_BRIDGE_PROTOCOL_FEE = "BRIDGE_PROTOCOL_FEE";

    public static final String FUNC_FLASHLOAN_PREMIUM_TOTAL = "FLASHLOAN_PREMIUM_TOTAL";

    public static final String FUNC_FLASHLOAN_PREMIUM_TO_PROTOCOL = "FLASHLOAN_PREMIUM_TO_PROTOCOL";

    public static final String FUNC_MAX_NUMBER_RESERVES = "MAX_NUMBER_RESERVES";

    public static final String FUNC_MAX_STABLE_RATE_BORROW_SIZE_PERCENT = "MAX_STABLE_RATE_BORROW_SIZE_PERCENT";

    public static final String FUNC_BACKUNBACKED = "backUnbacked";

    public static final String FUNC_BORROW = "borrow";

    public static final String FUNC_CONFIGUREEMODECATEGORY = "configureEModeCategory";

    public static final String FUNC_DEPOSIT = "deposit";

    public static final String FUNC_DROPRESERVE = "dropReserve";

    public static final String FUNC_FINALIZETRANSFER = "finalizeTransfer";

    public static final String FUNC_FLASHLOAN = "flashLoan";

    public static final String FUNC_FLASHLOANSIMPLE = "flashLoanSimple";

    public static final String FUNC_GETCONFIGURATION = "getConfiguration";

    public static final String FUNC_GETEMODECATEGORYDATA = "getEModeCategoryData";

    public static final String FUNC_GETRESERVEADDRESSBYID = "getReserveAddressById";

    public static final String FUNC_GETRESERVEDATA = "getReserveData";

    public static final String FUNC_GETRESERVENORMALIZEDINCOME = "getReserveNormalizedIncome";

    public static final String FUNC_GETRESERVENORMALIZEDVARIABLEDEBT = "getReserveNormalizedVariableDebt";

    public static final String FUNC_GETRESERVESLIST = "getReservesList";

    public static final String FUNC_GETUSERACCOUNTDATA = "getUserAccountData";

    public static final String FUNC_GETUSERCONFIGURATION = "getUserConfiguration";

    public static final String FUNC_GETUSEREMODE = "getUserEMode";

    public static final String FUNC_INITRESERVE = "initReserve";

    public static final String FUNC_LIQUIDATIONCALL = "liquidationCall";

    public static final String FUNC_MINTTOTREASURY = "mintToTreasury";

    public static final String FUNC_MINTUNBACKED = "mintUnbacked";

    public static final String FUNC_REBALANCESTABLEBORROWRATE = "rebalanceStableBorrowRate";

    public static final String FUNC_REPAY = "repay";

    public static final String FUNC_REPAYWITHATOKENS = "repayWithATokens";

    public static final String FUNC_REPAYWITHPERMIT = "repayWithPermit";

    public static final String FUNC_RESCUETOKENS = "rescueTokens";

    public static final String FUNC_RESETISOLATIONMODETOTALDEBT = "resetIsolationModeTotalDebt";

    public static final String FUNC_SETCONFIGURATION = "setConfiguration";

    public static final String FUNC_SETRESERVEINTERESTRATESTRATEGYADDRESS = "setReserveInterestRateStrategyAddress";

    public static final String FUNC_SETUSEREMODE = "setUserEMode";

    public static final String FUNC_SETUSERUSERESERVEASCOLLATERAL = "setUserUseReserveAsCollateral";

    public static final String FUNC_SUPPLY = "supply";

    public static final String FUNC_SUPPLYWITHPERMIT = "supplyWithPermit";

    public static final String FUNC_SWAPBORROWRATEMODE = "swapBorrowRateMode";

    public static final String FUNC_UPDATEBRIDGEPROTOCOLFEE = "updateBridgeProtocolFee";

    public static final String FUNC_UPDATEFLASHLOANPREMIUMS = "updateFlashloanPremiums";

    public static final String FUNC_WITHDRAW = "withdraw";

    public static final Event BACKUNBACKED_EVENT = new Event("BackUnbacked", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event BORROW_EVENT = new Event("Borrow", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>() {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint8>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint16>(true) {}));
    ;

    public static final Event FLASHLOAN_EVENT = new Event("FlashLoan", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>() {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint8>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint16>(true) {}));
    ;

    public static final Event ISOLATIONMODETOTALDEBTUPDATED_EVENT = new Event("IsolationModeTotalDebtUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event LIQUIDATIONCALL_EVENT = new Event("LiquidationCall", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event MINTUNBACKED_EVENT = new Event("MintUnbacked", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>() {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint16>(true) {}));
    ;

    public static final Event MINTEDTOTREASURY_EVENT = new Event("MintedToTreasury", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event REBALANCESTABLEBORROWRATE_EVENT = new Event("RebalanceStableBorrowRate", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event REPAY_EVENT = new Event("Repay", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event RESERVEDATAUPDATED_EVENT = new Event("ReserveDataUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event RESERVEUSEDASCOLLATERALDISABLED_EVENT = new Event("ReserveUsedAsCollateralDisabled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event RESERVEUSEDASCOLLATERALENABLED_EVENT = new Event("ReserveUsedAsCollateralEnabled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event SUPPLY_EVENT = new Event("Supply", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>() {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint16>(true) {}));
    ;

    public static final Event SWAPBORROWRATEMODE_EVENT = new Event("SwapBorrowRateMode", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint8>() {}));
    ;

    public static final Event USEREMODESET_EVENT = new Event("UserEModeSet", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint8>() {}));
    ;

    public static final Event WITHDRAW_EVENT = new Event("Withdraw", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected IPool(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IPool(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IPool(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IPool(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<BackUnbackedEventResponse> getBackUnbackedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(BACKUNBACKED_EVENT, transactionReceipt);
        ArrayList<BackUnbackedEventResponse> responses = new ArrayList<BackUnbackedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BackUnbackedEventResponse typedResponse = new BackUnbackedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.reserve = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.backer = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.fee = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static BackUnbackedEventResponse getBackUnbackedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(BACKUNBACKED_EVENT, log);
        BackUnbackedEventResponse typedResponse = new BackUnbackedEventResponse();
        typedResponse.log = log;
        typedResponse.reserve = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.backer = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.fee = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<BackUnbackedEventResponse> backUnbackedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getBackUnbackedEventFromLog(log));
    }

    public Flowable<BackUnbackedEventResponse> backUnbackedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BACKUNBACKED_EVENT));
        return backUnbackedEventFlowable(filter);
    }

    public static List<BorrowEventResponse> getBorrowEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(BORROW_EVENT, transactionReceipt);
        ArrayList<BorrowEventResponse> responses = new ArrayList<BorrowEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BorrowEventResponse typedResponse = new BorrowEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.reserve = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.onBehalfOf = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.referralCode = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.interestRateMode = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.borrowRate = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static BorrowEventResponse getBorrowEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(BORROW_EVENT, log);
        BorrowEventResponse typedResponse = new BorrowEventResponse();
        typedResponse.log = log;
        typedResponse.reserve = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.onBehalfOf = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.referralCode = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.interestRateMode = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        typedResponse.borrowRate = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
        return typedResponse;
    }

    public Flowable<BorrowEventResponse> borrowEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getBorrowEventFromLog(log));
    }

    public Flowable<BorrowEventResponse> borrowEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BORROW_EVENT));
        return borrowEventFlowable(filter);
    }

    public static List<FlashLoanEventResponse> getFlashLoanEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(FLASHLOAN_EVENT, transactionReceipt);
        ArrayList<FlashLoanEventResponse> responses = new ArrayList<FlashLoanEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FlashLoanEventResponse typedResponse = new FlashLoanEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.target = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.asset = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.referralCode = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.initiator = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.interestRateMode = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.premium = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static FlashLoanEventResponse getFlashLoanEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(FLASHLOAN_EVENT, log);
        FlashLoanEventResponse typedResponse = new FlashLoanEventResponse();
        typedResponse.log = log;
        typedResponse.target = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.asset = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.referralCode = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.initiator = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.interestRateMode = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        typedResponse.premium = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
        return typedResponse;
    }

    public Flowable<FlashLoanEventResponse> flashLoanEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getFlashLoanEventFromLog(log));
    }

    public Flowable<FlashLoanEventResponse> flashLoanEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FLASHLOAN_EVENT));
        return flashLoanEventFlowable(filter);
    }

    public static List<IsolationModeTotalDebtUpdatedEventResponse> getIsolationModeTotalDebtUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ISOLATIONMODETOTALDEBTUPDATED_EVENT, transactionReceipt);
        ArrayList<IsolationModeTotalDebtUpdatedEventResponse> responses = new ArrayList<IsolationModeTotalDebtUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            IsolationModeTotalDebtUpdatedEventResponse typedResponse = new IsolationModeTotalDebtUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.asset = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.totalDebt = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static IsolationModeTotalDebtUpdatedEventResponse getIsolationModeTotalDebtUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ISOLATIONMODETOTALDEBTUPDATED_EVENT, log);
        IsolationModeTotalDebtUpdatedEventResponse typedResponse = new IsolationModeTotalDebtUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.asset = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.totalDebt = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<IsolationModeTotalDebtUpdatedEventResponse> isolationModeTotalDebtUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getIsolationModeTotalDebtUpdatedEventFromLog(log));
    }

    public Flowable<IsolationModeTotalDebtUpdatedEventResponse> isolationModeTotalDebtUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ISOLATIONMODETOTALDEBTUPDATED_EVENT));
        return isolationModeTotalDebtUpdatedEventFlowable(filter);
    }

    public static List<LiquidationCallEventResponse> getLiquidationCallEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LIQUIDATIONCALL_EVENT, transactionReceipt);
        ArrayList<LiquidationCallEventResponse> responses = new ArrayList<LiquidationCallEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LiquidationCallEventResponse typedResponse = new LiquidationCallEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.collateralAsset = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.debtAsset = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.user = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.debtToCover = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.liquidatedCollateralAmount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.liquidator = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.receiveAToken = (Boolean) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static LiquidationCallEventResponse getLiquidationCallEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LIQUIDATIONCALL_EVENT, log);
        LiquidationCallEventResponse typedResponse = new LiquidationCallEventResponse();
        typedResponse.log = log;
        typedResponse.collateralAsset = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.debtAsset = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.user = (String) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.debtToCover = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.liquidatedCollateralAmount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.liquidator = (String) eventValues.getNonIndexedValues().get(2).getValue();
        typedResponse.receiveAToken = (Boolean) eventValues.getNonIndexedValues().get(3).getValue();
        return typedResponse;
    }

    public Flowable<LiquidationCallEventResponse> liquidationCallEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLiquidationCallEventFromLog(log));
    }

    public Flowable<LiquidationCallEventResponse> liquidationCallEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LIQUIDATIONCALL_EVENT));
        return liquidationCallEventFlowable(filter);
    }

    public static List<MintUnbackedEventResponse> getMintUnbackedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(MINTUNBACKED_EVENT, transactionReceipt);
        ArrayList<MintUnbackedEventResponse> responses = new ArrayList<MintUnbackedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MintUnbackedEventResponse typedResponse = new MintUnbackedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.reserve = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.onBehalfOf = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.referralCode = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static MintUnbackedEventResponse getMintUnbackedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(MINTUNBACKED_EVENT, log);
        MintUnbackedEventResponse typedResponse = new MintUnbackedEventResponse();
        typedResponse.log = log;
        typedResponse.reserve = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.onBehalfOf = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.referralCode = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<MintUnbackedEventResponse> mintUnbackedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getMintUnbackedEventFromLog(log));
    }

    public Flowable<MintUnbackedEventResponse> mintUnbackedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MINTUNBACKED_EVENT));
        return mintUnbackedEventFlowable(filter);
    }

    public static List<MintedToTreasuryEventResponse> getMintedToTreasuryEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(MINTEDTOTREASURY_EVENT, transactionReceipt);
        ArrayList<MintedToTreasuryEventResponse> responses = new ArrayList<MintedToTreasuryEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MintedToTreasuryEventResponse typedResponse = new MintedToTreasuryEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.reserve = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amountMinted = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static MintedToTreasuryEventResponse getMintedToTreasuryEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(MINTEDTOTREASURY_EVENT, log);
        MintedToTreasuryEventResponse typedResponse = new MintedToTreasuryEventResponse();
        typedResponse.log = log;
        typedResponse.reserve = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.amountMinted = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<MintedToTreasuryEventResponse> mintedToTreasuryEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getMintedToTreasuryEventFromLog(log));
    }

    public Flowable<MintedToTreasuryEventResponse> mintedToTreasuryEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MINTEDTOTREASURY_EVENT));
        return mintedToTreasuryEventFlowable(filter);
    }

    public static List<RebalanceStableBorrowRateEventResponse> getRebalanceStableBorrowRateEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(REBALANCESTABLEBORROWRATE_EVENT, transactionReceipt);
        ArrayList<RebalanceStableBorrowRateEventResponse> responses = new ArrayList<RebalanceStableBorrowRateEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RebalanceStableBorrowRateEventResponse typedResponse = new RebalanceStableBorrowRateEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.reserve = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static RebalanceStableBorrowRateEventResponse getRebalanceStableBorrowRateEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(REBALANCESTABLEBORROWRATE_EVENT, log);
        RebalanceStableBorrowRateEventResponse typedResponse = new RebalanceStableBorrowRateEventResponse();
        typedResponse.log = log;
        typedResponse.reserve = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<RebalanceStableBorrowRateEventResponse> rebalanceStableBorrowRateEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getRebalanceStableBorrowRateEventFromLog(log));
    }

    public Flowable<RebalanceStableBorrowRateEventResponse> rebalanceStableBorrowRateEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REBALANCESTABLEBORROWRATE_EVENT));
        return rebalanceStableBorrowRateEventFlowable(filter);
    }

    public static List<RepayEventResponse> getRepayEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(REPAY_EVENT, transactionReceipt);
        ArrayList<RepayEventResponse> responses = new ArrayList<RepayEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RepayEventResponse typedResponse = new RepayEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.reserve = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.repayer = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.useATokens = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static RepayEventResponse getRepayEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(REPAY_EVENT, log);
        RepayEventResponse typedResponse = new RepayEventResponse();
        typedResponse.log = log;
        typedResponse.reserve = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.repayer = (String) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.useATokens = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<RepayEventResponse> repayEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getRepayEventFromLog(log));
    }

    public Flowable<RepayEventResponse> repayEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REPAY_EVENT));
        return repayEventFlowable(filter);
    }

    public static List<ReserveDataUpdatedEventResponse> getReserveDataUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(RESERVEDATAUPDATED_EVENT, transactionReceipt);
        ArrayList<ReserveDataUpdatedEventResponse> responses = new ArrayList<ReserveDataUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ReserveDataUpdatedEventResponse typedResponse = new ReserveDataUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.reserve = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.liquidityRate = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.stableBorrowRate = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.variableBorrowRate = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.liquidityIndex = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.variableBorrowIndex = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ReserveDataUpdatedEventResponse getReserveDataUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(RESERVEDATAUPDATED_EVENT, log);
        ReserveDataUpdatedEventResponse typedResponse = new ReserveDataUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.reserve = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.liquidityRate = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.stableBorrowRate = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.variableBorrowRate = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        typedResponse.liquidityIndex = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
        typedResponse.variableBorrowIndex = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
        return typedResponse;
    }

    public Flowable<ReserveDataUpdatedEventResponse> reserveDataUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getReserveDataUpdatedEventFromLog(log));
    }

    public Flowable<ReserveDataUpdatedEventResponse> reserveDataUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(RESERVEDATAUPDATED_EVENT));
        return reserveDataUpdatedEventFlowable(filter);
    }

    public static List<ReserveUsedAsCollateralDisabledEventResponse> getReserveUsedAsCollateralDisabledEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(RESERVEUSEDASCOLLATERALDISABLED_EVENT, transactionReceipt);
        ArrayList<ReserveUsedAsCollateralDisabledEventResponse> responses = new ArrayList<ReserveUsedAsCollateralDisabledEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ReserveUsedAsCollateralDisabledEventResponse typedResponse = new ReserveUsedAsCollateralDisabledEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.reserve = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ReserveUsedAsCollateralDisabledEventResponse getReserveUsedAsCollateralDisabledEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(RESERVEUSEDASCOLLATERALDISABLED_EVENT, log);
        ReserveUsedAsCollateralDisabledEventResponse typedResponse = new ReserveUsedAsCollateralDisabledEventResponse();
        typedResponse.log = log;
        typedResponse.reserve = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<ReserveUsedAsCollateralDisabledEventResponse> reserveUsedAsCollateralDisabledEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getReserveUsedAsCollateralDisabledEventFromLog(log));
    }

    public Flowable<ReserveUsedAsCollateralDisabledEventResponse> reserveUsedAsCollateralDisabledEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(RESERVEUSEDASCOLLATERALDISABLED_EVENT));
        return reserveUsedAsCollateralDisabledEventFlowable(filter);
    }

    public static List<ReserveUsedAsCollateralEnabledEventResponse> getReserveUsedAsCollateralEnabledEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(RESERVEUSEDASCOLLATERALENABLED_EVENT, transactionReceipt);
        ArrayList<ReserveUsedAsCollateralEnabledEventResponse> responses = new ArrayList<ReserveUsedAsCollateralEnabledEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ReserveUsedAsCollateralEnabledEventResponse typedResponse = new ReserveUsedAsCollateralEnabledEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.reserve = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ReserveUsedAsCollateralEnabledEventResponse getReserveUsedAsCollateralEnabledEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(RESERVEUSEDASCOLLATERALENABLED_EVENT, log);
        ReserveUsedAsCollateralEnabledEventResponse typedResponse = new ReserveUsedAsCollateralEnabledEventResponse();
        typedResponse.log = log;
        typedResponse.reserve = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<ReserveUsedAsCollateralEnabledEventResponse> reserveUsedAsCollateralEnabledEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getReserveUsedAsCollateralEnabledEventFromLog(log));
    }

    public Flowable<ReserveUsedAsCollateralEnabledEventResponse> reserveUsedAsCollateralEnabledEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(RESERVEUSEDASCOLLATERALENABLED_EVENT));
        return reserveUsedAsCollateralEnabledEventFlowable(filter);
    }

    public static List<SupplyEventResponse> getSupplyEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(SUPPLY_EVENT, transactionReceipt);
        ArrayList<SupplyEventResponse> responses = new ArrayList<SupplyEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SupplyEventResponse typedResponse = new SupplyEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.reserve = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.onBehalfOf = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.referralCode = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static SupplyEventResponse getSupplyEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(SUPPLY_EVENT, log);
        SupplyEventResponse typedResponse = new SupplyEventResponse();
        typedResponse.log = log;
        typedResponse.reserve = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.onBehalfOf = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.referralCode = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<SupplyEventResponse> supplyEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getSupplyEventFromLog(log));
    }

    public Flowable<SupplyEventResponse> supplyEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SUPPLY_EVENT));
        return supplyEventFlowable(filter);
    }

    public static List<SwapBorrowRateModeEventResponse> getSwapBorrowRateModeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(SWAPBORROWRATEMODE_EVENT, transactionReceipt);
        ArrayList<SwapBorrowRateModeEventResponse> responses = new ArrayList<SwapBorrowRateModeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SwapBorrowRateModeEventResponse typedResponse = new SwapBorrowRateModeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.reserve = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.interestRateMode = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static SwapBorrowRateModeEventResponse getSwapBorrowRateModeEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(SWAPBORROWRATEMODE_EVENT, log);
        SwapBorrowRateModeEventResponse typedResponse = new SwapBorrowRateModeEventResponse();
        typedResponse.log = log;
        typedResponse.reserve = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.interestRateMode = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<SwapBorrowRateModeEventResponse> swapBorrowRateModeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getSwapBorrowRateModeEventFromLog(log));
    }

    public Flowable<SwapBorrowRateModeEventResponse> swapBorrowRateModeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SWAPBORROWRATEMODE_EVENT));
        return swapBorrowRateModeEventFlowable(filter);
    }

    public static List<UserEModeSetEventResponse> getUserEModeSetEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(USEREMODESET_EVENT, transactionReceipt);
        ArrayList<UserEModeSetEventResponse> responses = new ArrayList<UserEModeSetEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UserEModeSetEventResponse typedResponse = new UserEModeSetEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.categoryId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static UserEModeSetEventResponse getUserEModeSetEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(USEREMODESET_EVENT, log);
        UserEModeSetEventResponse typedResponse = new UserEModeSetEventResponse();
        typedResponse.log = log;
        typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.categoryId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<UserEModeSetEventResponse> userEModeSetEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getUserEModeSetEventFromLog(log));
    }

    public Flowable<UserEModeSetEventResponse> userEModeSetEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(USEREMODESET_EVENT));
        return userEModeSetEventFlowable(filter);
    }

    public static List<WithdrawEventResponse> getWithdrawEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(WITHDRAW_EVENT, transactionReceipt);
        ArrayList<WithdrawEventResponse> responses = new ArrayList<WithdrawEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WithdrawEventResponse typedResponse = new WithdrawEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.reserve = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static WithdrawEventResponse getWithdrawEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(WITHDRAW_EVENT, log);
        WithdrawEventResponse typedResponse = new WithdrawEventResponse();
        typedResponse.log = log;
        typedResponse.reserve = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.to = (String) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<WithdrawEventResponse> withdrawEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getWithdrawEventFromLog(log));
    }

    public Flowable<WithdrawEventResponse> withdrawEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WITHDRAW_EVENT));
        return withdrawEventFlowable(filter);
    }

    public RemoteFunctionCall<String> ADDRESSES_PROVIDER() {
        final Function function = new Function(FUNC_ADDRESSES_PROVIDER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> BRIDGE_PROTOCOL_FEE() {
        final Function function = new Function(FUNC_BRIDGE_PROTOCOL_FEE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> FLASHLOAN_PREMIUM_TOTAL() {
        final Function function = new Function(FUNC_FLASHLOAN_PREMIUM_TOTAL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint128>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> FLASHLOAN_PREMIUM_TO_PROTOCOL() {
        final Function function = new Function(FUNC_FLASHLOAN_PREMIUM_TO_PROTOCOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint128>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> MAX_NUMBER_RESERVES() {
        final Function function = new Function(FUNC_MAX_NUMBER_RESERVES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint16>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> MAX_STABLE_RATE_BORROW_SIZE_PERCENT() {
        final Function function = new Function(FUNC_MAX_STABLE_RATE_BORROW_SIZE_PERCENT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> backUnbacked(String asset, BigInteger amount, BigInteger fee) {
        final Function function = new Function(
                FUNC_BACKUNBACKED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset), 
                new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.generated.Uint256(fee)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> borrow(String asset, BigInteger amount, BigInteger interestRateMode, BigInteger referralCode, String onBehalfOf) {
        final Function function = new Function(
                FUNC_BORROW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset), 
                new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.generated.Uint256(interestRateMode), 
                new org.web3j.abi.datatypes.generated.Uint16(referralCode), 
                new org.web3j.abi.datatypes.Address(160, onBehalfOf)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> configureEModeCategory(BigInteger id, EModeCategory config) {
        final Function function = new Function(
                FUNC_CONFIGUREEMODECATEGORY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(id), 
                config), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> deposit(String asset, BigInteger amount, String onBehalfOf, BigInteger referralCode) {
        final Function function = new Function(
                FUNC_DEPOSIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset), 
                new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.Address(160, onBehalfOf), 
                new org.web3j.abi.datatypes.generated.Uint16(referralCode)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> dropReserve(String asset) {
        final Function function = new Function(
                FUNC_DROPRESERVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> finalizeTransfer(String asset, String from, String to, BigInteger amount, BigInteger balanceFromBefore, BigInteger balanceToBefore) {
        final Function function = new Function(
                FUNC_FINALIZETRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset), 
                new org.web3j.abi.datatypes.Address(160, from), 
                new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.generated.Uint256(balanceFromBefore), 
                new org.web3j.abi.datatypes.generated.Uint256(balanceToBefore)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> flashLoan(String receiverAddress, List<String> assets, List<BigInteger> amounts, List<BigInteger> interestRateModes, String onBehalfOf, byte[] params, BigInteger referralCode) {
        final Function function = new Function(
                FUNC_FLASHLOAN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, receiverAddress), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(assets, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(amounts, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(interestRateModes, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.Address(160, onBehalfOf), 
                new org.web3j.abi.datatypes.DynamicBytes(params), 
                new org.web3j.abi.datatypes.generated.Uint16(referralCode)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> flashLoanSimple(String receiverAddress, String asset, BigInteger amount, byte[] params, BigInteger referralCode) {
        final Function function = new Function(
                FUNC_FLASHLOANSIMPLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, receiverAddress), 
                new org.web3j.abi.datatypes.Address(160, asset), 
                new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.DynamicBytes(params), 
                new org.web3j.abi.datatypes.generated.Uint16(referralCode)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<ReserveConfigurationMap> getConfiguration(String asset) {
        final Function function = new Function(FUNC_GETCONFIGURATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<ReserveConfigurationMap>() {}));
        return executeRemoteCallSingleValueReturn(function, ReserveConfigurationMap.class);
    }

    public RemoteFunctionCall<EModeCategory> getEModeCategoryData(BigInteger id) {
        final Function function = new Function(FUNC_GETEMODECATEGORYDATA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<EModeCategory>() {}));
        return executeRemoteCallSingleValueReturn(function, EModeCategory.class);
    }

    public RemoteFunctionCall<String> getReserveAddressById(BigInteger id) {
        final Function function = new Function(FUNC_GETRESERVEADDRESSBYID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint16(id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<ReserveData> getReserveData(String asset) {
        final Function function = new Function(FUNC_GETRESERVEDATA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<ReserveData>() {}));
        return executeRemoteCallSingleValueReturn(function, ReserveData.class);
    }

    public RemoteFunctionCall<BigInteger> getReserveNormalizedIncome(String asset) {
        final Function function = new Function(FUNC_GETRESERVENORMALIZEDINCOME, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getReserveNormalizedVariableDebt(String asset) {
        final Function function = new Function(FUNC_GETRESERVENORMALIZEDVARIABLEDEBT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<List> getReservesList() {
        final Function function = new Function(FUNC_GETRESERVESLIST, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
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

    public RemoteFunctionCall<Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>> getUserAccountData(String user) {
        final Function function = new Function(FUNC_GETUSERACCOUNTDATA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, user)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue());
                    }
                });
    }

    public RemoteFunctionCall<UserConfigurationMap> getUserConfiguration(String user) {
        final Function function = new Function(FUNC_GETUSERCONFIGURATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, user)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<UserConfigurationMap>() {}));
        return executeRemoteCallSingleValueReturn(function, UserConfigurationMap.class);
    }

    public RemoteFunctionCall<BigInteger> getUserEMode(String user) {
        final Function function = new Function(FUNC_GETUSEREMODE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, user)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> initReserve(String asset, String aTokenAddress, String stableDebtAddress, String variableDebtAddress, String interestRateStrategyAddress) {
        final Function function = new Function(
                FUNC_INITRESERVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset), 
                new org.web3j.abi.datatypes.Address(160, aTokenAddress), 
                new org.web3j.abi.datatypes.Address(160, stableDebtAddress), 
                new org.web3j.abi.datatypes.Address(160, variableDebtAddress), 
                new org.web3j.abi.datatypes.Address(160, interestRateStrategyAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> liquidationCall(String collateralAsset, String debtAsset, String user, BigInteger debtToCover, Boolean receiveAToken) {
        final Function function = new Function(
                FUNC_LIQUIDATIONCALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, collateralAsset), 
                new org.web3j.abi.datatypes.Address(160, debtAsset), 
                new org.web3j.abi.datatypes.Address(160, user), 
                new org.web3j.abi.datatypes.generated.Uint256(debtToCover), 
                new org.web3j.abi.datatypes.Bool(receiveAToken)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> mintToTreasury(List<String> assets) {
        final Function function = new Function(
                FUNC_MINTTOTREASURY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(assets, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> mintUnbacked(String asset, BigInteger amount, String onBehalfOf, BigInteger referralCode) {
        final Function function = new Function(
                FUNC_MINTUNBACKED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset), 
                new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.Address(160, onBehalfOf), 
                new org.web3j.abi.datatypes.generated.Uint16(referralCode)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> rebalanceStableBorrowRate(String asset, String user) {
        final Function function = new Function(
                FUNC_REBALANCESTABLEBORROWRATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset), 
                new org.web3j.abi.datatypes.Address(160, user)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> repay(String asset, BigInteger amount, BigInteger interestRateMode, String onBehalfOf) {
        final Function function = new Function(
                FUNC_REPAY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset), 
                new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.generated.Uint256(interestRateMode), 
                new org.web3j.abi.datatypes.Address(160, onBehalfOf)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> repayWithATokens(String asset, BigInteger amount, BigInteger interestRateMode) {
        final Function function = new Function(
                FUNC_REPAYWITHATOKENS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset), 
                new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.generated.Uint256(interestRateMode)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> repayWithPermit(String asset, BigInteger amount, BigInteger interestRateMode, String onBehalfOf, BigInteger deadline, BigInteger permitV, byte[] permitR, byte[] permitS) {
        final Function function = new Function(
                FUNC_REPAYWITHPERMIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset), 
                new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.generated.Uint256(interestRateMode), 
                new org.web3j.abi.datatypes.Address(160, onBehalfOf), 
                new org.web3j.abi.datatypes.generated.Uint256(deadline), 
                new org.web3j.abi.datatypes.generated.Uint8(permitV), 
                new org.web3j.abi.datatypes.generated.Bytes32(permitR), 
                new org.web3j.abi.datatypes.generated.Bytes32(permitS)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> rescueTokens(String token, String to, BigInteger amount) {
        final Function function = new Function(
                FUNC_RESCUETOKENS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, token), 
                new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> resetIsolationModeTotalDebt(String asset) {
        final Function function = new Function(
                FUNC_RESETISOLATIONMODETOTALDEBT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setConfiguration(String asset, ReserveConfigurationMap configuration) {
        final Function function = new Function(
                FUNC_SETCONFIGURATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset), 
                configuration), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setReserveInterestRateStrategyAddress(String asset, String rateStrategyAddress) {
        final Function function = new Function(
                FUNC_SETRESERVEINTERESTRATESTRATEGYADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset), 
                new org.web3j.abi.datatypes.Address(160, rateStrategyAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setUserEMode(BigInteger categoryId) {
        final Function function = new Function(
                FUNC_SETUSEREMODE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(categoryId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setUserUseReserveAsCollateral(String asset, Boolean useAsCollateral) {
        final Function function = new Function(
                FUNC_SETUSERUSERESERVEASCOLLATERAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset), 
                new org.web3j.abi.datatypes.Bool(useAsCollateral)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> supply(String asset, BigInteger amount, String onBehalfOf, BigInteger referralCode) {
        final Function function = new Function(
                FUNC_SUPPLY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset), 
                new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.Address(160, onBehalfOf), 
                new org.web3j.abi.datatypes.generated.Uint16(referralCode)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> supplyWithPermit(String asset, BigInteger amount, String onBehalfOf, BigInteger referralCode, BigInteger deadline, BigInteger permitV, byte[] permitR, byte[] permitS) {
        final Function function = new Function(
                FUNC_SUPPLYWITHPERMIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset), 
                new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.Address(160, onBehalfOf), 
                new org.web3j.abi.datatypes.generated.Uint16(referralCode), 
                new org.web3j.abi.datatypes.generated.Uint256(deadline), 
                new org.web3j.abi.datatypes.generated.Uint8(permitV), 
                new org.web3j.abi.datatypes.generated.Bytes32(permitR), 
                new org.web3j.abi.datatypes.generated.Bytes32(permitS)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> swapBorrowRateMode(String asset, BigInteger interestRateMode) {
        final Function function = new Function(
                FUNC_SWAPBORROWRATEMODE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset), 
                new org.web3j.abi.datatypes.generated.Uint256(interestRateMode)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateBridgeProtocolFee(BigInteger bridgeProtocolFee) {
        final Function function = new Function(
                FUNC_UPDATEBRIDGEPROTOCOLFEE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(bridgeProtocolFee)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateFlashloanPremiums(BigInteger flashLoanPremiumTotal, BigInteger flashLoanPremiumToProtocol) {
        final Function function = new Function(
                FUNC_UPDATEFLASHLOANPREMIUMS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint128(flashLoanPremiumTotal), 
                new org.web3j.abi.datatypes.generated.Uint128(flashLoanPremiumToProtocol)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdraw(String asset, BigInteger amount, String to) {
        final Function function = new Function(
                FUNC_WITHDRAW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset), 
                new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.Address(160, to)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static IPool load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IPool(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IPool load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IPool(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IPool load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new IPool(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IPool load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IPool(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<IPool> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IPool.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<IPool> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IPool.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<IPool> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IPool.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<IPool> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IPool.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class EModeCategory extends DynamicStruct {
        public BigInteger ltv;

        public BigInteger liquidationThreshold;

        public BigInteger liquidationBonus;

        public String priceSource;

        public String label;

        public EModeCategory(BigInteger ltv, BigInteger liquidationThreshold, BigInteger liquidationBonus, String priceSource, String label) {
            super(new org.web3j.abi.datatypes.generated.Uint16(ltv), 
                    new org.web3j.abi.datatypes.generated.Uint16(liquidationThreshold), 
                    new org.web3j.abi.datatypes.generated.Uint16(liquidationBonus), 
                    new org.web3j.abi.datatypes.Address(160, priceSource), 
                    new org.web3j.abi.datatypes.Utf8String(label));
            this.ltv = ltv;
            this.liquidationThreshold = liquidationThreshold;
            this.liquidationBonus = liquidationBonus;
            this.priceSource = priceSource;
            this.label = label;
        }

        public EModeCategory(Uint16 ltv, Uint16 liquidationThreshold, Uint16 liquidationBonus, Address priceSource, Utf8String label) {
            super(ltv, liquidationThreshold, liquidationBonus, priceSource, label);
            this.ltv = ltv.getValue();
            this.liquidationThreshold = liquidationThreshold.getValue();
            this.liquidationBonus = liquidationBonus.getValue();
            this.priceSource = priceSource.getValue();
            this.label = label.getValue();
        }
    }

    public static class ReserveConfigurationMap extends StaticStruct {
        public BigInteger data;

        public ReserveConfigurationMap(BigInteger data) {
            super(new org.web3j.abi.datatypes.generated.Uint256(data));
            this.data = data;
        }

        public ReserveConfigurationMap(Uint256 data) {
            super(data);
            this.data = data.getValue();
        }
    }

    public static class UserConfigurationMap extends StaticStruct {
        public BigInteger data;

        public UserConfigurationMap(BigInteger data) {
            super(new org.web3j.abi.datatypes.generated.Uint256(data));
            this.data = data;
        }

        public UserConfigurationMap(Uint256 data) {
            super(data);
            this.data = data.getValue();
        }
    }

    public static class ReserveData extends StaticStruct {
        public ReserveConfigurationMap configuration;

        public BigInteger liquidityIndex;

        public BigInteger currentLiquidityRate;

        public BigInteger variableBorrowIndex;

        public BigInteger currentVariableBorrowRate;

        public BigInteger currentStableBorrowRate;

        public BigInteger lastUpdateTimestamp;

        public BigInteger id;

        public String aTokenAddress;

        public String stableDebtTokenAddress;

        public String variableDebtTokenAddress;

        public String interestRateStrategyAddress;

        public BigInteger accruedToTreasury;

        public BigInteger unbacked;

        public BigInteger isolationModeTotalDebt;

        public ReserveData(ReserveConfigurationMap configuration, BigInteger liquidityIndex, BigInteger currentLiquidityRate, BigInteger variableBorrowIndex, BigInteger currentVariableBorrowRate, BigInteger currentStableBorrowRate, BigInteger lastUpdateTimestamp, BigInteger id, String aTokenAddress, String stableDebtTokenAddress, String variableDebtTokenAddress, String interestRateStrategyAddress, BigInteger accruedToTreasury, BigInteger unbacked, BigInteger isolationModeTotalDebt) {
            super(configuration, 
                    new org.web3j.abi.datatypes.generated.Uint128(liquidityIndex), 
                    new org.web3j.abi.datatypes.generated.Uint128(currentLiquidityRate), 
                    new org.web3j.abi.datatypes.generated.Uint128(variableBorrowIndex), 
                    new org.web3j.abi.datatypes.generated.Uint128(currentVariableBorrowRate), 
                    new org.web3j.abi.datatypes.generated.Uint128(currentStableBorrowRate), 
                    new org.web3j.abi.datatypes.generated.Uint40(lastUpdateTimestamp), 
                    new org.web3j.abi.datatypes.generated.Uint16(id), 
                    new org.web3j.abi.datatypes.Address(160, aTokenAddress), 
                    new org.web3j.abi.datatypes.Address(160, stableDebtTokenAddress), 
                    new org.web3j.abi.datatypes.Address(160, variableDebtTokenAddress), 
                    new org.web3j.abi.datatypes.Address(160, interestRateStrategyAddress), 
                    new org.web3j.abi.datatypes.generated.Uint128(accruedToTreasury), 
                    new org.web3j.abi.datatypes.generated.Uint128(unbacked), 
                    new org.web3j.abi.datatypes.generated.Uint128(isolationModeTotalDebt));
            this.configuration = configuration;
            this.liquidityIndex = liquidityIndex;
            this.currentLiquidityRate = currentLiquidityRate;
            this.variableBorrowIndex = variableBorrowIndex;
            this.currentVariableBorrowRate = currentVariableBorrowRate;
            this.currentStableBorrowRate = currentStableBorrowRate;
            this.lastUpdateTimestamp = lastUpdateTimestamp;
            this.id = id;
            this.aTokenAddress = aTokenAddress;
            this.stableDebtTokenAddress = stableDebtTokenAddress;
            this.variableDebtTokenAddress = variableDebtTokenAddress;
            this.interestRateStrategyAddress = interestRateStrategyAddress;
            this.accruedToTreasury = accruedToTreasury;
            this.unbacked = unbacked;
            this.isolationModeTotalDebt = isolationModeTotalDebt;
        }

        public ReserveData(ReserveConfigurationMap configuration, Uint128 liquidityIndex, Uint128 currentLiquidityRate, Uint128 variableBorrowIndex, Uint128 currentVariableBorrowRate, Uint128 currentStableBorrowRate, Uint40 lastUpdateTimestamp, Uint16 id, Address aTokenAddress, Address stableDebtTokenAddress, Address variableDebtTokenAddress, Address interestRateStrategyAddress, Uint128 accruedToTreasury, Uint128 unbacked, Uint128 isolationModeTotalDebt) {
            super(configuration, liquidityIndex, currentLiquidityRate, variableBorrowIndex, currentVariableBorrowRate, currentStableBorrowRate, lastUpdateTimestamp, id, aTokenAddress, stableDebtTokenAddress, variableDebtTokenAddress, interestRateStrategyAddress, accruedToTreasury, unbacked, isolationModeTotalDebt);
            this.configuration = configuration;
            this.liquidityIndex = liquidityIndex.getValue();
            this.currentLiquidityRate = currentLiquidityRate.getValue();
            this.variableBorrowIndex = variableBorrowIndex.getValue();
            this.currentVariableBorrowRate = currentVariableBorrowRate.getValue();
            this.currentStableBorrowRate = currentStableBorrowRate.getValue();
            this.lastUpdateTimestamp = lastUpdateTimestamp.getValue();
            this.id = id.getValue();
            this.aTokenAddress = aTokenAddress.getValue();
            this.stableDebtTokenAddress = stableDebtTokenAddress.getValue();
            this.variableDebtTokenAddress = variableDebtTokenAddress.getValue();
            this.interestRateStrategyAddress = interestRateStrategyAddress.getValue();
            this.accruedToTreasury = accruedToTreasury.getValue();
            this.unbacked = unbacked.getValue();
            this.isolationModeTotalDebt = isolationModeTotalDebt.getValue();
        }
    }

    public static class BackUnbackedEventResponse extends BaseEventResponse {
        public String reserve;

        public String backer;

        public BigInteger amount;

        public BigInteger fee;
    }

    public static class BorrowEventResponse extends BaseEventResponse {
        public String reserve;

        public String onBehalfOf;

        public BigInteger referralCode;

        public String user;

        public BigInteger amount;

        public BigInteger interestRateMode;

        public BigInteger borrowRate;
    }

    public static class FlashLoanEventResponse extends BaseEventResponse {
        public String target;

        public String asset;

        public BigInteger referralCode;

        public String initiator;

        public BigInteger amount;

        public BigInteger interestRateMode;

        public BigInteger premium;
    }

    public static class IsolationModeTotalDebtUpdatedEventResponse extends BaseEventResponse {
        public String asset;

        public BigInteger totalDebt;
    }

    public static class LiquidationCallEventResponse extends BaseEventResponse {
        public String collateralAsset;

        public String debtAsset;

        public String user;

        public BigInteger debtToCover;

        public BigInteger liquidatedCollateralAmount;

        public String liquidator;

        public Boolean receiveAToken;
    }

    public static class MintUnbackedEventResponse extends BaseEventResponse {
        public String reserve;

        public String onBehalfOf;

        public BigInteger referralCode;

        public String user;

        public BigInteger amount;
    }

    public static class MintedToTreasuryEventResponse extends BaseEventResponse {
        public String reserve;

        public BigInteger amountMinted;
    }

    public static class RebalanceStableBorrowRateEventResponse extends BaseEventResponse {
        public String reserve;

        public String user;
    }

    public static class RepayEventResponse extends BaseEventResponse {
        public String reserve;

        public String user;

        public String repayer;

        public BigInteger amount;

        public Boolean useATokens;
    }

    public static class ReserveDataUpdatedEventResponse extends BaseEventResponse {
        public String reserve;

        public BigInteger liquidityRate;

        public BigInteger stableBorrowRate;

        public BigInteger variableBorrowRate;

        public BigInteger liquidityIndex;

        public BigInteger variableBorrowIndex;
    }

    public static class ReserveUsedAsCollateralDisabledEventResponse extends BaseEventResponse {
        public String reserve;

        public String user;
    }

    public static class ReserveUsedAsCollateralEnabledEventResponse extends BaseEventResponse {
        public String reserve;

        public String user;
    }

    public static class SupplyEventResponse extends BaseEventResponse {
        public String reserve;

        public String onBehalfOf;

        public BigInteger referralCode;

        public String user;

        public BigInteger amount;
    }

    public static class SwapBorrowRateModeEventResponse extends BaseEventResponse {
        public String reserve;

        public String user;

        public BigInteger interestRateMode;
    }

    public static class UserEModeSetEventResponse extends BaseEventResponse {
        public String user;

        public BigInteger categoryId;
    }

    public static class WithdrawEventResponse extends BaseEventResponse {
        public String reserve;

        public String user;

        public String to;

        public BigInteger amount;
    }
}
