package org.reliable.infrastructure.AMQPReceiver;

import java.util.logging.Logger;

import org.reliable.infrastructure.AMQPReceiver.messagesTasks.Task;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

@RabbitListener(queues = Util.QUEUE_NAME)
public class Receiver {
	
	@Autowired
	private ObjectMapper mapper;
	
	private Logger logger = Logger.getLogger(Receiver.class.getName());
	
	@Autowired
	private Util util;

    @RabbitHandler
    public void receive(String message) throws Exception {
    	
    	logger.info(" [x] Received '" + message + "'");
    	Notifications notifications = mapper.readValue(message, Notifications.class);
    	Task task = util.taskFactory(notifications.getNotificationType());
    	task.setNotifications(notifications);
    	task.startTask();
    	
    	logger.info("task: " + task + " was executed");
    	
    }
}
