package it.tndigit.iot.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

   @RequestMapping(value="/", method = RequestMethod.GET)
    public String home (HttpServletRequest request, HttpServletResponse response) {
        return "redirect:/swagger-ui.html";
    }






}

