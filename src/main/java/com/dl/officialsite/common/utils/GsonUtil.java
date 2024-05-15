package com.dl.officialsite.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

@Slf4j
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

    public static <T> List<T> readListFromJsonFile(String filePath, Class<T> clazz) {
        try (FileReader reader = new FileReader(filePath)) {
            // Use TypeToken to get the type of List<T>
            Type typeOfT = TypeToken.getParameterized(List.class, clazz).getType();
            return gson.fromJson(reader, typeOfT);
        } catch (FileNotFoundException e) {
            log.error("File {} not found.", filePath);
        } catch (IOException e) {
            log.error("Read file {} error.", filePath, e);
        }
        return Collections.emptyList();
    }


}