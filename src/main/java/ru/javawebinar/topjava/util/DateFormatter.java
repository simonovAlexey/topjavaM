package ru.javawebinar.topjava.util;

import java.lang.annotation.*;

/**
 * Created by Алексей on 01.02.2017.
 */
@Target(value={ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface  DateFormatter {

}
