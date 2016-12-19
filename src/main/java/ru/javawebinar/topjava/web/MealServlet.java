package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    //    private MealRepository repository;
    private ConfigurableApplicationContext appCtx;
    private MealRestController mealController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealController = appCtx.getBean(MealRestController.class);

    }

    @Override
    public void destroy() {
        appCtx.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        mealController.save(meal);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String userIdStr = request.getParameter("userId");


        if (userIdStr != null) {
            int userId = Integer.parseInt(userIdStr);
            AuthorizedUser.setId(userId);
        }

        if (action == null) {
            String fromDate = request.getParameter("fromDate");
            String toDate = request.getParameter("toDate");
            String fromTime = request.getParameter("fromTime");
            String toTime = request.getParameter("toTime");
//            Cookie[] cookies = request.getCookies();
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("fromDate") ) fromDate = cookie.getValue();
//                if (cookie.getName().equals("toDate") ) toDate = cookie.getValue();
//                if (cookie.getName().equals("fromTime") ) fromTime = cookie.getValue();
//                if (cookie.getName().equals("toTime") ) toTime = cookie.getValue();
//                cookie.setMaxAge(0);
//            }
//            String fromDate1 = (String) request.getAttribute("fromDate");

//            response.addCookie(new Cookie("fromDate",fromDate));
//            response.addCookie(new Cookie("toDate",toDate));
//            response.addCookie(new Cookie("fromTime",fromTime));
//            response.addCookie(new Cookie("toTime",toTime));


            LOG.info("getAll betwenDate:" + fromDate + " : " + toDate + " betwenTime:" + fromTime + " : " + toTime);
            LocalDate fD = (fromDate == null || fromDate == "") ? LocalDate.MIN : LocalDate.parse(fromDate);
            LocalDate tD = (toDate == null || toDate == "") ? LocalDate.MAX : LocalDate.parse(toDate);
            LocalTime fT = (fromTime == null || fromTime == "") ? LocalTime.MIN : LocalTime.parse(fromTime);
            LocalTime tT = (toTime == null || toTime == "") ? LocalTime.MAX : LocalTime.parse(toTime);

            request.setAttribute("meals",
                    mealController.getFiltred(AuthorizedUser.id(), AuthorizedUser.getCaloriesPerDay(),
                            fD, tD, fT, tT));
            request.setAttribute("fromDate", fromDate);
            request.setAttribute("fromTime", fromTime);
            request.setAttribute("toDate", toDate);
            request.setAttribute("toTime", toTime);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);

        } else if ("delete".equals(action)) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            mealController.delete(id);
            response.sendRedirect("meals");

        } else if ("create".equals(action) || "update".equals(action)) {
            final Meal meal = action.equals("create") ?
                    new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                    mealController.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("meal.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}