package com.rves.validator;


import com.rves.Dto.UserDto;
import com.rves.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

@Component
public class UserValidator implements org.springframework.validation.Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDto user = (UserDto) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.message.empty");
        if (user.getUsername().length() < 5 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "error.message.size.username");
        }

        if (userService.userExists(user.getUsername())) {
            errors.rejectValue("username", "error.message.invalid_username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.message.empty");
        if (user.getPassword().length() < 5 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "error.message.password_size");
        }

        if (!user.getMatchingPassword().equals(user.getPassword())) {
            errors.rejectValue("matchingPassword", "error.message.password");
        }

    }

}

