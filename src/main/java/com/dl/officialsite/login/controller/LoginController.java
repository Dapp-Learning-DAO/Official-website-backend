package com.dl.officialsite.login.controller;


import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.common.utils.HttpSessionUtils;
import com.dl.officialsite.login.model.SessionUserInfo;
import com.dl.officialsite.login.model.SignInfo;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberController;
import com.dl.officialsite.member.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.SignatureException;
import java.util.Optional;
import java.util.UUID;

import static org.web3j.crypto.Sign.getEthereumMessageHash;
import static org.web3j.utils.Numeric.hexStringToByteArray;

@RestController
@RequestMapping("/login")
public class LoginController {
    public static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private MemberRepository memberRepository;


    @GetMapping("/nonce")
    public String getNonce( @RequestParam String address, HttpSession session) {
        logger.info(session.getId());
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString().replaceAll("-", "");

        SessionUserInfo userInfo = new SessionUserInfo();
        userInfo.setNonce(uuidAsString);
        userInfo.setAddress(address);
        HttpSessionUtils.putUserInfo(session, userInfo);
        return uuidAsString;
    }


    @PostMapping("/check")
    public BaseResponse login(@RequestBody SignInfo sign, @RequestParam String address, HttpSession session) throws SignatureException {

        if(!HttpSessionUtils.hasNonce(session)){
            return BaseResponse.failWithReason("1003", "get nonce first");
        }

        SessionUserInfo userInfo = HttpSessionUtils.getMember(session);
        if(!checkNonce(sign.getMessage(), userInfo.getNonce())) {
            logger.info( "session nonce: {}", userInfo.getNonce());
            return BaseResponse.failWithReason("10002", "nonce check failed");
        }

        if (!checkSignature(sign)) {
            return BaseResponse.failWithReason("1004", "fail to check signature");
        }
        userInfo.setAddress(sign.getAddress());
        HttpSessionUtils.putUserInfo(session, userInfo);
        Optional<Member> member =  memberRepository.findByAddress(sign.getAddress());
        if(!member.isPresent()){
            return BaseResponse.successWithData(null);
        }
        return BaseResponse.successWithData(member.get());

    }



    private boolean checkSignature(SignInfo sign) throws SignatureException {
        // ECVERIFY
        byte[] v = hexStringToByteArray(sign.getV());
        byte[] r = hexStringToByteArray(sign.getR());
        byte[] s = hexStringToByteArray(sign.getS());
        Sign.SignatureData signatureData = new Sign.SignatureData(v,r ,s);
        BigInteger publicKey = Sign.signedMessageHashToKey(getEthereumMessageHash(sign.getMessage().getBytes()), signatureData);
        String address =  Keys.getAddress(publicKey);
        logger.info("address: "+ address);
        return address.equals(sign.getAddress().substring(2).toLowerCase());

    }


    private boolean checkNonce(String message, String nonce ) {
        int index = message.indexOf("Nonce:");
        String nonceRecover = message.substring(index+7, index+39);
        logger.info( " nonce recover: "+  nonceRecover);
        return nonce.equals(nonceRecover);
    }


    @GetMapping("/logout")
    public BaseResponse logout(@RequestParam String address, HttpSession session) {
        HttpSessionUtils.clearLogin(session);
        return  BaseResponse.successWithData(null);
    }

    @GetMapping("/check-session")
    public BaseResponse checkSessionStatue( HttpServletRequest request) {

        if (request.isRequestedSessionIdValid()) {

            return BaseResponse.successWithData(true) ;
        }
        return  BaseResponse.successWithData(false);
    }
}
