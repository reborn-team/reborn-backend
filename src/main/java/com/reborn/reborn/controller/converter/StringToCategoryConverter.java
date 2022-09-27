package com.reborn.reborn.controller.converter;

import com.reborn.reborn.entity.WorkoutCategory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCategoryConverter implements Converter<String, WorkoutCategory> {

    private final String UNDERSCORE = "_";
    private final String HYPHEN = "-";

    @Override
    public WorkoutCategory convert(String source) {
        if (source.contains(HYPHEN)) {
            source = source.replace(HYPHEN, UNDERSCORE);
        }
        return WorkoutCategory.valueOf(source.toUpperCase());
    }
}
