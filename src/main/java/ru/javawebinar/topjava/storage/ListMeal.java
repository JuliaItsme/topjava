package ru.javawebinar.topjava.storage;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class ListMeal implements Storage {
    private static final Logger log = getLogger(ListMeal.class);
    private final List<Meal> listMeals = MealsUtil.getMeals();

    @Override
    public void save(Meal meal) {
        log.info("Save " + meal);
        listMeals.add(meal);

    }

    @Override
    public void update(Meal meal) {
        log.info("Update " + meal);
        Integer searchKey = getSearchKey(meal.getUuid());
        listMeals.set(searchKey, meal);
    }

    @Override
    public void delete(Meal meal) {
        log.info("Delete " + meal);
        listMeals.remove(meal);

    }

    @Override
    public Meal get(String uuid) {
        log.info("Get " + uuid);
        return listMeals.get(getSearchKey(uuid));
    }

    @Override
    public List<Meal> getList() {
        log.info("GetList");
        return new ArrayList<>(listMeals);
    }

    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < listMeals.size(); i++) {
            if (uuid.equals(listMeals.get(i).getUuid())) {
                return i;
            }
        }
        return null;
    }
}
