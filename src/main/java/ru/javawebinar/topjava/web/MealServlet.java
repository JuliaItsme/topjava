package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MapStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    public static final Meal EMPTY = new Meal(null, null, " ", 0);
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new MapStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        final boolean isCreate = (id == null || id.length() == 0);
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        Integer calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal;
        if (isCreate) {
            meal = new Meal(null, dateTime, description, calories);
            storage.save(meal);
        } else {
            meal = new Meal(Integer.valueOf(id), dateTime, description, calories);
            storage.update(meal);
        }
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String id = request.getParameter("id");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("meals", MealsUtil.creatMealTo(new ArrayList<>(storage.getCollection()), 2000));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }
        Meal meal;
        switch (action) {
            case "delete":
                storage.delete(Integer.parseInt(id));
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                if (action.equals("create"))
                    meal = EMPTY;
                else
                    meal = storage.get(Integer.parseInt(id));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/update.jsp").forward(request, response);
                break;
            default:
                request.setAttribute("meals", MealsUtil.creatMealTo(new ArrayList<>(storage.getCollection()), 2000));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }
}
