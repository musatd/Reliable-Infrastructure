package org.reliable.infrastructure.admin_web_content.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	
	@RequestMapping("/")
    public ModelAndView welcome() {
        return new ModelAndView("welcome");
    }

}
