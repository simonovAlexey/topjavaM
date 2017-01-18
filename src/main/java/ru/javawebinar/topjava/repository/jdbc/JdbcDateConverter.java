package ru.javawebinar.topjava.repository.jdbc;

import java.time.LocalDateTime;

/**
 * Created by Алексей on 18.01.2017.
 */
public interface JdbcDateConverter<T> {
    T convert(LocalDateTime ldt);

}
