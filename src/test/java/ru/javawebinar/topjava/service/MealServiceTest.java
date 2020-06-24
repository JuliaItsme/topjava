package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;


import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealTestData.USER_ID;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.MealTestData.getUpdated;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private MealRepository repository;

    @Test
    public void get() throws Exception {
        Meal meal = service.get(MEAL_1.getId(), USER_ID);
        assertMatch(meal, MEAL_1);
        Meal mealAdmin = service.get(MEAL_7.getId(), ADMIN_ID);
        assertMatch(mealAdmin, MEAL_7);
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL_2.getId(), USER_ID);
        assertNull(repository.get(MEAL_2.getId(), USER_ID));
        service.delete(MEAL_8.getId(), ADMIN_ID);
        assertNull(repository.get(MEAL_8.getId(), ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() throws Exception {
        List<Meal> mealGetBetweenInclusive = sortMeal(service.getBetweenInclusive(LocalDate.of(2020, Month.MAY, 31),
                LocalDate.of(2020, Month.MAY, 31), USER_ID));
        assertMatch(mealGetBetweenInclusive, MEAL_BETWEEN_INCLUSIVE);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> all = sortMeal(service.getAll(USER_ID));
        assertMatch(all, MEAL);
        List<Meal> allAdmin = sortMeal(service.getAll(ADMIN_ID));
        assertMatch(allAdmin, MEAL_ADMIN);
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL_3.getId(), USER_ID), updated);
    }

    @Test
    public void updateMealAdmin() throws Exception {
        Meal updated = getUpdatedMealAdmin();
        service.update(updated, ADMIN_ID);
        assertMatch(service.get(MEAL_7.getId(), ADMIN_ID), updated);
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = getNew();
        Meal created = service.create(newMeal, USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void createMealAdmin() throws Exception {
        Meal newMeal = getNewMealAdmin();
        Meal created = service.create(newMeal, ADMIN_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, ADMIN_ID), newMeal);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() throws Exception {
        service.update(getUpdated(), ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(MEAL_1.getId(), ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(MEAL_2.getId(), ADMIN_ID);
    }
}