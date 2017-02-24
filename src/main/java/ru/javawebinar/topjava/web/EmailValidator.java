package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

/**
 * Created by Алексей on 24.02.2017.
 */
@Component
public class EmailValidator implements Validator {

    @Autowired
    private UserService service;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserTo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserTo userTo = (UserTo) target;
        User byEmail = null;
        try {
            byEmail = service.getByEmail(userTo.getEmail());
        } catch (NotFoundException e) {
            //NOP
        }
        if (userTo.isNew()) {
            if (byEmail != null) errors.rejectValue("email", "users.present");
        } else if (byEmail != null && !userTo.getId().equals(byEmail.getId())) {
            errors.rejectValue("email", "users.present");
        }

    }
}
