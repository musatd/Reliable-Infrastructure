package org.reliable.infrastructure.admin_web_content.controllers;

import java.util.logging.Logger;

import org.reliable.infrastructure.admin_web_content.controllers.util.AdminService;
import org.reliable.infrastructure.admin_web_content.controllers.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CreateMessageController {
	
	@Autowired
	private AdminService adminService;
	
	private Logger logger = Logger.getLogger(CreateMessageController.class.getName());


	@RequestMapping(value="/createMessage")
    public String createMessage(@ModelAttribute Message message, Model model) {
		
		logger.info("admin-service createMessage() invoked: for " + message);
		Boolean isSuccessful = adminService.createMessage(message);
		logger.info("admin-service createMessage() found: " + isSuccessful);
		
		if (isSuccessful != null && isSuccessful) {
			model.addAttribute("responseMessage", "The alert was successfully saved");
		} else {
			model.addAttribute("responseMessage", "The alert was not saved to database");
		}
        
        return "response";
    }
}
