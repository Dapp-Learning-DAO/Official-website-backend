package com.dl.officialsite.common.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;

@Converter
public class IntegerListConverter implements AttributeConverter<List<Integer>, String> {
    public static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(List<Integer> intList) {
        List<String> result = new ArrayList<>();
        for(Integer i : intList) {
           result.add(i.toString());
        }
        return result.size() !=0 ? String.join(SPLIT_CHAR, result) : "";
    }

    @Override
    public List<Integer> convertToEntityAttribute(String string) {

        String [] list= string.split(SPLIT_CHAR);
        List<Integer> intList = new ArrayList<>();

        for(String s : list) {
            intList.add(Integer.valueOf(s));
        }
        return intList;
    }
}
