package fi.haagahelia.course.morpg.domain;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	
	public User findByName(String name);
	public List<User> findByRole(String role);
	
	@Query(value="{ 'name' : ?0 }", delete = true)
	public long deleteByUserName(String name);
}
