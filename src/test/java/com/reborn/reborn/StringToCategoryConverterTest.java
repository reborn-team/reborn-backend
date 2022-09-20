package com.reborn.reborn;

import com.reborn.reborn.entity.WorkoutCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

import static org.assertj.core.api.Assertions.*;

class StringToCategoryConverterTest {


    @Test
<<<<<<< HEAD
    @DisplayName("카테고리에 하이픈이 들어오면 언더스코어로 변환한다.")
=======
    @DisplayName("카테고리에 하이픈이 들어오면 언더스코어로 변환 후 Enum으로 반환한다.")
>>>>>>> 113f9386f9f64211c2345b78b02f41d117e6e735
    void ConverterHyphenTest() {
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new StringToCategoryConverter());

        String input = "lower-body";
        WorkoutCategory convertCategory = conversionService.convert(input, WorkoutCategory.class);
        assertThat(convertCategory).isEqualTo(WorkoutCategory.LOWER_BODY);
    }

    @Test
    @DisplayName("소문자의 카테고리명을 Enum으로 바꾼다.")
    void lowerCaseToCategoryTest() {
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new StringToCategoryConverter());

        String input = "back";
        WorkoutCategory convertCategory = conversionService.convert(input, WorkoutCategory.class);
        assertThat(convertCategory).isEqualTo(WorkoutCategory.BACK);
    }

}