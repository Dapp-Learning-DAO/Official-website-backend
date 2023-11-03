package com.dl.officialsite;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.member.MemberController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;

import java.math.BigInteger;
import java.security.SignatureException;

import static org.web3j.crypto.Sign.getEthereumMessageHash;
import static org.web3j.utils.Numeric.hexStringToByteArray;


class UtilTests {
	public static final Logger logger = LoggerFactory.getLogger(MemberController.class);


	@Test
	public void checkSignature() throws SignatureException {
//		SignInfo sign = new SignInfo("0x2fE023204958fc4c44f639CE72D3bdC0f025Adfe",new BigInteger("1c",16)
//		, new BigInteger("0ea7d9596f0c90d2cf417a079c6c56ef10dda520cf392e89ae5a7bdc1d09c8db",16)
//		,new BigInteger("037f66fde24749ba842ef5de24902451f0fe9baf5cd1b0b9dca42c71baaf98b0",16),"hello");
//		byte[] v = new byte[] {(byte) sign.getV()};
//		byte[] r = Numeric.toBytesPadded(sign.getR(), 32);
//		byte[] s = Numeric.toBytesPadded(sign.getS(), 32);
//		Sign.SignatureData signatureData = new Sign.SignatureData(v,r ,s );
//		BigInteger publicKey = Sign.signedMessageHashToKey(getEthereumMessageHash(sign.getMessage().getBytes()), signatureData);
//		String address =  Keys.getAddress(publicKey);
//		logger.info("*******"+address);
		
	}


	@Test
	public void checkSignature1() throws SignatureException {
		String message = "localhost:3000 wants you to sign in with your Ethereum account:\n" +
				"0x2fE023204958fc4c44f639CE72D3bdC0f025Adfe\n" +
				"\n" +
				"Sign in with Etherem to Dapp-Learning\n" +
				"\n" +
				"URI: http://localhost:3000\n" +
				"Version: 1\n" +
				"Chain ID: 1\n" +
				"Nonce: ABA490FDFFF5742158889682D9308F1AF\n" +
				"Issued At: 2023-10-15T15:35:00.404Z";



		byte[] v1 = hexStringToByteArray("0x1b");
		byte[] r1 = hexStringToByteArray("0x64d251ade78cf78ad3f1b1115421f6ac97eff32c8c2b8404db6484bc3d6fe67f");
		byte[] s1 = hexStringToByteArray("0x2afeb45431847f3fa513921542a18727ec72413b05a8c6c3a39323d4e4095f16");
		Sign.SignatureData signatureData1 = new Sign.SignatureData(v1,r1 ,s1);
		BigInteger publicKey1 = Sign.signedMessageHashToKey(getEthereumMessageHash(message.getBytes()), signatureData1);
		String address1 =  Keys.getAddress(publicKey1);

		logger.info("*******" + address1 + "*******");



	}


	@Test
	public void checkNonce() throws SignatureException {
		String message = "localhost:3000 wants you to sign in with your Ethereum account:\n" +
				"0x2fE023204958fc4c44f639CE72D3bdC0f025Adfe\n" +
				"\n" +
				"Sign in with Etherem to Dapp-Learning\n" +
				"\n" +
				"URI: http://localhost:3000\n" +
				"Version: 1\n" +
				"Chain ID: 1\n" +
				"Nonce: ABA490FDFFF5742158889682D9308F1AF\n" +
				"Issued At: 2023-10-15T15:35:00.404Z";


		//JSONObject obj = new JSONObject(message);
		int index = message.indexOf("Nonce:");
		String nonce = message.substring(index+7, index+39);



		logger.info("*******" + nonce + "*******");

	}



	@Test
	public void objectToJson() throws JsonProcessingException {


		BaseResponse baseResponse = BaseResponse.failWithReason("2001", "please login in");

		ObjectMapper objectMapper =  new  ObjectMapper();

		String s =  objectMapper.writeValueAsString( baseResponse );

		logger.info("*******" + s + "*******");


	}



}
