package ru.javawebinar.topjava.storage;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class MapStorage implements Storage {
    private static final Logger log = getLogger(MapStorage.class);

    private final Map<Integer, Meal> mapMeals = new ConcurrentHashMap<>();

    private final AtomicInteger atomicId = new AtomicInteger(0);

    public MapStorage() {
        MealsUtil.getMeals().forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        log.info("Save " + meal);
        return mapMeals.putIfAbsent(setId(meal), meal);
    }

    @Override
    public Meal update(Meal meal) {
        log.info("Update " + meal);
        return mapMeals.replace(meal.getId(), meal);
    }

    @Override
    public void delete(Integer id) {
        log.info("Delete " + id);
        mapMeals.remove(id);
    }

    @Override
    public Meal get(Integer id) {
        log.info("Get " + id);
        return mapMeals.get(id);
    }

    @Override
    public Collection<Meal> getCollection() {
        log.info("GetCollection");
        return mapMeals.values();
    }

    private Integer setId(Meal meal) {
        meal.setId(atomicId.incrementAndGet());
        return meal.getId();
    }
}
