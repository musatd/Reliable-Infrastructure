package org.reliable.infrastructure.AMQPSender;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SenderController {
	
	@Autowired
	private SenderService senderService;
	
	private Logger logger = Logger.getLogger(SenderController.class.getName());
	
	@RequestMapping(value = "/amqpSender/sendAlerts", produces = MediaType.APPLICATION_JSON_VALUE, consumes="application/json")
    public Boolean receiveAlert(@RequestBody Notifications notifications) throws Exception {
		
		logger.info("sender-service sendDataToRabbitMQServer() invoked: for " + notifications);
		senderService.sendDataToRabbitMQServer(notifications);
		logger.info("sender-service sendDataToRabbitMQServer() finished successfully");		
		
		
        return true;
    }

}
