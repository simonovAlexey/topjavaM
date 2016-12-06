package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );

        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList,
                                                                    LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

       List<UserMealWithExceed> list = new ArrayList<>();
         Map<LocalDate,Integer> sortedByDate = new HashMap<>();
         for (UserMeal meal:mealList) {                              //решение через циклы
            LocalDate currD = meal.getDateTime().toLocalDate();
            sortedByDate.merge(currD,meal.getCalories(),(calOld,calN)-> calOld+calN);
        }


        for (UserMeal meal:mealList) {
            LocalDateTime currDT = meal.getDateTime();
            LocalDate currD = currDT.toLocalDate();
                if (TimeUtil.isBetween(currDT.toLocalTime(),startTime,endTime)) {
                    Boolean currExceed = caloriesPerDay < sortedByDate.get(currD);
                    list.add(new UserMealWithExceed(currDT, meal.getDescription(), meal.getCalories(), currExceed));
            }
        }
//        return list;

        /*Map<LocalDate, Integer> caloriesByDays = mealList.stream()     // вариант получения Map<LocalDate,Integer> из mealList
                                .collect(Collectors.groupingBy(userMeal -> userMeal.getDateTime().toLocalDate(),
                                        Collectors.summingInt(UserMeal::getCalories))
                                );*/

        Map<LocalDate,Integer> sortedByDate2
        = mealList.stream()
                .collect(Collectors.toMap(( (p) -> p.getDateTime().toLocalDate() ),(UserMeal::getCalories),(x, y) -> x+y));

        return  mealList.stream()
                        .filter(p->TimeUtil.isBetween(p.getDateTime().toLocalTime(),startTime,endTime))
                        .map(s->new UserMealWithExceed(s.getDateTime(),s.getDescription(),s.getCalories(),
                                caloriesPerDay < sortedByDate2.get(s.getDateTime().toLocalDate())))
                        .collect(Collectors.toList());
    }
}
