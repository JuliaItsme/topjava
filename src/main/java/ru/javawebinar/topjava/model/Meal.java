package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

public class Meal {
    public static final Meal EMPTY = new Meal();

    private String uuid;

    private LocalDateTime dateTime;

    private String description;

    private int calories;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(UUID.randomUUID().toString(), dateTime, description, calories);
    }

    public Meal(String uuid, LocalDateTime dateTime, String description, int calories) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(dateTime, "date must not be null");
        Objects.requireNonNull(description, "description must not be null");
        this.uuid = uuid;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
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

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return Objects.equals(uuid, meal.uuid) &&
                Objects.equals(dateTime, meal.dateTime) &&
                Objects.equals(description, meal.description) &&
                Objects.equals(calories, meal.calories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dateTime, description, calories);
    }

    @Override
    public String toString() {
        return dateTime + " " + description + " " + calories;
    }
}
