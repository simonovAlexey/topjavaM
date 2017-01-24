package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
@Controller
public class RootController extends MealRestController {
    private static final Logger LOG = getLogger(RootController.class);
    @Autowired
    private UserService service;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "index";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users(Model model) {
        model.addAttribute("users", service.getAll());
        return "users";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String setUser(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userId"));
        AuthorizedUser.setId(userId);
        return "redirect:meals";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String meals(Model model) {
        model.addAttribute("meals", getAll());
        return "meals";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteUser(HttpServletRequest request) {
        int id = Integer.valueOf(request.getParameter("id"));
        delete(id);
        return "redirect:meals";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String updateUser(HttpServletRequest request, Model model) {
        int id = Integer.valueOf(request.getParameter("id"));
        Meal meal = get(id);
        model.addAttribute("meal", meal);
        return "meal";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createUser(HttpServletRequest request, Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 999);
        model.addAttribute("meal", meal);
        return "meal";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.POST)
    public String setMeal(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            final Meal meal = new Meal(
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.valueOf(request.getParameter("calories")));

            if (request.getParameter("id").isEmpty()) {
                LOG.info("Create {}", meal);
                create(meal);
            } else {
                LOG.info("Update {}", meal);
                update(meal, getId(request));
            }
            return "redirect:meals";

        } else if ("filter".equals(action))  {
            LocalDate startDate = DateTimeUtil.parseLocalDate(request.getParameter("startDate"));
            LocalDate endDate = DateTimeUtil.parseLocalDate(request.getParameter("endDate"));
            LocalTime startTime = DateTimeUtil.parseLocalTime(request.getParameter("startTime"));
            LocalTime endTime = DateTimeUtil.parseLocalTime(request.getParameter("endTime"));
            request.setAttribute("meals", getBetween(startDate, startTime, endDate, endTime));

        }
        return "meals";

    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }


}
