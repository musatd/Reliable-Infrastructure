package org.reliable.infrastructure.entities_controllers.client;

import java.util.logging.Logger;

import org.reliable.infrastructure.entities_controllers.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {
	
	private Logger logger = Logger.getLogger(ClientController.class.getName());
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private Util clientUtil;
	
	
	/**
	 * Save a client to the database
	 * 
	 * @param client to be saved
	 * @return True if client was saved, False otherwise.
	 */
	@RequestMapping(value = "/clients/saveClient", produces = MediaType.APPLICATION_JSON_VALUE)
	public Boolean saveClient(@RequestBody User newUser) {
		
		String token = newUser.getToken();
		logger.info("clientRepository byToken() invoked: " + token);
		Client foundClient = clientRepository.findByToken(token);
		logger.info("clientRepository byToken() found: " + foundClient);
		
		if (foundClient == null) {
			foundClient = new Client();
		}
		
		clientUtil.prepareClientForSave(foundClient, newUser);

		logger.info("clientRepository save() invoked: " + foundClient);
		Client savedClient = clientRepository.save(foundClient);
		logger.info("clientRepository save() found: " + savedClient);

		return savedClient == null ? false : true;
	}
	
	
	/**
	 * If a client has the specified token, delete it.
	 * 
	 * @param token used to find the client
	 * @return True if client with specified token was deleted, False otherwise.
	 */
	@RequestMapping(value = "/clients/deleteClient", produces = MediaType.APPLICATION_JSON_VALUE)
	public Boolean deleteClient(@RequestBody String token) {
		
		logger.info("clientRepository deleteByToken() invoked: " + token);
		Long deleted = clientRepository.deleteByToken(token);
		logger.info("clientRepository deleteByToken() deleted: " + deleted + " entries");
		
		return deleted == 0 ? false : true;
	}
	

}
