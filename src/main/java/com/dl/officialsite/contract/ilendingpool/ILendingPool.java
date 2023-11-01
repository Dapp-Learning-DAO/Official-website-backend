package com.dl.officialsite.contract.ilendingpool;

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
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
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
public class ILendingPool extends Contract {
    public static final String BINARY = "";

    public static final String FUNC_BORROW = "borrow";

    public static final String FUNC_DEPOSIT = "deposit";

    public static final String FUNC_FINALIZETRANSFER = "finalizeTransfer";

    public static final String FUNC_FLASHLOAN = "flashLoan";

    public static final String FUNC_GETADDRESSESPROVIDER = "getAddressesProvider";

    public static final String FUNC_GETCONFIGURATION = "getConfiguration";

    public static final String FUNC_GETRESERVEDATA = "getReserveData";

    public static final String FUNC_GETRESERVENORMALIZEDINCOME = "getReserveNormalizedIncome";

    public static final String FUNC_GETRESERVENORMALIZEDVARIABLEDEBT = "getReserveNormalizedVariableDebt";

    public static final String FUNC_GETRESERVESLIST = "getReservesList";

    public static final String FUNC_GETUSERACCOUNTDATA = "getUserAccountData";

    public static final String FUNC_GETUSERCONFIGURATION = "getUserConfiguration";

    public static final String FUNC_INITRESERVE = "initReserve";

    public static final String FUNC_LIQUIDATIONCALL = "liquidationCall";

    public static final String FUNC_PAUSED = "paused";

    public static final String FUNC_REBALANCESTABLEBORROWRATE = "rebalanceStableBorrowRate";

    public static final String FUNC_REPAY = "repay";

    public static final String FUNC_SETCONFIGURATION = "setConfiguration";

    public static final String FUNC_SETPAUSE = "setPause";

    public static final String FUNC_SETRESERVEINTERESTRATESTRATEGYADDRESS = "setReserveInterestRateStrategyAddress";

    public static final String FUNC_SETUSERUSERESERVEASCOLLATERAL = "setUserUseReserveAsCollateral";

    public static final String FUNC_SWAPBORROWRATEMODE = "swapBorrowRateMode";

    public static final String FUNC_WITHDRAW = "withdraw";

    public static final Event BORROW_EVENT = new Event("Borrow", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>() {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint16>(true) {}));
    ;

    public static final Event DEPOSIT_EVENT = new Event("Deposit", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>() {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint16>(true) {}));
    ;

