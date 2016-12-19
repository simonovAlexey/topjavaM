package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.DateTimeUtil.isBetweenDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.isBetweenTime;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController {
    private static final Logger LOG = LoggerFactory.getLogger(MealRestController.class);
    @Autowired
    private MealService service;

    public List<MealWithExceed> getFiltred(int userId,int caloriesPerDay, LocalDate fD, LocalDate tD, LocalTime fT, LocalTime tT) {
        List<MealWithExceed> mealList = getWithExceeded(service.getAll(userId),caloriesPerDay);
        List<MealWithExceed> sorted = mealList.stream().
                filter(meal -> (isBetweenTime(meal.getTime(), fT, tT) && isBetweenDate(meal.getDate(), fD, tD))).
                collect(Collectors.toList());
        return sorted;
    }

    public List<MealWithExceed> getWithExceeded(Collection<Meal> meals, int caloriesPerDay) {
        return MealsUtil.getWithExceeded(meals, caloriesPerDay);
    }

    public Meal get(int id) {
        LOG.info("get meal: " + id);
        return service.get(id, AuthorizedUser.id());
    }

    public List<MealWithExceed> getAll(int userId) {
        LOG.info("get all userId: " + userId);
        return getWithExceeded(service.getAll(userId),AuthorizedUser.getCaloriesPerDay());
    }

    public void delete(int id) {
        LOG.info("delete meal: " + id);
        service.delete(id, AuthorizedUser.id());
    }

    public void update(Meal meal) {
        LOG.info("update meal: " + meal);
        service.update(meal, AuthorizedUser.id());
    }

    public void save(Meal meal) {
        LOG.info("save meal: " + meal);
        service.save(meal, AuthorizedUser.id());
    }


}
