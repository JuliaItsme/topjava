package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Comparator.*;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> InMemoryMealRepository.this.save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {}", meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        if (!compareIdAndUserId(meal.getId(), userId))
            return null;
        else
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {}", id);
        if (!compareIdAndUserId(id, userId)) {
            return false;
        } else
            return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {}", id);
        if (!compareIdAndUserId(id, userId))
            return null;
        else
            return repository.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll");
        List<Meal> meals = new ArrayList<>(repository.values());
        if (meals.isEmpty()) {
            return Optional.of(meals).orElse(new ArrayList<>());
        } else {
            meals.sort(comparing(Meal::getDateTime, reverseOrder()));
            return meals;
        }
    }

    private boolean compareIdAndUserId(int id, int userId) {
        return repository.get(id) != null && repository.get(id).getUserId().equals(userId);
    }
}

