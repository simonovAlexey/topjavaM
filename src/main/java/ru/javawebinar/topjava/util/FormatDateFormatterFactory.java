package ru.javawebinar.topjava.util;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Алексей on 01.02.2017.
 */
public class FormatDateFormatterFactory  implements AnnotationFormatterFactory<DateFormatter> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> fieldTypes = new HashSet<Class<?>>(1, 1);

//        fieldTypes.add(LocalDateTime.class);
        fieldTypes.add(String.class);
        return fieldTypes;
    }

    @Override
    public Printer<?> getPrinter(DateFormatter annotation, Class<?> fieldType) {
        return new MyDateTimeFormatter();
    }

    @Override
    public Parser<?> getParser(DateFormatter annotation, Class<?> fieldType) {
        return new MyDateTimeFormatter();
    }


}
