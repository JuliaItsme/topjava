package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.ListMeal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private ListMeal listMeal;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        listMeal = new ListMeal();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");
        final boolean isCreate = (uuid == null || uuid.length() == 0);
        Meal meal;
        if (isCreate) {
            meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")), request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
        } else {
            meal = listMeal.get(uuid);
            meal.setDateTime(dateTime);
            meal.setDescription(description);
            meal.setCalories(Integer.parseInt(calories));
        }
        if (isCreate) {
            listMeal.save(meal);
        } else {
            listMeal.update(meal);
        }
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("meals", MealsUtil.creatMealTo(listMeal.getList(), 2000));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }
        Meal meal;
        switch (action) {
            case "delete":
                listMeal.delete(listMeal.get(uuid));
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                if (action.equals("create"))
                    meal = Meal.EMPTY;
                else
                    meal = listMeal.get(uuid);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/update.jsp").forward(request, response);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
    }
}
