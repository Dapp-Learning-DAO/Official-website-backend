package com.dl.officialsite.wish.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

/**
 * @ClassName TokenDeserializer
 * @Author jackchen
 * @Date 2025/1/24 15:26
 * @Description TokenDeserializer
 **/
public class TokenDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        // 将对象转换为字符串
        return p.readValueAsTree().toString();
    }
}
