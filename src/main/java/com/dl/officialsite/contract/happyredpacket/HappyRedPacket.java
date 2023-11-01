package com.dl.officialsite.contract.happyredpacket;

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
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
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
public class HappyRedPacket extends Contract {
    public static final String BINARY = "608060405234801561000f575f80fd5b5061191f8061001d5f395ff3fe608060405260043610610049575f3560e01c80636bfdaece1461004d5780637249fbb6146100aa5780638129fc1c146100cb578063e923849d146100df578063f5c05eb7146100f2575b5f80fd5b348015610058575f80fd5b5061006c610067366004611366565b61011f565b604080516001600160a01b03909716875260208701959095529385019290925260608401521515608083015260a082015260c0015b60405180910390f35b3480156100b5575f80fd5b506100c96100c4366004611366565b6101cb565b005b3480156100d6575f80fd5b506100c96103d2565b6100c96100ed366004611461565b6104e6565b3480156100fd575f80fd5b5061011161010c36600461151a565b6109f4565b6040519081526020016100a1565b5f818152600160208181526040808420815180830183528154815293810154928401839052849384938493849384939092909161015e919060a0610e59565b815161016d9060806060610e59565b61017e836020015160ef600f610e59565b61018f846020015160e0600f610e59565b845161019e9060e06020610e59565b335f9081526002909701602052604090962054939d929c50909a5098504293909311965094509092505050565b5f81815260016020818152604092839020835180850190945280548452918201549083015260048101549091906001600160a01b03163381146102445760405162461bcd60e51b815260206004820152600c60248201526b43726561746f72204f6e6c7960a01b60448201526064015b60405180910390fd5b42610255835f015160e06020610e59565b11156102955760405162461bcd60e51b815260206004820152600f60248201526e139bdd08195e1c1a5c9959081e595d608a1b604482015260640161023b565b5f6102a6835f015160806060610e59565b9050805f036102f75760405162461bcd60e51b815260206004820152601b60248201527f4e6f6e65206c65667420696e2074686520726564207061636b65740000000000604482015260640161023b565b5f610309846020015160fe6001610e59565b90505f61031d8560200151604060a0610e59565b85516bffffffffffffffffffffffff60801b1916875590505f82900361036d57604051339084156108fc029085905f818181858888f19350505050158015610367573d5f803e3d5ffd5b50610380565b8160010361038057610380813385610e68565b604080518881526001600160a01b03831660208201529081018490527f66c304c539e0bc7c8070207c09b9f6a5a9591b434dfed1867cc57fde7fb600939060600160405180910390a150505050505050565b5f54610100900460ff166103eb575f5460ff16156103ef565b303b155b6104525760405162461bcd60e51b815260206004820152602e60248201527f496e697469616c697a61626c653a20636f6e747261637420697320616c72656160448201526d191e481a5b9a5d1a585b1a5e995960921b606482015260840161023b565b5f54610100900460ff16158015610472575f805461ffff19166101011790555b6040517f44617070204c6561726e696e67205265647061636b657400000000000000000060208201524260378201526001600160601b03193360601b166057820152606b0160408051601f19818403018152919052805160209091012060025580156104e3575f805461ff00191690555b50565b5f805462010000900463ffffffff16906002610501836115db565b91906101000a81548163ffffffff021916908363ffffffff16021790555050888110156105655760405162461bcd60e51b815260206004820152601260248201527123746f6b656e73203e20237061636b65747360701b604482015260640161023b565b5f89116105ab5760405162461bcd60e51b8152602060048201526014602482015273105d081b19585cdd080c481c9958da5c1a595b9d60621b604482015260640161023b565b61010089106105f55760405162461bcd60e51b81526020600482015260166024820152754174206d6f73742032353520726563697069656e747360501b604482015260640161023b565b8215806106025750826001145b61064e5760405162461bcd60e51b815260206004820152601960248201527f556e7265636f676e697a61626c6520746f6b656e207479706500000000000000604482015260640161023b565b886001836001600160a01b031663313ce5676040518163ffffffff1660e01b8152600401602060405180830381865afa15801561068d573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906106b191906115fd565b6106bb919061161d565b6106c690600a611716565b6106d09190611724565b811161071e5760405162461bcd60e51b815260206004820152601a60248201527f4174206c6561737420302e3120666f7220656163682075736572000000000000604482015260640161023b565b805f84900361076c57813410156107675760405162461bcd60e51b815260206004820152600d60248201526c09cde40cadcdeeaced0408aa89609b1b604482015260640161023b565b6108b3565b836001036108b3576040516370a0823160e01b81523060048201525f906001600160a01b038516906370a0823190602401602060405180830381865afa1580156107b8573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906107dc919061173b565b90506107f36001600160a01b038516333086610e81565b6040516370a0823160e01b81523060048201525f906001600160a01b038616906370a0823190602401602060405180830381865afa158015610837573d5f803e3d5ffd5b505050506040513d601f19601f8201168201806040525081019061085b919061173b565b90506108678183610ef2565b92508b8310156108b05760405162461bcd60e51b8152602060048201526014602482015273237265636569766564203e20237061636b65747360601b604482015260640161023b565b50505b5f80546002546040513360601b6001600160601b03191660208201524260348201526201000090920460e01b6001600160e01b03191660548301526058820152607881018990526098016040516020818303038152906040528051906020012090505f8a610921575f610924565b60015b5f83815260016020526040902060ff919091169150610943848c610f04565b8155610951868e8985610f32565b815f01600101819055508d816003018190555033816004015f6101000a8154816001600160a01b0302191690836001600160a01b0316021790555050505f8b90505f8b90505f8b90507f86af556fd7cfab9462285ad44f2d5913527c539ff549f74731ca9997ca53401885858b8d33428d8a8a8a6040516109db9a9998979695949392919061179f565b60405180910390a1505050505050505050505050505050565b5f8281526001602081815260408084208151808301909252805480835293810154828401529290914291610a2a9160e090610e59565b11610a615760405162461bcd60e51b8152602060048201526007602482015266115e1c1a5c995960ca1b604482015260640161023b565b5f610a73826020015160ef600f610e59565b90505f610a87836020015160e0600f610e59565b9050818110610ac75760405162461bcd60e51b815260206004820152600c60248201526b4f7574206f662073746f636b60a01b604482015260640161023b565b6003840154610b138782610b0e336040516001600160601b0319606083901b1660208201525f90603401604051602081830303815290604052805190602001209050919050565b610f8c565b610b555760405162461bcd60e51b815260206004820152601360248201527215995c9a599a58d85d1a5bdb8819985a5b1959606a1b604482015260640161023b565b5f80610b68866020015160fe6001610e59565b90505f610b7c876020015160ff6001610e59565b90505f610b8f885f015160806060610e59565b90505f610ba38960200151604060a0610e59565b90505f6001826001600160a01b031663313ce5676040518163ffffffff1660e01b8152600401602060405180830381865afa158015610be4573d5f803e3d5ffd5b505050506040513d601f19601f82011682018060405250810190610c0891906115fd565b610c12919061161d565b610c1d90600a611716565b905083600103610cd457610c31888a611815565b600103610c4057829550610cfe565b5f81610c4c8a8c611815565b610c569190611724565b90505f610c638286611815565b9050610c82610c73826002610fa1565b610c7d8c8e611815565b610fac565b6002545f54610c9d919062010000900463ffffffff16610fb7565b610ca7919061183c565b9750828810610cc957610cba838961183c565b610cc49089611815565b610ccb565b825b97505050610cfe565b610cde888a611815565b600103610ced57829550610cfe565b610cfb83610c7d8a8c611815565b95505b8951610d289060806060610d128a88611815565b821b600190911b5f190190911b19919091161790565b8b55335f90815260028c01602052604090205415610d7a5760405162461bcd60e51b815260206004820152600f60248201526e105b1c9958591e4818db185a5b5959608a1b604482015260640161023b565b335f90815260028c01602090815260409091208790558a0151610da69060e0600f610d128c600161184f565b60018c01555f859003610de357604051339087156108fc029088905f818181858888f19350505050158015610ddd573d5f803e3d5ffd5b50610df6565b84600103610df657610df6823388610e68565b604080518f81523360208201529081018790526001600160a01b03831660608201527f358ddd686a5ca3ef6f8aee9b8d2dc3c642ecc278657c3802f8802b1a44c10e449060800160405180910390a1509399505050505050505050505b92915050565b5f19600190911b0191901c1690565b610e7c6001600160a01b0384168383611016565b505050565b6040516001600160a01b0380851660248301528316604482015260648101829052610eec9085906323b872dd60e01b906084015b60408051601f198184030181529190526020810180516001600160e01b03166001600160e01b031990931692909217909152611046565b50505050565b5f610efd8284611815565b9392505050565b5f80610f136080606086611117565b17610f2a60e06020610f25864261184f565b611117565b179392505050565b5f80610f4a604060a06001600160a01b038916611117565b17610f5860e0600f5f611117565b17610f6660ef600f87611117565b17610f7460fe600186611117565b17610f8260ff600185611117565b1795945050505050565b5f82610f988584611172565b14949350505050565b5f610efd8284611724565b5f610efd8284611862565b6040516001600160e01b031960e083901b1660208201526001600160601b03193360601b166024820152603881018390524260588201525f9060780160408051601f198184030181529190528051602090910120610efd90600161184f565b6040516001600160a01b038316602482015260448101829052610e7c90849063a9059cbb60e01b90606401610eb5565b5f61109a826040518060400160405280602081526020017f5361666545524332303a206c6f772d6c6576656c2063616c6c206661696c6564815250856001600160a01b03166111be9092919063ffffffff16565b805190915015610e7c57808060200190518101906110b89190611875565b610e7c5760405162461bcd60e51b815260206004820152602a60248201527f5361666545524332303a204552433230206f7065726174696f6e20646964206e6044820152691bdd081cdd58d8d9595960b21b606482015260840161023b565b5f6001831b82106101008414176111695760405162461bcd60e51b81526020600482015260166024820152750acc2d8eaca40deeae840decc40e4c2dcceca40849eb60531b604482015260640161023b565b5090911b919050565b5f81815b84518110156111b6576111a28286838151811061119557611195611890565b60200260200101516111d4565b9150806111ae816118a4565b915050611176565b509392505050565b60606111cc84845f85611200565b949350505050565b5f8183106111ee575f828152602084905260409020610efd565b5f838152602083905260409020610efd565b6060824710156112615760405162461bcd60e51b815260206004820152602660248201527f416464726573733a20696e73756666696369656e742062616c616e636520666f6044820152651c8818d85b1b60d21b606482015260840161023b565b6001600160a01b0385163b6112b85760405162461bcd60e51b815260206004820152601d60248201527f416464726573733a2063616c6c20746f206e6f6e2d636f6e7472616374000000604482015260640161023b565b5f80866001600160a01b031685876040516112d391906118bc565b5f6040518083038185875af1925050503d805f811461130d576040519150601f19603f3d011682016040523d82523d5f602084013e611312565b606091505b509150915061132282828661132d565b979650505050505050565b6060831561133c575081610efd565b82511561134c5782518084602001fd5b8160405162461bcd60e51b815260040161023b91906118d7565b5f60208284031215611376575f80fd5b5035919050565b80151581146104e3575f80fd5b80356113958161137d565b919050565b634e487b7160e01b5f52604160045260245ffd5b604051601f8201601f1916810167ffffffffffffffff811182821017156113d7576113d761139a565b604052919050565b5f82601f8301126113ee575f80fd5b813567ffffffffffffffff8111156114085761140861139a565b61141b601f8201601f19166020016113ae565b81815284602083860101111561142f575f80fd5b816020850160208301375f918101602001919091529392505050565b80356001600160a01b0381168114611395575f80fd5b5f805f805f805f805f806101408b8d03121561147b575f80fd5b8a35995060208b0135985061149260408c0161138a565b975060608b0135965060808b0135955060a08b013567ffffffffffffffff808211156114bc575f80fd5b6114c88e838f016113df565b965060c08d01359150808211156114dd575f80fd5b506114ea8d828e016113df565b94505060e08b013592506115016101008c0161144b565b91506101208b013590509295989b9194979a5092959850565b5f806040838503121561152b575f80fd5b8235915060208084013567ffffffffffffffff8082111561154a575f80fd5b818601915086601f83011261155d575f80fd5b81358181111561156f5761156f61139a565b8060051b91506115808483016113ae565b8181529183018401918481019089841115611599575f80fd5b938501935b838510156115b75784358252938501939085019061159e565b8096505050505050509250929050565b634e487b7160e01b5f52601160045260245ffd5b5f63ffffffff8083168181036115f3576115f36115c7565b6001019392505050565b5f6020828403121561160d575f80fd5b815160ff81168114610efd575f80fd5b60ff8281168282160390811115610e5357610e536115c7565b600181815b8085111561167057815f1904821115611656576116566115c7565b8085161561166357918102915b93841c939080029061163b565b509250929050565b5f8261168657506001610e53565b8161169257505f610e53565b81600181146116a857600281146116b2576116ce565b6001915050610e53565b60ff8411156116c3576116c36115c7565b50506001821b610e53565b5060208310610133831016604e8410600b84101617156116f1575081810a610e53565b6116fb8383611636565b805f190482111561170e5761170e6115c7565b029392505050565b5f610efd60ff841683611678565b8082028115828204841417610e5357610e536115c7565b5f6020828403121561174b575f80fd5b5051919050565b5f5b8381101561176c578181015183820152602001611754565b50505f910152565b5f815180845261178b816020860160208601611752565b601f01601f19169290920160200192915050565b5f6101408c83528b60208401528060408401526117be8184018c611774565b905082810360608401526117d2818b611774565b6001600160a01b03998a16608085015260a084019890985250509390951660c084015260e083019190915215156101008201526101200191909152949350505050565b81810381811115610e5357610e536115c7565b634e487b7160e01b5f52601260045260245ffd5b5f8261184a5761184a611828565b500690565b80820180821115610e5357610e536115c7565b5f8261187057611870611828565b500490565b5f60208284031215611885575f80fd5b8151610efd8161137d565b634e487b7160e01b5f52603260045260245ffd5b5f600182016118b5576118b56115c7565b5060010190565b5f82516118cd818460208701611752565b9190910192915050565b602081525f610efd602083018461177456fea264697066735822122062f06af3f0c3e6c234a04b0db9df68c5955e6861b981123dc82b5d157ff8db0d64736f6c63430008140033";

