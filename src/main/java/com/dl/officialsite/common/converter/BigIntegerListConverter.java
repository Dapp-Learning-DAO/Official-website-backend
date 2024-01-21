package com.dl.officialsite.common.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Converter
public class BigIntegerListConverter implements AttributeConverter<List<BigInteger>, String> {
    public static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(List<BigInteger> intList) {
        if (intList == null)
            return null;
        List<String> result = new ArrayList<>();
        for(BigInteger i : intList) {
           result.add(i.toString());
        }
        return result.size() !=0 ? String.join(SPLIT_CHAR, result) : "";
    }

    @Override
    public List<BigInteger> convertToEntityAttribute(String string) {

        if (string == null)
            return null;

        String [] list= string.split(SPLIT_CHAR);
        List<BigInteger> intList = new ArrayList<>();

        for(String s : list) {
            intList.add(new BigInteger(s));
        }
        return intList;
    }
}
