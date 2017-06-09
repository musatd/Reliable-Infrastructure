package org.reliable.infrastructure.entities_controllers.sidAlert;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

public interface SidAlertRepository  extends CrudRepository<SidAlert, Integer> {
	
	public SidAlert findBySid(String sid);
	
	@Transactional
    public Long deleteBySid(String sid);
}
