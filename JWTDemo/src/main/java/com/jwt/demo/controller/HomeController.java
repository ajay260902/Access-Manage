package com.jwt.demo.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.demo.entity.User;
import com.jwt.demo.service.UserService;

@RestController
@RequestMapping("/api")
public class HomeController {

//    Logger logger = (Logger) LoggerFactory.getLogger(HomeController.class);
     
   @Autowired
    private UserService userService;
    
    @RequestMapping("/test")
    public String test() {
//        this.logger.warn("This is working message");
        return "Testing message";
    }
    
    @GetMapping("/users")
    public List<User> getUSers() {
		return this.userService.getUsers();
	}
    
    @GetMapping("/{email}")
    public User getUser(@PathVariable String email) {
    	System.out.println("email ---- "+email);
		return this.userService.getOneUser(email);
	}
    
    
    
    
   

}