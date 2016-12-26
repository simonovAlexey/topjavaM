package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * Created by Алексей on 26.12.2016.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
        MEAL1.setId(100_002);
        MEAL2.setId(100_003);
    }

    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(100002, USER_ID);
        M_MATCHER.assertEquals(MEAL1, meal);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFoundIncorrectUser() throws Exception {
        service.get(100_002, USER_ID + 1);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(100_002, USER_ID);
        M_MATCHER.assertCollectionEquals(Collections.singletonList(MEAL2), service.getAll(UserTestData.USER_ID));


    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDeleteIncorrectUser() throws Exception {
        service.delete(100_002, USER_ID + 1);
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        LocalDateTime beforeMEAL2 = LocalDateTime.of(2016, Month.DECEMBER, 26, 9, 0);
        Collection<Meal> betweenDateTimes = service.getBetweenDateTimes(LocalDateTime.MIN, beforeMEAL2, USER_ID);
        M_MATCHER.assertCollectionEquals(Arrays.asList(MEAL2, MEAL1), betweenDateTimes);
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        LocalDateTime beforeMEAL2 = LocalDateTime.of(2016, Month.DECEMBER, 26, 7, 0);
        Collection<Meal> betweenDateTimes = service.getBetweenDateTimes(LocalDateTime.MIN, beforeMEAL2, USER_ID);
        M_MATCHER.assertCollectionEquals(Arrays.asList(MEAL1), betweenDateTimes);

        LocalDateTime afterMEAL1 = LocalDateTime.of(2016, Month.DECEMBER, 25, 9, 0);
        Collection<Meal> betweenDateTimes2 = service.getBetweenDateTimes(afterMEAL1, LocalDateTime.MAX, USER_ID);
        M_MATCHER.assertCollectionEquals(Arrays.asList(MEAL2), betweenDateTimes2);
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<Meal> all = service.getAll(USER_ID);
        M_MATCHER.assertCollectionEquals(Arrays.asList(MEAL2, MEAL1), all);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = new Meal(MEAL1);
        updated.setId(100_002);
        updated.setDescription("UpdatedMEAL");
        updated.setCalories(330);
        service.update(updated, USER_ID);
        M_MATCHER.assertEquals(updated, service.get(100_002, USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundUpdateIncorrectUser() throws Exception {
        service.update(service.get(100002, USER_ID + 1), USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundUpdateIncorrectMeal() throws Exception {
        Meal updated = service.get(100002, USER_ID);
        updated.setDescription("UpdatedMEAL");
        updated.setCalories(330);
        service.update(updated, USER_ID + 1);
    }

    @Test
    public void testSave() throws Exception {
        Meal save = new Meal(LocalDateTime.of(2016, Month.DECEMBER, 20, 12, 0), "Обед", 2000);
        Meal save1 = service.save(save, USER_ID);
        save.setId(save1.getId());

        Collection<Meal> all = service.getAll(USER_ID);
        M_MATCHER.assertCollectionEquals(Arrays.asList(MEAL2, MEAL1, save), all);

    }

}