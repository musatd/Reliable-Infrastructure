package org.reliable.infrastructure.AMQPSender;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SenderService {
	
	@Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue queue;
    
    @Autowired
    private ObjectMapper mapper;
    
    @Autowired
    private Util util;
    
	private Logger logger = Logger.getLogger(SenderService.class.getName());
    
    private int priority;
	
	
	public void sendDataToRabbitMQServer(Notifications notifications) throws Exception {
		
		List<String> tokens = notifications.getTokens();
		List<String> phoneNumbers = notifications.getPhoneNumbers();
		priority = Integer.parseInt(notifications.getPriority());
		int nrSubscribers = tokens.size();
		String type = notifications.getNotificationType();
		int chunkSize = util.getChunkSize(type);
		int nrMessages = nrSubscribers / chunkSize;
		nrMessages = nrSubscribers % chunkSize == 0 ? nrMessages : nrMessages + 1;
		
		logger.info("start sending messages to priority-queue " + queue.getName());
		for (int i = 0; i < nrMessages; i++) {
			int startIndex = i * chunkSize;
			int stopIndex = startIndex + chunkSize;
			stopIndex = stopIndex > nrSubscribers ? nrSubscribers : stopIndex;
			
			List<String> subTokens = tokens.subList(startIndex, stopIndex);
			List<String> subPhoneNumbers = phoneNumbers.subList(startIndex, stopIndex);
			notifications.setTokens(subTokens);
			notifications.setPhoneNumbers(subPhoneNumbers);
			
			String message = mapper.writeValueAsString(notifications);
			this.template.convertAndSend("", queue.getName(), message, new MessagePostProcessor() {

	            @Override
	            public Message postProcessMessage(Message message) throws AmqpException {
	                 message.getMessageProperties().setPriority(priority);
	                return message;
	            }
	        });
			
			logger.info(message + " was sent to priority-queue " + queue.getName());
		}
		
	}

}
