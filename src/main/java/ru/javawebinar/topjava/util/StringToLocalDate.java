package ru.javawebinar.topjava.util;


import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

public class StringToLocalDate implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        return StringUtils.isEmpty(source) ? null : DateTimeUtil.parseLocalDate(source);
    }
}
