package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Алексей on 10.12.2016.
 */
public class MealDaoMemory implements MealDao {
    private static volatile CopyOnWriteArrayList<Meal> meals;

    private static AtomicInteger count = new AtomicInteger(0);

    static {
        Meal arr[] = {
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "ЗавтракT", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "ОбедT", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "УжинF", 500),
                new Meal(LocalDateTime.of(2016, Month.JANUARY, 16, 20, 0), "Янв ужин T", 10),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "ЗавтракF", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "ОбедT", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "УжинF", 510),
                new Meal(LocalDateTime.of(2016, Month.JULY, 16, 20, 0), "Новый ужин !", 2010)
        };
        for (int i = 0; i <arr.length ; i++) {
            arr[i].setId(count.incrementAndGet());
        }
        meals = new CopyOnWriteArrayList(arr);
    }

    @Override
    public void create(Meal meal) {
        if (meal.getId()==0) meal.setId(count.incrementAndGet());
        meals.addIfAbsent(meal);

    }

    @Override
    public List<MealWithExceed> getAll(LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return MealsUtil.getFilteredWithExceeded(meals,startTime,endTime,caloriesPerDay);
    }

    @Override
    public Meal getById(int id) {
        return meals.get(getIndexOfMeals(id));
//      return meals.stream().filter(k->k.getId()==id).findFirst().get();
    }

    @Override
    public void update(Meal meal) {
        delete(meal.getId());
        meals.addIfAbsent(meal);
    }

    @Override
    public void delete(int id) {
        try {
            meals.remove(getIndexOfMeals(id));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private int getIndexOfMeals(int id){
        for (int i = 0; i < meals.size(); i++) {
            if (meals.get(i).getId()==id) return i;
        }
        return 0;
    }
}
