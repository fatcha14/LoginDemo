package com.example.demo.service;

import com.example.demo.dao.mapper.UserMapper;
import com.example.demo.entity.ResponseEntity;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public ResponseEntity register(User user) {
        ResponseEntity responseEntity = defaultResponse();
        try {
            User existingUser = userMapper.findExistingUser(user.getUsername());
            if (existingUser != null) {
                responseEntity.setResponse("User has been already created!");

            } else if (!isPasswordConfirmed(user.getPassword(), user.getConfirmedPassword())) {
                responseEntity.setResponse("Confirm password does not match!");
            } else {
                userMapper.register(user);
                responseEntity.setResponse("You have successfully registered!");
                responseEntity.setSuccess(true);
                responseEntity.setDetail(user);
            }
        } catch (Exception e) {
            responseEntity.setResponse(e.getMessage());
            e.printStackTrace();
        }
        return responseEntity;
    }

    public ResponseEntity login(User user) {
        ResponseEntity responseEntity = defaultResponse();
        try {
            if (!isExistingUser(user)) {
                responseEntity.setResponse("Invalid username or password!");
                return responseEntity;
            }
            if (!isValidAttempt(user)) {
                responseEntity.setResponse("You have reached maximum attempts for login, please contact us for support!");
                return responseEntity;
            }
            User existingUser = userMapper.login(user);
            if (existingUser == null) {
                responseEntity.setResponse("Invalid username or password!");
                userMapper.incrementFailedAttempt(user);
            } else {
                responseEntity.setResponse("You have successfully logged-in!");
                responseEntity.setSuccess(true);
                responseEntity.setDetail(existingUser);
            }
        } catch (Exception e) {
            responseEntity.setResponse(e.getMessage());
        }
        return responseEntity;
    }

    public ResponseEntity updatePassword(User user) {
        ResponseEntity responseEntity = defaultResponse();
        try {
            if (!isExistingUser(user)) {
                responseEntity.setResponse("Invalid username or password!");
                return responseEntity;
            }
            if (!isValidAttempt(user)) {
                responseEntity.setResponse("You have reached maximum attempts for login, please contact us for support!");
                return responseEntity;
            }
            User existingUser = userMapper.login(user);
            if (existingUser == null) {
                responseEntity.setResponse("Invalid username or password");
                userMapper.incrementFailedAttempt(user);
            } else if (!isPasswordConfirmed(user.getNewPassword(), user.getConfirmedPassword())) {
                responseEntity.setResponse("Confirm password does not match!");
            } else {
                userMapper.updatePassword(user);
                responseEntity.setResponse("You have successfully updated the password!");
                responseEntity.setSuccess(true);
                responseEntity.setDetail(existingUser);
            }
        } catch (Exception e) {
            responseEntity.setResponse(e.getMessage());
        }
        return responseEntity;
    }

    private ResponseEntity defaultResponse() {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setSuccess(false);
        responseEntity.setDetail(null);
        return responseEntity;
    }

    private boolean isPasswordConfirmed(String firstInput, String secondInput) {
        return firstInput.equals(secondInput);
    }

    private boolean isValidAttempt(User user) {
        int failedAttempt = userMapper.getFailedAttempt(user);
        return failedAttempt <= 3;
    }

    private boolean isExistingUser(User user) {
        if (userMapper.findExistingUser(user.getUsername()) == null) {
            return false;
        }
        return true;
    }
}
