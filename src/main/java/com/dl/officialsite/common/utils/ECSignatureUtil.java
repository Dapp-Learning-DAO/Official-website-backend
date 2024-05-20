package com.dl.officialsite.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.web3j.abi.TypeEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.security.SignatureException;

public class ECSignatureUtil {

    private static final String MESSAGE_PREFIX = "\u0019Ethereum Signed Message:\n32";


    public static String sign(Credentials credentials, String receiverAddress, BigInteger seed, BigInteger signedAt,
                              BigInteger chainId, String nftAddress) {
        Sign.SignatureData signature =
            Sign.signMessage(
                toEthSignedMessageHash(sha3AbiEncodedData(receiverAddress, seed, signedAt, chainId, nftAddress)),
                credentials.getEcKeyPair(), false);

        byte[] retVal = new byte[65];
        System.arraycopy(signature.getR(), 0, retVal, 0, 32);
        System.arraycopy(signature.getS(), 0, retVal, 32, 32);
        System.arraycopy(signature.getV(), 0, retVal, 64, 1);
        return Numeric.toHexString(retVal);
    }

    private static byte[] toEthSignedMessageHash(String sha3AbiEncodedData) {
        byte[] prefixBytes = MESSAGE_PREFIX.getBytes();
        byte[] messageBytes = Numeric.hexStringToByteArray(sha3AbiEncodedData);

        byte[] msgBytes = new byte[MESSAGE_PREFIX.getBytes().length + messageBytes.length];
        System.arraycopy(prefixBytes, 0, msgBytes, 0, prefixBytes.length);
        System.arraycopy(messageBytes, 0, msgBytes, prefixBytes.length, messageBytes.length);

        return Hash.sha3(msgBytes);
    }

    public static BigInteger randomSeed() {
        // Create a SecureRandom instance
        SecureRandom random = new SecureRandom();

        // Specify the bit length for the BigInteger
        int bitLength = 128; // You can change this to any size you need

        // Generate a random BigInteger
        return new BigInteger(bitLength, random);
    }

    /**
     * Sha3 == keccak256
     *
     * @param receiverAddress
     * @param seed
     * @param signedAt
     * @param chainId
     * @param nftAddress
     * @return
     */
    private static String sha3AbiEncodedData(String receiverAddress, BigInteger seed, BigInteger signedAt,
                                             BigInteger chainId, String nftAddress) {
        return Hash.sha3(abiEncode(receiverAddress, seed, signedAt, chainId, nftAddress));
    }

    private static String abiEncode(String receiverAddress, BigInteger seed, BigInteger signedAt,
                                    BigInteger chainId, String nftAddress) {
        return TypeEncoder.encode(new DynamicStruct(
            new Address(receiverAddress),
            new Uint256(seed),
            new Uint256(signedAt),
            new Uint(chainId),
            new Address(nftAddress)
        ));
    }

    public static boolean verifySignature(String signature,
                                          String expectedAddress,
                                          String receiverAddress,
                                          BigInteger seed,
                                          BigInteger signedAt,
                                          BigInteger chainId,
                                          String nftAddress) {
        byte[] ethSignedMessageHash = toEthSignedMessageHash(sha3AbiEncodedData(receiverAddress, seed, signedAt, chainId, nftAddress));

        String extractedAddress = recoverAddress(ethSignedMessageHash, signature);

        return StringUtils.equalsIgnoreCase(expectedAddress, extractedAddress);
    }

    public static String recoverAddress(byte[] digest, String signature) {
        if (signature.startsWith("0x")) {
            signature = signature.substring(2);
        }

        // No need to prepend these strings with 0x because
        // Numeric.hexStringToByteArray() accepts both formats
        String r = signature.substring(0, 64);
        String s = signature.substring(64, 128);
        String v = signature.substring(128, 130);

        BigInteger pubkey = null;
        try {
            pubkey = Sign.signedMessageHashToKey(digest,
                new Sign.SignatureData(
                    Numeric.hexStringToByteArray(v)[0],
                    Numeric.hexStringToByteArray(r),
                    Numeric.hexStringToByteArray(s)));
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        }

        return "0x" + Keys.getAddress(pubkey);
    }

}
