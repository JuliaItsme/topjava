package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface Storage {

    Meal save(Meal meal);

    Meal update(Meal meal);

    void delete(Integer id);

    Meal get(Integer id);

    Collection<Meal> getCollection();
}
