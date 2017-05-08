package org.reliable.infrastructure.entities_controllers.city;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityController {
	
	private Logger logger = Logger.getLogger(CityController.class.getName());
	
	@Autowired
	private CityRepository cityRepository;
	
	
	/**
	 * Fetch cities from database that are in the variable "cities".
	 * I don't use this method in my project.
	 * 
	 * @param cities
	 * @return The found cities.
	 */
	@RequestMapping("/cities/getByNameInList")
	public List<City> getByNameInList(@RequestBody List<String> cities) {

		logger.info("cityRepository getByNameInList() invoked: " + cities);
		List<City> foundCities = cityRepository.getByNameInList(cities);
		logger.info("cityRepository getByNameInList() found: " + foundCities);

		return foundCities;
	}

}
