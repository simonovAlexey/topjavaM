package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * gkislin
 * 02.10.2016
 */
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    List<Meal> findByUserIdOrderByDateTimeDesc(int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=?1 and m.user.id=?2")
    int delete(int id,int userId);

    Meal findByIdAndUserId(int id, int userId);

    @Override
    Meal getOne(Integer integer);

    List<Meal> findByUserIdAndDateTimeIsBetweenOrderByDateTimeDesc(int userId, LocalDateTime startDate, LocalDateTime endDate);
}
