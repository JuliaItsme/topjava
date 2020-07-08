package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealTestData.NOT_FOUND;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(DATAJPA)
public class DataJpaMealRepositoryTest extends AbstractMealServiceTest {

    @Test
    public void getMealWithUser() throws Exception {
        Meal meal = service.getMealWithUser(ADMIN_MEAL_ID, ADMIN_ID);
        MEAL_MATCHER.assertMatch(meal, ADMIN_MEAL1);
        USER_MATCHER.assertMatch(meal.getUser(), ADMIN);
    }

    @Test
    public void getMealWithUserNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.getMealWithUser(NOT_FOUND, USER_ID));
    }
}