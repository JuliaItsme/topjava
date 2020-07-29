package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = MealRestController.REST_URL_MEAL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {
    static final String REST_URL_MEAL = "/rest/meals";

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRest(@PathVariable int id) {
        super.delete(id);
    }

    //  https://www.codeflow.site/ru/article/spring-response-entity
    @GetMapping("/{id}")
    public Meal getRest(@PathVariable int id) {
        return ResponseEntity.ok(super.get(id)).getBody();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRest(@RequestBody Meal meal) {
        super.update(meal, meal.getId());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createRest(@RequestBody Meal meal) {
        Meal mealNew = super.create(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL_MEAL + "/{id}")
                .buildAndExpand(mealNew.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(mealNew);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getAllRest() {
        return ResponseEntity.ok(super.getAll()).getBody();
    }

    @GetMapping("/filter")
    public List<MealTo> getBetweenRest(@RequestParam LocalDate startDate, @RequestParam LocalTime startTime, @RequestParam LocalDate endDate, @RequestParam LocalTime endTime) {
        return ResponseEntity.ok(super.getBetween(startDate, startTime, endDate, endTime)).getBody();
    }
}