package ru.javawebinar.topjava.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealWithExceed;

import static org.slf4j.LoggerFactory.getLogger;


/**
 * Created by Алексей on 09.12.2016.
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);

    private static List<MealWithExceed> meals;

    static {
        meals = Arrays.asList(
                new MealWithExceed(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "ЗавтракT", 500,true),
                new MealWithExceed(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "ОбедT", 1000,true),
                new MealWithExceed(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "УжинF", 500,false),
                new MealWithExceed(LocalDateTime.of(2016, Month.JANUARY, 16, 20, 0), "Янв ужин T", 10,true),
                new MealWithExceed(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "ЗавтракF", 1000,false),
                new MealWithExceed(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "ОбедT", 500,true),
                new MealWithExceed(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "УжинF", 510,false),
                new MealWithExceed(LocalDateTime.of(2016, Month.JULY, 16, 20, 0), "Новый ужин F", 10,false)
        );
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug(LocalDateTime.now()+" doGet from meals ");

        request.setAttribute("list",meals);

        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
