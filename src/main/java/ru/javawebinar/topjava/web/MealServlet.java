package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;


/**
 * Created by Алексей on 09.12.2016.
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);
    private MealDao dao = new MealDaoMemory();

    private static String INSERT_OR_EDIT = "/meal.jsp";
    private static String LIST_MEAL = "/meals.jsp";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug(LocalDateTime.now() + " doPost from meals ");

        request.setCharacterEncoding("UTF-8");
        String idString = request.getParameter("mealId");

        int calories = Integer.parseInt(request.getParameter("calories"));
        String desc = request.getParameter("desc");
        String date = request.getParameter("date");
        LocalDateTime dateTime = LocalDateTime.parse(date);
        Meal meal = new Meal(dateTime, desc, calories);

        if (idString == null || idString.isEmpty()) {
            dao.create(meal);
        } else {
            meal.setId(Integer.parseInt(idString));
            dao.update(meal);
        }
        RequestDispatcher view = request.getRequestDispatcher(LIST_MEAL);
        List<MealWithExceed> mealWithExceedList = dao.getAll(LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
        request.setAttribute("list", mealWithExceedList);
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug(LocalDateTime.now() + " doGet from meals ");

        String forward = "";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            dao.delete(mealId);
            forward = LIST_MEAL;
            List<MealWithExceed> mw = dao.getAll(LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
            request.setAttribute("list", mw);
        } else if (action.equalsIgnoreCase("edit")) {
            forward = INSERT_OR_EDIT;
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = dao.getById(mealId);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("list")) {
            forward = LIST_MEAL;
            request.setAttribute("list", dao.getAll(LocalTime.of(0, 0), LocalTime.of(23, 59), 2000));
        } else {
            forward = INSERT_OR_EDIT;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);


//        request.setAttribute("list",dao.getAll(LocalTime.of(7, 0), LocalTime.of(21, 0), 2000));
//        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
