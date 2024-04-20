package com.dl.officialsite.bot.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class TelegramVerifyValidator {
    public static final long AUTH_DATE_RANGE_MINUTE = 10;

    public static boolean verifyTelegramParameter(Map<String, String> params, String telegramBotToken) {
        String hash = (String) params.get("hash");
        params.remove("hash");

        // Prepare the string
        String str = params.entrySet().stream()
            .sorted((a, b) -> a.getKey().compareToIgnoreCase(b.getKey()))
            .map(kvp -> kvp.getKey() + "=" + kvp.getValue())
            .collect(Collectors.joining("\n"));

        try {
            long authDate = Long.parseLong(params.get("auth_date"));
            long now = Instant.now().getEpochSecond();
            if (authDate >= now || (now - authDate) > AUTH_DATE_RANGE_MINUTE * 60) {
                log.error("The auth date of telegram request is before {} minutes ago.", AUTH_DATE_RANGE_MINUTE);
                return false;
            }
            SecretKeySpec sk = new SecretKeySpec(
                MessageDigest.getInstance("SHA-256").digest(telegramBotToken.getBytes(StandardCharsets.UTF_8)), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(sk);

            byte[] result = mac.doFinal(str.getBytes(StandardCharsets.UTF_8));
            String resultStr = Hex.encodeHexString(result);

            return StringUtils.equalsIgnoreCase(hash, resultStr);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}