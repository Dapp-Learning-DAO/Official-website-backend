package com.dl.officialsite.login;


import com.dl.officialsite.member.MemberController;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.SignatureException;
import java.util.UUID;

@RestController
@RequestMapping("/login")
public class LoginController {
    public static final Logger logger = LoggerFactory.getLogger(MemberController.class);


    @GetMapping("/nonce")
    public String getNonce( HttpSession session) {
        logger.info(session.getAttributeNames().toString());
        logger.info(session.getId());
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        session.setAttribute("nonce", uuidAsString);
        return uuidAsString;
    }


    @PostMapping("/check")
    public String login(@RequestBody SignInfo sign, HttpSession session) throws SignatureException {
        logger.info(session.getAttributeNames().toString());
        logger.info(session.getId());

        if(!checkNoce(sign.getMessage(), (String)session.getAttribute("nonce"))) {
            return "fail to check nonce";
        }
        if (checkSignature(sign)) {
            session.setAttribute("member", sign.getAddress());
            return "log in successfully";
        }
        return "fail to check sign message";
    }



    private boolean checkSignature(SignInfo sign) throws SignatureException {
        // ECVERIFY
        byte[] v = new byte[] {(byte) sign.getV()};
        byte[] r = Numeric.toBytesPadded(sign.getR(), 32);
        byte[] s = Numeric.toBytesPadded(sign.getS(), 32);
        Sign.SignatureData signatureData = new Sign.SignatureData(v,r ,s );
        BigInteger publicKey = Sign.signedMessageHashToKey(Hash.sha3(sign.getMessage().getBytes()), signatureData);
        String address =  Keys.getAddress(publicKey);
        return address.equals(sign.getAddress());

    }


    private boolean checkNoce(String message, String nonce ) {

        JSONObject obj = new JSONObject(message);
        String nonceRecover = obj.getString("nonce") ;
        return nonce.equals(nonceRecover);
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 退出登录就是将用户信息删除
        session.removeAttribute("member");
        return "log out successfully";
    }


}
