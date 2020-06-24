package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final Meal MEAL_1 = new Meal(START_SEQ + 2, of(2020, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL_2 = new Meal(START_SEQ + 3, of(2020, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL_3 = new Meal(START_SEQ + 4, of(2020, Month.MAY, 30, 19, 0), "Ужин", 500);
    public static final Meal MEAL_4 = new Meal(START_SEQ + 5, of(2020, Month.MAY, 31, 10, 0), "Завтрак", 500);
    public static final Meal MEAL_5 = new Meal(START_SEQ + 6, of(2020, Month.MAY, 31, 13, 0), "Обед", 1000);
    public static final Meal MEAL_6 = new Meal(START_SEQ + 7, of(2020, Month.MAY, 31, 19, 0), "Ужин", 550);
    public static final Meal MEAL_7 = new Meal(START_SEQ + 8, of(2020, Month.JUNE, 1, 10, 0), "Админ завтрак", 500);
    public static final Meal MEAL_8 = new Meal(START_SEQ + 9, of(2020, Month.JUNE, 1, 15, 0), "Админ обед", 1100);

    public static final List<Meal> MEAL = Arrays.asList(MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2, MEAL_1);
    public static final List<Meal> MEAL_ADMIN = Arrays.asList(MEAL_8, MEAL_7);
    public static final List<Meal> MEAL_BETWEEN_INCLUSIVE = Arrays.asList(MEAL_6, MEAL_5, MEAL_4);


    public static Meal getNew() {
        return new Meal(null, of(2020, Month.JUNE, 02, 10, 0), "Завтрак", 550);
    }

    public static Meal getNewMealAdmin() {
        return new Meal(null, of(2020, Month.JUNE, 01, 22, 0), "Админ ужин", 400);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL_3);
        updated.setDateTime(of(2020, Month.MAY, 30, 17, 0));
        updated.setDescription("Полдник");
        updated.setCalories(700);
        return updated;
    }

    public static Meal getUpdatedMealAdmin() {
        Meal updated = new Meal(MEAL_7);
        updated.setDateTime(of(2020, Month.JUNE, 1, 12, 0));
        updated.setDescription("Поздний завтрак");
        updated.setCalories(700);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields().isEqualTo(expected);
    }
}
