package com.dl.officialsite.login;


import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.common.utils.HttpSessionUtils;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberController;
import com.dl.officialsite.member.MemberRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

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
    public String getNonce( HttpSession session) {
        logger.info(session.getAttributeNames().toString());
        logger.info(session.getId());
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString().replaceAll("-", "");
        session.setAttribute("nonce", uuidAsString);
        return uuidAsString;
    }


    @PostMapping("/check")
    public BaseResponse login(@RequestBody SignInfo sign, HttpSession session) throws SignatureException {

        if(session.getAttribute("nonce")==null) {
            return BaseResponse.failWithReason("1003", "get nonce first");
        }

        if(!checkNonce(sign.getMessage(), (String)session.getAttribute("nonce"))) {
            return BaseResponse.failWithReason("10002", "nonce check failed");
        }
        if (checkSignature(sign)) {
            HttpSessionUtils.putMember(session, sign.getAddress());
           Optional<Member> member =  memberRepository.findByAddress(sign.getAddress());
            if(!member.isPresent()){
                return BaseResponse.successWithData(null);
            }
            return BaseResponse.successWithData(member.get());

        }
        return BaseResponse.failWithReason("1004", "fail to check signature");
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

        JSONObject obj = new JSONObject(message);
        String nonceRecover = obj.getString("Nonce") ;
        return nonce.equals(nonceRecover);
    }


    @GetMapping("/logout")
    public BaseResponse logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.MEMBER_ATTRIBUTE_KEY);
        return  BaseResponse.successWithData(null);
    }
}
