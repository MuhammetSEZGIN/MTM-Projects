package com.example.productservice.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
@Slf4j
public class Mapper {
    public static <S, T> void map(S source, T target) {
        if (source == null || target == null) {
            log.error("Source and target objects cannot be null");
            throw new IllegalArgumentException("Source and target objects cannot be null");
        }

        Field[] sourceFields = source.getClass().getDeclaredFields();

        for (Field sourceField : sourceFields) {
            sourceField.setAccessible(true);

            try {
                Field targetField = target.getClass().getDeclaredField(sourceField.getName());
                targetField.setAccessible(true);

                if (targetField.getType().isAssignableFrom(sourceField.getType())) {
                    targetField.set(target, sourceField.get(source));
                }
            }
            catch (Exception e ) {
                log.error("Field mapping failed: " + sourceField.getName(), e);
                throw new RuntimeException("Field mapping failed: " + sourceField.getName(), e);

            }
        }
    }
}
