package org.reliable.infrastructure.admin_web_content.controllers;

import java.util.logging.Logger;

import org.reliable.infrastructure.admin_web_content.controllers.util.AdminService;
import org.reliable.infrastructure.admin_web_content.controllers.util.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CreateUserController {
	
	@Autowired
	private AdminService adminService;
	
	private Logger logger = Logger.getLogger(CreateUserController.class.getName());


	@RequestMapping(value="/createUser")
    public String createUser(@ModelAttribute User newUser, Model model) {
        
		logger.info("admin-service saveUser() invoked: for " + newUser);
		Boolean isSuccesful = adminService.saveUser(newUser);
		logger.info("admin-service saveUser() found: " + isSuccesful);
		
		if (isSuccesful) {
			model.addAttribute("responseMessage", "The user was successfully saved");
		} else {
			model.addAttribute("responseMessage", "The user was not saved to database");
		}
		
        return "response";
    }
}