    public static final Event FLASHLOAN_EVENT = new Event("FlashLoan", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint16>() {}));
    ;

    public static final Event LIQUIDATIONCALL_EVENT = new Event("LiquidationCall", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event PAUSED_EVENT = new Event("Paused", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event REBALANCESTABLEBORROWRATE_EVENT = new Event("RebalanceStableBorrowRate", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event REPAY_EVENT = new Event("Repay", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
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

    public static final Event SWAP_EVENT = new Event("Swap", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event UNPAUSED_EVENT = new Event("Unpaused", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event WITHDRAW_EVENT = new Event("Withdraw", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected ILendingPool(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ILendingPool(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ILendingPool(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ILendingPool(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<BorrowEventResponse> getBorrowEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(BORROW_EVENT, transactionReceipt);
        ArrayList<BorrowEventResponse> responses = new ArrayList<BorrowEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BorrowEventResponse typedResponse = new BorrowEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.reserve = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.onBehalfOf = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.referral = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.borrowRateMode = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
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
        typedResponse.referral = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.borrowRateMode = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
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

    public static List<DepositEventResponse> getDepositEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(DEPOSIT_EVENT, transactionReceipt);
        ArrayList<DepositEventResponse> responses = new ArrayList<DepositEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DepositEventResponse typedResponse = new DepositEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.reserve = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.onBehalfOf = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.referral = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static DepositEventResponse getDepositEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(DEPOSIT_EVENT, log);
        DepositEventResponse typedResponse = new DepositEventResponse();
        typedResponse.log = log;
        typedResponse.reserve = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.onBehalfOf = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.referral = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<DepositEventResponse> depositEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getDepositEventFromLog(log));
    }

    public Flowable<DepositEventResponse> depositEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DEPOSIT_EVENT));
        return depositEventFlowable(filter);
    }

    public static List<FlashLoanEventResponse> getFlashLoanEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(FLASHLOAN_EVENT, transactionReceipt);
        ArrayList<FlashLoanEventResponse> responses = new ArrayList<FlashLoanEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FlashLoanEventResponse typedResponse = new FlashLoanEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.target = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.initiator = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.asset = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.premium = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.referralCode = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static FlashLoanEventResponse getFlashLoanEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(FLASHLOAN_EVENT, log);
        FlashLoanEventResponse typedResponse = new FlashLoanEventResponse();
        typedResponse.log = log;
        typedResponse.target = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.initiator = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.asset = (String) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.premium = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.referralCode = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
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

    public static List<PausedEventResponse> getPausedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(PAUSED_EVENT, transactionReceipt);
        ArrayList<PausedEventResponse> responses = new ArrayList<PausedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PausedEventResponse typedResponse = new PausedEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static PausedEventResponse getPausedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(PAUSED_EVENT, log);
        PausedEventResponse typedResponse = new PausedEventResponse();
        typedResponse.log = log;
        return typedResponse;
    }

    public Flowable<PausedEventResponse> pausedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getPausedEventFromLog(log));
    }

    public Flowable<PausedEventResponse> pausedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PAUSED_EVENT));
        return pausedEventFlowable(filter);
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

    public static List<SwapEventResponse> getSwapEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(SWAP_EVENT, transactionReceipt);
        ArrayList<SwapEventResponse> responses = new ArrayList<SwapEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SwapEventResponse typedResponse = new SwapEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.reserve = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.rateMode = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static SwapEventResponse getSwapEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(SWAP_EVENT, log);
        SwapEventResponse typedResponse = new SwapEventResponse();
        typedResponse.log = log;
        typedResponse.reserve = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.rateMode = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<SwapEventResponse> swapEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getSwapEventFromLog(log));
    }

    public Flowable<SwapEventResponse> swapEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SWAP_EVENT));
        return swapEventFlowable(filter);
    }

    public static List<UnpausedEventResponse> getUnpausedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(UNPAUSED_EVENT, transactionReceipt);
        ArrayList<UnpausedEventResponse> responses = new ArrayList<UnpausedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UnpausedEventResponse typedResponse = new UnpausedEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static UnpausedEventResponse getUnpausedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(UNPAUSED_EVENT, log);
        UnpausedEventResponse typedResponse = new UnpausedEventResponse();
        typedResponse.log = log;
        return typedResponse;
    }

    public Flowable<UnpausedEventResponse> unpausedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getUnpausedEventFromLog(log));
    }

    public Flowable<UnpausedEventResponse> unpausedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(UNPAUSED_EVENT));
        return unpausedEventFlowable(filter);
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

    public RemoteFunctionCall<TransactionReceipt> finalizeTransfer(String asset, String from, String to, BigInteger amount, BigInteger balanceFromAfter, BigInteger balanceToBefore) {
        final Function function = new Function(
                FUNC_FINALIZETRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset), 
                new org.web3j.abi.datatypes.Address(160, from), 
                new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.generated.Uint256(balanceFromAfter), 
                new org.web3j.abi.datatypes.generated.Uint256(balanceToBefore)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> flashLoan(String receiverAddress, List<String> assets, List<BigInteger> amounts, List<BigInteger> modes, String onBehalfOf, byte[] params, BigInteger referralCode) {
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
                        org.web3j.abi.Utils.typeMap(modes, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.Address(160, onBehalfOf), 
                new org.web3j.abi.datatypes.DynamicBytes(params), 
                new org.web3j.abi.datatypes.generated.Uint16(referralCode)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> getAddressesProvider() {
        final Function function = new Function(FUNC_GETADDRESSESPROVIDER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<ReserveConfigurationMap> getConfiguration(String asset) {
        final Function function = new Function(FUNC_GETCONFIGURATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<ReserveConfigurationMap>() {}));
        return executeRemoteCallSingleValueReturn(function, ReserveConfigurationMap.class);
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

    public RemoteFunctionCall<TransactionReceipt> initReserve(String reserve, String aTokenAddress, String stableDebtAddress, String variableDebtAddress, String interestRateStrategyAddress) {
        final Function function = new Function(
                FUNC_INITRESERVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, reserve), 
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

    public RemoteFunctionCall<Boolean> paused() {
        final Function function = new Function(FUNC_PAUSED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> rebalanceStableBorrowRate(String asset, String user) {
        final Function function = new Function(
                FUNC_REBALANCESTABLEBORROWRATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset), 
                new org.web3j.abi.datatypes.Address(160, user)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> repay(String asset, BigInteger amount, BigInteger rateMode, String onBehalfOf) {
        final Function function = new Function(
                FUNC_REPAY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset), 
                new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.generated.Uint256(rateMode), 
                new org.web3j.abi.datatypes.Address(160, onBehalfOf)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setConfiguration(String reserve, BigInteger configuration) {
        final Function function = new Function(
                FUNC_SETCONFIGURATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, reserve), 
                new org.web3j.abi.datatypes.generated.Uint256(configuration)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setPause(Boolean val) {
        final Function function = new Function(
                FUNC_SETPAUSE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(val)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setReserveInterestRateStrategyAddress(String reserve, String rateStrategyAddress) {
        final Function function = new Function(
                FUNC_SETRESERVEINTERESTRATESTRATEGYADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, reserve), 
                new org.web3j.abi.datatypes.Address(160, rateStrategyAddress)), 
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

    public RemoteFunctionCall<TransactionReceipt> swapBorrowRateMode(String asset, BigInteger rateMode) {
        final Function function = new Function(
                FUNC_SWAPBORROWRATEMODE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, asset), 
                new org.web3j.abi.datatypes.generated.Uint256(rateMode)), 
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
    public static ILendingPool load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ILendingPool(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ILendingPool load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ILendingPool(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ILendingPool load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ILendingPool(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ILendingPool load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ILendingPool(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ILendingPool> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ILendingPool.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ILendingPool> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ILendingPool.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<ILendingPool> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ILendingPool.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ILendingPool> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ILendingPool.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
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

        public BigInteger variableBorrowIndex;

        public BigInteger currentLiquidityRate;

        public BigInteger currentVariableBorrowRate;

        public BigInteger currentStableBorrowRate;

        public BigInteger lastUpdateTimestamp;

        public String aTokenAddress;

        public String stableDebtTokenAddress;

        public String variableDebtTokenAddress;

        public String interestRateStrategyAddress;

        public BigInteger id;

        public ReserveData(ReserveConfigurationMap configuration, BigInteger liquidityIndex, BigInteger variableBorrowIndex, BigInteger currentLiquidityRate, BigInteger currentVariableBorrowRate, BigInteger currentStableBorrowRate, BigInteger lastUpdateTimestamp, String aTokenAddress, String stableDebtTokenAddress, String variableDebtTokenAddress, String interestRateStrategyAddress, BigInteger id) {
            super(configuration, 
                    new org.web3j.abi.datatypes.generated.Uint128(liquidityIndex), 
                    new org.web3j.abi.datatypes.generated.Uint128(variableBorrowIndex), 
                    new org.web3j.abi.datatypes.generated.Uint128(currentLiquidityRate), 
                    new org.web3j.abi.datatypes.generated.Uint128(currentVariableBorrowRate), 
                    new org.web3j.abi.datatypes.generated.Uint128(currentStableBorrowRate), 
                    new org.web3j.abi.datatypes.generated.Uint40(lastUpdateTimestamp), 
                    new org.web3j.abi.datatypes.Address(160, aTokenAddress), 
                    new org.web3j.abi.datatypes.Address(160, stableDebtTokenAddress), 
                    new org.web3j.abi.datatypes.Address(160, variableDebtTokenAddress), 
                    new org.web3j.abi.datatypes.Address(160, interestRateStrategyAddress), 
                    new org.web3j.abi.datatypes.generated.Uint8(id));
            this.configuration = configuration;
            this.liquidityIndex = liquidityIndex;
            this.variableBorrowIndex = variableBorrowIndex;
            this.currentLiquidityRate = currentLiquidityRate;
            this.currentVariableBorrowRate = currentVariableBorrowRate;
            this.currentStableBorrowRate = currentStableBorrowRate;
            this.lastUpdateTimestamp = lastUpdateTimestamp;
            this.aTokenAddress = aTokenAddress;
            this.stableDebtTokenAddress = stableDebtTokenAddress;
            this.variableDebtTokenAddress = variableDebtTokenAddress;
            this.interestRateStrategyAddress = interestRateStrategyAddress;
            this.id = id;
        }

        public ReserveData(ReserveConfigurationMap configuration, Uint128 liquidityIndex, Uint128 variableBorrowIndex, Uint128 currentLiquidityRate, Uint128 currentVariableBorrowRate, Uint128 currentStableBorrowRate, Uint40 lastUpdateTimestamp, Address aTokenAddress, Address stableDebtTokenAddress, Address variableDebtTokenAddress, Address interestRateStrategyAddress, Uint8 id) {
            super(configuration, liquidityIndex, variableBorrowIndex, currentLiquidityRate, currentVariableBorrowRate, currentStableBorrowRate, lastUpdateTimestamp, aTokenAddress, stableDebtTokenAddress, variableDebtTokenAddress, interestRateStrategyAddress, id);
            this.configuration = configuration;
            this.liquidityIndex = liquidityIndex.getValue();
            this.variableBorrowIndex = variableBorrowIndex.getValue();
            this.currentLiquidityRate = currentLiquidityRate.getValue();
            this.currentVariableBorrowRate = currentVariableBorrowRate.getValue();
            this.currentStableBorrowRate = currentStableBorrowRate.getValue();
            this.lastUpdateTimestamp = lastUpdateTimestamp.getValue();
            this.aTokenAddress = aTokenAddress.getValue();
            this.stableDebtTokenAddress = stableDebtTokenAddress.getValue();
            this.variableDebtTokenAddress = variableDebtTokenAddress.getValue();
            this.interestRateStrategyAddress = interestRateStrategyAddress.getValue();
            this.id = id.getValue();
        }
    }

    public static class BorrowEventResponse extends BaseEventResponse {
        public String reserve;

        public String onBehalfOf;

        public BigInteger referral;

        public String user;

        public BigInteger amount;

        public BigInteger borrowRateMode;

        public BigInteger borrowRate;
    }

    public static class DepositEventResponse extends BaseEventResponse {
        public String reserve;

        public String onBehalfOf;

        public BigInteger referral;

        public String user;

        public BigInteger amount;
    }

    public static class FlashLoanEventResponse extends BaseEventResponse {
        public String target;

        public String initiator;

        public String asset;

        public BigInteger amount;

        public BigInteger premium;

        public BigInteger referralCode;
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

    public static class PausedEventResponse extends BaseEventResponse {
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

    public static class SwapEventResponse extends BaseEventResponse {
        public String reserve;

        public String user;

        public BigInteger rateMode;
    }

    public static class UnpausedEventResponse extends BaseEventResponse {
    }

    public static class WithdrawEventResponse extends BaseEventResponse {
        public String reserve;

        public String user;

        public String to;

        public BigInteger amount;
    }
}
