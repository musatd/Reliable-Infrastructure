package org.reliable.infrastructure.AMQPSender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;

public class NotificationsDeserializer extends StdDeserializer<Notifications> {
	private static final long serialVersionUID = 1L;


	public NotificationsDeserializer() { 
        this(null); 
    } 
 
    public NotificationsDeserializer(Class<?> vc) { 
        super(vc); 
    }
	
	
	@Override
	public Notifications deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		
		JsonNode node = p.getCodec().readTree(p);
		Long idalert = new Long((Integer)((IntNode) node.get("idalert")).numberValue());
		String message = node.get("message").asText();
		String priority = node.get("priority").asText();
		String status = node.get("alertClients").findValue("status").asText();
		List<String> tokens = new ArrayList<>();
		List<String> phoneNumbers = new ArrayList<>();
		

		JsonNode alertClients = node.get("alertClients");
		
		if (alertClients.isArray()) {
		    for (JsonNode objNode : alertClients) {
		    	JsonNode clientNode = objNode.get("client");
		    	
		    	String token = clientNode.get("token").asText();
		    	String phone = clientNode.get("phone").asText();
		        System.out.println(token + " " + phone);
	        	tokens.add(token);
	        	phoneNumbers.add(phone);
		    }
		}
		
		Notifications notifications = new Notifications(idalert, status, message, priority,
																		tokens, phoneNumbers);

		
		return notifications;
	}

}
