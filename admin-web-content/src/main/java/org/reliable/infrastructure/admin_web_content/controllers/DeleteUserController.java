package org.reliable.infrastructure.admin_web_content.controllers;

import java.util.logging.Logger;

import org.reliable.infrastructure.admin_web_content.controllers.util.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DeleteUserController {
	
	@Autowired
	private AdminService adminService;
	
	private Logger logger = Logger.getLogger(DeleteUserController.class.getName());


	@RequestMapping(value="/deleteUser")
    public String deleteUser(@RequestParam String token, Model model) {
        
		logger.info("admin-service deleteUser() invoked: for " + token);
		Boolean isSuccesful = adminService.deleteUser(token);
		logger.info("admin-service deleteUser() found: " + isSuccesful);
		
		if (isSuccesful) {
			model.addAttribute("responseMessage", "The user was successfully deleted");
		} else {
			model.addAttribute("responseMessage", "The user was not deleted from database");
		}
		
        return "response";
    }
}
