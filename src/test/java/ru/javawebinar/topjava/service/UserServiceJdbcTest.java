package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

/**
 * Created by Алексей on 18.01.2017.
 */
@ActiveProfiles({Profiles.JDBC})
public class UserServiceJdbcTest extends AbstractUserServiceTest{
}
