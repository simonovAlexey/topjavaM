package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.AuthorizedUser;

import javax.servlet.http.HttpServletRequest;

/**
 * User: gkislin
 * Date: 23.09.2014
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

//    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
   /* @ExceptionHandler(DataIntegrityViolationException.class)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ModelAndView conflictU(HttpServletRequest req, DataIntegrityViolationException e) {
        LOG.error("Exception at request " + req.getRequestURL(), e);
        ModelAndView mav = new ModelAndView("profile");
        mav.addObject("exception", e);
        mav.addAllObjects(req.getParameterMap());
        mav.getModel().remove("_csrf");

        return mav;
    }
*/
    @ExceptionHandler(Exception.class)
    @Order(Ordered.LOWEST_PRECEDENCE)
    ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        LOG.error("Exception at request " + req.getRequestURL(), e);
        ModelAndView mav = new ModelAndView("exception/exception");
        mav.addObject("exception", e);

        // Interceptor is not invoked, put userTo
        AuthorizedUser authorizedUser = AuthorizedUser.safeGet();
        if (authorizedUser != null) {
            mav.addObject("userTo", authorizedUser.getUserTo());
        }
        return mav;
    }
}
