package com.dl.officialsite.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {
    private static final Gson gson = new Gson();

    private GsonUtil() {
        // 私有构造方法，防止实例化
    }

    /**
     * 将对象转换为 JSON 字符串
     */
    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    /**
     * 将对象转换为格式化的 JSON 字符串（带缩进）
     */
    public static String toPrettyJson(Object object) {
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        return prettyGson.toJson(object);
    }

    /**
     * 将 JSON 字符串转换为指定类型的对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }


}