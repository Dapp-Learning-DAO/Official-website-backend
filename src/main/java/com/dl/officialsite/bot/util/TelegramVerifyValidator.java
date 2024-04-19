package com.dl.officialsite.bot.util;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.stream.Collectors;

public class TelegramVerifyValidator {

    public static boolean verifyTelegramParameter(Map<String, String> params, String telegramBotToken){
        String hash = (String) params.get("hash");
        params.remove("hash");

        // Prepare the string
        String str = params.entrySet().stream()
            .sorted((a, b) -> a.getKey().compareToIgnoreCase(b.getKey()))
            .map(kvp -> kvp.getKey() + "=" + kvp.getValue())
            .collect(Collectors.joining("\n"));

        try {
            SecretKeySpec sk = new SecretKeySpec(
                MessageDigest.getInstance("SHA-256").digest(telegramBotToken.getBytes(StandardCharsets.UTF_8)), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(sk);

            byte[] result = mac.doFinal(str.getBytes(StandardCharsets.UTF_8));
            String resultStr = Hex.encodeHexString(result);

            return StringUtils.equalsIgnoreCase(hash, resultStr);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return false;
        }
    }
}