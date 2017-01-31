package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.time.LocalDate.of;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


/**
 * Created by Алексей on 31.01.2017.
 */
public class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Test
    public void testGetAll() throws Exception {
        ResultActions actions = mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        String json = TestUtil.getContent(actions);
        List<MealWithExceed> mealWithExceeds = JsonUtil.readValues(json, MealWithExceed.class);
        List<MealWithExceed> expected = MealsUtil.getWithExceeded(MEALS, AuthorizedUser.getCaloriesPerDay());
        EXCMATCHER.assertCollectionEquals(mealWithExceeds, expected);
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk());
        List<Meal> nMeal = new ArrayList<>(MEALS);
        nMeal.remove(MEAL1);
        MATCHER.assertCollectionEquals(nMeal, mealService.getAll(AuthorizedUser.id()));
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("Updated");

        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated, mealService.get(MEAL1_ID, AuthorizedUser.id()));

    }

    @Test
    public void testCreateWithLocation() throws Exception {
        Meal expected = new Meal(LocalDateTime.now(), "созданный", 3333);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))).andExpect(status().isCreated());
        Meal returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());
        List<Meal> nMeal = new ArrayList<>(MEALS);
        nMeal.add(expected);

        MATCHER.assertEquals(expected, returned);
//        Collection<Meal> actual = mealService.getAll(AuthorizedUser.id());
//        MATCHER.assertCollectionEquals(nMeal, actual);
    }

    @Test
    public void testGetBetween() throws Exception {

        String format = String.format("filter/sd%s/ed%s/st%s/et%s",
                of(2015, Month.MAY, 30).toString(),
                of(2015, Month.MAY, 30).toString(),
                LocalTime.of(0, 0, 0).toString(),
                LocalTime.of(23, 59, 59).toString());
        ResultActions actions = mockMvc.perform(get(REST_URL + format))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        String json = TestUtil.getContent(actions);
        List<MealWithExceed> mealWithExceeds = JsonUtil.readValues(json, MealWithExceed.class);
        Collection<Meal> betweenDates = mealService.getBetweenDates(of(2015, Month.MAY, 30), of(2015, Month.MAY, 30), USER_ID);

        List<MealWithExceed> expected = MealsUtil.getWithExceeded(betweenDates, AuthorizedUser.getCaloriesPerDay());
        EXCMATCHER.assertCollectionEquals(mealWithExceeds, expected);
    }

}