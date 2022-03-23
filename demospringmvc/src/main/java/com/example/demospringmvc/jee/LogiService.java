package com.example.demospringmvc.jee;


public class LogiService {
    public boolean validateUser(String user, String password) {
        return user.equalsIgnoreCase("in28Minutes") && password.equals("dummy");
    }

}
