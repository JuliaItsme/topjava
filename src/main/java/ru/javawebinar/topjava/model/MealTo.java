package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class MealTo {

    private final String uuid;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean excess;

    public MealTo(String uuid, LocalDateTime dateTime, String description, int calories, boolean excess) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(dateTime, "date must not be null");
        Objects.requireNonNull(description, "description must not be null");
        this.uuid = uuid;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    public MealTo(Meal meal, boolean excess) {
        this(meal.getUuid(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

    public String getUuid() {
        return uuid;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExcess() {
        return excess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MealTo mealTo = (MealTo) o;
        return Objects.equals(uuid, mealTo.uuid) &&
                Objects.equals(dateTime, mealTo.dateTime) &&
                Objects.equals(description, mealTo.description) &&
                Objects.equals(calories, mealTo.calories) &&
                Objects.equals(excess, mealTo.excess);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dateTime, description, calories, excess);
    }

    @Override
    public String toString() {
        return dateTime + " " + description + " " + calories + " " + excess;
    }
}
