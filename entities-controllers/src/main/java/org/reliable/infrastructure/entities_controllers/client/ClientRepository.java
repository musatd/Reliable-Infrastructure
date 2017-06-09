package org.reliable.infrastructure.entities_controllers.client;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ClientRepository extends CrudRepository<Client, Integer>{

	public Client findByToken(String token);
	
	public Client findByPhone(String phone);
	
	@Transactional
    public Long deleteByToken(String token);
	
	@Query("SELECT client FROM Client client WHERE EXISTS (SELECT city FROM client.cities city WHERE city.name IN (:names))")
	public List<Client> findClientsSubscribedToCities(@Param("names") List<String> names);
    
}