    public static final String FUNC_CHECK_AVAILABILITY = "check_availability";

    public static final String FUNC_CLAIM = "claim";

    public static final String FUNC_CREATE_RED_PACKET = "create_red_packet";

    public static final String FUNC_INITIALIZE = "initialize";

    public static final String FUNC_REFUND = "refund";

    public static final Event CLAIMSUCCESS_EVENT = new Event("ClaimSuccess", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event CREATIONSUCCESS_EVENT = new Event("CreationSuccess", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event REFUNDSUCCESS_EVENT = new Event("RefundSuccess", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected HappyRedPacket(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected HappyRedPacket(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected HappyRedPacket(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected HappyRedPacket(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ClaimSuccessEventResponse> getClaimSuccessEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CLAIMSUCCESS_EVENT, transactionReceipt);
        ArrayList<ClaimSuccessEventResponse> responses = new ArrayList<ClaimSuccessEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ClaimSuccessEventResponse typedResponse = new ClaimSuccessEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.claimer = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.claimed_value = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.token_address = (String) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ClaimSuccessEventResponse getClaimSuccessEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CLAIMSUCCESS_EVENT, log);
        ClaimSuccessEventResponse typedResponse = new ClaimSuccessEventResponse();
        typedResponse.log = log;
        typedResponse.id = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.claimer = (String) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.claimed_value = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        typedResponse.token_address = (String) eventValues.getNonIndexedValues().get(3).getValue();
        return typedResponse;
    }

    public Flowable<ClaimSuccessEventResponse> claimSuccessEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getClaimSuccessEventFromLog(log));
    }

    public Flowable<ClaimSuccessEventResponse> claimSuccessEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLAIMSUCCESS_EVENT));
        return claimSuccessEventFlowable(filter);
    }

    public static List<CreationSuccessEventResponse> getCreationSuccessEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CREATIONSUCCESS_EVENT, transactionReceipt);
        ArrayList<CreationSuccessEventResponse> responses = new ArrayList<CreationSuccessEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CreationSuccessEventResponse typedResponse = new CreationSuccessEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.total = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.id = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.name = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.message = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.creator = (String) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.creation_time = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
            typedResponse.token_address = (String) eventValues.getNonIndexedValues().get(6).getValue();
            typedResponse.number = (BigInteger) eventValues.getNonIndexedValues().get(7).getValue();
            typedResponse.ifrandom = (Boolean) eventValues.getNonIndexedValues().get(8).getValue();
            typedResponse.duration = (BigInteger) eventValues.getNonIndexedValues().get(9).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static CreationSuccessEventResponse getCreationSuccessEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CREATIONSUCCESS_EVENT, log);
        CreationSuccessEventResponse typedResponse = new CreationSuccessEventResponse();
        typedResponse.log = log;
        typedResponse.total = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.id = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.name = (String) eventValues.getNonIndexedValues().get(2).getValue();
        typedResponse.message = (String) eventValues.getNonIndexedValues().get(3).getValue();
        typedResponse.creator = (String) eventValues.getNonIndexedValues().get(4).getValue();
        typedResponse.creation_time = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
        typedResponse.token_address = (String) eventValues.getNonIndexedValues().get(6).getValue();
        typedResponse.number = (BigInteger) eventValues.getNonIndexedValues().get(7).getValue();
        typedResponse.ifrandom = (Boolean) eventValues.getNonIndexedValues().get(8).getValue();
        typedResponse.duration = (BigInteger) eventValues.getNonIndexedValues().get(9).getValue();
        return typedResponse;
    }

    public Flowable<CreationSuccessEventResponse> creationSuccessEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getCreationSuccessEventFromLog(log));
    }

    public Flowable<CreationSuccessEventResponse> creationSuccessEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CREATIONSUCCESS_EVENT));
        return creationSuccessEventFlowable(filter);
    }

    public static List<RefundSuccessEventResponse> getRefundSuccessEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(REFUNDSUCCESS_EVENT, transactionReceipt);
        ArrayList<RefundSuccessEventResponse> responses = new ArrayList<RefundSuccessEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RefundSuccessEventResponse typedResponse = new RefundSuccessEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.token_address = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.remaining_balance = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static RefundSuccessEventResponse getRefundSuccessEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(REFUNDSUCCESS_EVENT, log);
        RefundSuccessEventResponse typedResponse = new RefundSuccessEventResponse();
        typedResponse.log = log;
        typedResponse.id = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.token_address = (String) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.remaining_balance = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<RefundSuccessEventResponse> refundSuccessEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getRefundSuccessEventFromLog(log));
    }

    public Flowable<RefundSuccessEventResponse> refundSuccessEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REFUNDSUCCESS_EVENT));
        return refundSuccessEventFlowable(filter);
    }

    public RemoteFunctionCall<Tuple6<String, BigInteger, BigInteger, BigInteger, Boolean, BigInteger>> check_availability(byte[] id) {
        final Function function = new Function(FUNC_CHECK_AVAILABILITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple6<String, BigInteger, BigInteger, BigInteger, Boolean, BigInteger>>(function,
                new Callable<Tuple6<String, BigInteger, BigInteger, BigInteger, Boolean, BigInteger>>() {
                    @Override
                    public Tuple6<String, BigInteger, BigInteger, BigInteger, Boolean, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<String, BigInteger, BigInteger, BigInteger, Boolean, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (Boolean) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> claim(byte[] id, List<byte[]> proof) {
        final Function function = new Function(
                FUNC_CLAIM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(id), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(proof, org.web3j.abi.datatypes.generated.Bytes32.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> create_red_packet(byte[] _merkleroot, BigInteger _number, Boolean _ifrandom, BigInteger _duration, byte[] _seed, String _message, String _name, BigInteger _token_type, String _token_addr, BigInteger _total_tokens, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_CREATE_RED_PACKET, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_merkleroot), 
                new org.web3j.abi.datatypes.generated.Uint256(_number), 
                new org.web3j.abi.datatypes.Bool(_ifrandom), 
                new org.web3j.abi.datatypes.generated.Uint256(_duration), 
                new org.web3j.abi.datatypes.generated.Bytes32(_seed), 
                new org.web3j.abi.datatypes.Utf8String(_message), 
                new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.generated.Uint256(_token_type), 
                new org.web3j.abi.datatypes.Address(160, _token_addr), 
                new org.web3j.abi.datatypes.generated.Uint256(_total_tokens)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> initialize() {
        final Function function = new Function(
                FUNC_INITIALIZE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> refund(byte[] id) {
        final Function function = new Function(
                FUNC_REFUND, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static HappyRedPacket load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new HappyRedPacket(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static HappyRedPacket load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new HappyRedPacket(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static HappyRedPacket load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new HappyRedPacket(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static HappyRedPacket load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new HappyRedPacket(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<HappyRedPacket> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(HappyRedPacket.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<HappyRedPacket> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(HappyRedPacket.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<HappyRedPacket> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(HappyRedPacket.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<HappyRedPacket> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(HappyRedPacket.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class ClaimSuccessEventResponse extends BaseEventResponse {
        public byte[] id;

        public String claimer;

        public BigInteger claimed_value;

        public String token_address;
    }

    public static class CreationSuccessEventResponse extends BaseEventResponse {
        public BigInteger total;

        public byte[] id;

        public String name;

        public String message;

        public String creator;

        public BigInteger creation_time;

        public String token_address;

        public BigInteger number;

        public Boolean ifrandom;

        public BigInteger duration;
    }

    public static class RefundSuccessEventResponse extends BaseEventResponse {
        public byte[] id;

        public String token_address;

        public BigInteger remaining_balance;
    }
}
