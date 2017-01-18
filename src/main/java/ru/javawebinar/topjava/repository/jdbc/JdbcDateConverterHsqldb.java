package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by Алексей on 18.01.2017.
 */
@Component
@Profile("hsqldb")
public class JdbcDateConverterHsqldb  implements JdbcDateConverter<Timestamp>{

    @Override
    public Timestamp convert(LocalDateTime ldt) {
        return Timestamp.valueOf(ldt);
    }
}
