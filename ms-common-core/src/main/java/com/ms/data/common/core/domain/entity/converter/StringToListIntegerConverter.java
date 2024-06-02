package com.ms.data.common.core.domain.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class StringToListIntegerConverter implements AttributeConverter<List<Integer>, String> {

    @Override
    public String convertToDatabaseColumn(final List<Integer> list) {
        if (list == null) {
            return null;
        }
        return list.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    @Override
    public List<Integer> convertToEntityAttribute(final String string) {
        if (string == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(string.split(",")).map(Integer::valueOf).collect(Collectors.toList());
    }
}