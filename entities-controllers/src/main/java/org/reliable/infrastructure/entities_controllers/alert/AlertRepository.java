package org.reliable.infrastructure.entities_controllers.alert;

import java.sql.Timestamp;
import java.util.List;

import org.reliable.infrastructure.entities_controllers.util.AlertClient;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface AlertRepository extends CrudRepository<Alert, Integer>{

	public Alert findByIdalert(Long id);
	
	@Modifying
	@Transactional
	@Query("UPDATE Alert alert SET alert.timestamp=?2 WHERE alert.idalert=?1")
	public Integer updateTimestamp(Long idalert, Timestamp currentTimestamp);
	
	
	@Modifying
	@Transactional
	@Query("UPDATE AlertClient alertClient SET alertClient.status=?1 WHERE alertClient in ?2")
	public Integer updateStatus(String newStatus, List<AlertClient> notifications);
	
}
