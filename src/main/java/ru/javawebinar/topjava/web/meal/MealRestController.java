package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.time.LocalDate;
import java.time.LocalTime;
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
    @Autowired
    private MealService service;

    public List<Meal> getFiltred(int userId, LocalDate fromDate, LocalDate toDate, LocalTime fromTime, LocalTime toTime) {
        List<Meal> mealList = getAll(userId);
        List<Meal> sorted = mealList.stream().
                filter(meal -> (isBetweenTime(meal.getTime(), fromTime, toTime) && isBetweenDate(meal.getDate(), fromDate, toDate))).
                collect(Collectors.toList());
        return sorted;
    }

    public Meal get(int id) {
        return service.get(id, AuthorizedUser.id());
    }

    public List<Meal> getAll(int userId) {
        return service.getAll(userId);
    }

    public void delete(int id) {
        service.delete(id, AuthorizedUser.id());
    }

    public void update(Meal meal) {
        service.update(meal, AuthorizedUser.id());
    }

    public void save(Meal meal) {
        service.save(meal, AuthorizedUser.id());
    }


}
