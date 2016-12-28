package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final Meal MEAL1 = new Meal(LocalDateTime.of(2016, Month.DECEMBER, 25, 8, 0), "Завтрак тестовый1", 500);
    public static final Meal MEAL2 = new Meal(LocalDateTime.of(2016, Month.DECEMBER, 26, 8, 0), "Завтрак тестовый2", 500);

    public static final ModelMatcher<Meal> M_MATCHER = new ModelMatcher<>( );
}
