package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealTestData.NOT_FOUND;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(DATAJPA)
public class DataJpaUserRepositoryTest extends AbstractUserServiceTest {

    @Test
    public void getUserWithMeal() throws Exception {
        User user = service.getUserWithMeal(USER_ID);
        USER_MATCHER.assertMatch(user, USER);
        MEAL_MATCHER.assertMatch(user.getMeals(), MEALS_2);
    }

    @Test
    public void getUserWithMealNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.getUserWithMeal(NOT_FOUND));
    }
}