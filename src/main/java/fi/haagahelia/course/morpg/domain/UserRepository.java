package fi.haagahelia.course.morpg.domain;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
// import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	
	User findByName(String name);
	List<User> findByRole(String role);
	
	/*
	@Query(value="{ 'characters.charName': ?0 }")
	List<User> findByCharName(String charName);
		
	@Query(value="{ 'id' : ?0 }", fields="{ 'type' : ?0 }", delete = true)
	List<User> deleteCharacter(String id, String type);
	*/
}