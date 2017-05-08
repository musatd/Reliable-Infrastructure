package org.reliable.infrastructure.entities_controllers.alert;

import org.springframework.data.repository.CrudRepository;

public interface AlertRepository extends CrudRepository<Alert, Integer>{

	public Alert findByIdalert(Integer id);
}
