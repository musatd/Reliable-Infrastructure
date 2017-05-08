package org.reliable.infrastructure.entities_controllers.city;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CityRepository extends CrudRepository<City, Integer>{

	@Query("select city from City city where city.name in :names")
    List<City> getByNameInList(@Param("names") List<String> names);
}
