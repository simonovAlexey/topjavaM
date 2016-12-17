package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Comparator<Meal> MEAL_COMPARATOR = new Comparator<Meal>() {
        @Override
        public int compare(Meal o1, Meal o2) {
            return -o1.getDateTime().compareTo(o2.getDateTime());
        }
    };
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> meal.setUserId(1));
        MealsUtil.MEALS.forEach(this::save);

        repository.get(2).setUserId(2);  //"2" -> "Meal{dateTime=2015-05-30T13:00, description='Обед2', calories=1000, userId=2}"
        repository.get(5).setUserId(2);

    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int id) {
        repository.remove(id);
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        ArrayList<Meal> mc = new ArrayList<>(repository.values());
        List<Meal> msorted =
                mc.stream().
                        filter(meal -> meal.getUserId() == userId).
                        collect(Collectors.toList());

        Collections.sort(msorted, MEAL_COMPARATOR);
        return (msorted != null || !msorted.isEmpty()) ? msorted : Collections.emptyList();
    }
}

