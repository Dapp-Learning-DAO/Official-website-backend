package com.dl.officialsite.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.web3j.abi.TypeEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Uint;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.security.SecureRandom;

public class SignatureGeneration {

    public static String sign(Credentials credentials, String receiverAddress, BigInteger seed, BigInteger signedAt,
                              BigInteger chainId, String nftAddress) {
        Sign.SignatureData signature =
            Sign.signPrefixedMessage(concatParams(receiverAddress, seed, signedAt, chainId, nftAddress).getBytes(),
                credentials.getEcKeyPair());
        byte[] retval = new byte[65];
        System.arraycopy(signature.getR(), 0, retval, 0, 32);
        System.arraycopy(signature.getS(), 0, retval, 32, 32);
        System.arraycopy(signature.getV(), 0, retval, 64, 1);
        return Numeric.toHexString(retval);
    }

    public static boolean verifySignature(String signature,
                                          String expectedAddress,
                                          String receiverAddress, BigInteger seed, BigInteger signedAt,
                                          BigInteger chainId, String nftAddress) {
        String messageValue = concatParams(receiverAddress, seed, signedAt, chainId, nftAddress);
        String extractedAddress = EcdsaRecoverUtil.verifyMessage(messageValue, signature);
        return StringUtils.equalsIgnoreCase(expectedAddress, extractedAddress);
    }

    public static BigInteger randomSeed() {
        // Create a SecureRandom instance
        SecureRandom random = new SecureRandom();

        // Specify the bit length for the BigInteger
        int bitLength = 128; // You can change this to any size you need

        // Generate a random BigInteger
        return new BigInteger(bitLength, random);
    }

    private static String concatParams(String receiverAddress, BigInteger seed, BigInteger signedAt,
                                       BigInteger chainId, String nftAddress) {
        return Hash.sha3(TypeEncoder.encode(new DynamicStruct(
            new Address(receiverAddress),
            new Uint(seed),
            new Uint(signedAt),
            new Uint(chainId),
            new Address(nftAddress)
        )));
    }


}
