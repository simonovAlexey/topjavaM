package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class MealServiceImpl implements MealService {
    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(Meal meal, int userId) {
        meal.setUserId(userId);
        return repository.save(meal);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        Meal meal = checkNotFoundWithId(repository.get(id), id);
        if (meal.getUserId() == userId) repository.delete(id);
        else throw new NotFoundException("еда не пренадлежит пользователю id=" + userId);

    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        Meal meal = checkNotFoundWithId(repository.get(id), id);
        if (meal.getUserId() == userId) return meal;
        else throw new NotFoundException("еда не пренадлежит пользователю id= " + userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return (List<Meal>) repository.getAll(userId);
    }

    @Override
    public void update(Meal meal, int userId) {
        if (meal.getUserId() == userId) repository.save(meal);
        else throw new NotFoundException("еда не пренадлежит пользователю id=" + userId);
    }
}
