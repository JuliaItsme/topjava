package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage {

    void save(Meal meal);

    void update(Meal meal);

    void delete(Meal meal);

    Meal get(String uuid);

    List<Meal> getList();
}
