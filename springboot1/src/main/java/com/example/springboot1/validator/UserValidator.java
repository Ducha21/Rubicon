package com.example.springboot1.validator;

import com.example.springboot1.model.UserModel;
import com.example.springboot1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserModel userModel = (UserModel) target;
        //ktra dữu liệu nhập vào
        ValidationUtils.rejectIfEmptyOrWhitespace((errors),"username","NotEmpty");
        //nếu đồ dài của username mà nhỏ hơn 6 hoặc lớn hơn 32 thì hiển thị lỗi size.
        if(userModel.getUsername().length() < 6 || userModel.getUsername().length() > 32){
            errors.rejectValue("username", "Size.userForm.username");
        }
        //Nếu đồ dài của password mà nhỏ hơn 8 hoặc lớn hơn 32 thì hiển thị lỗi size
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (userModel.getPassword().length() < 8 || userModel.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!userModel.getPasswordConfirm().equals(userModel.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
}
