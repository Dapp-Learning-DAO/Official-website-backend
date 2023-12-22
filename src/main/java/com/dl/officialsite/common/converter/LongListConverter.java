package com.dl.officialsite.common.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;
import static java.util.Collections.emptyList;

@Converter
public class LongListConverter implements AttributeConverter<List<Long>, String> {
    public static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(List<Long> intList) {
        List<String> result = new ArrayList<>();
        for(long i : intList) {
           result.add(String.valueOf(i));
        }
        return result.size() !=0 ? String.join(SPLIT_CHAR, result) : "";
    }

    @Override
    public List<Long> convertToEntityAttribute(String string) {

        String [] list= string.split(SPLIT_CHAR);
        List<Long> intList = new ArrayList<>();

        for(String s : list) {
            intList.add( Long.parseLong(s));
        }
        return intList;
    }
}
