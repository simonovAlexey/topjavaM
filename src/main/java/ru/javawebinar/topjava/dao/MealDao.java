package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalTime;
import java.util.List;

/**
 * Created by Алексей on 10.12.2016.
 */
public interface MealDao {

    void create(Meal meal);
    List<MealWithExceed> getAll(LocalTime startTime, LocalTime endTime, int caloriesPerDay);
    Meal getById(int id);
    void update(Meal meal);
    void delete(int id);

}
