package com.example.Project1.Util;

import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.logging.Logger;

public class Mapper {
    public static <S, T> void map(S source, T target) {
        if (source == null || target == null) {
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
                    throw new RuntimeException("Field mapping failed: " + sourceField.getName(), e);
            }
        }
    }
}
