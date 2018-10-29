package fi.haagahelia.course.morpg.domain;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	
	User findByName(String name);
	List<User> findByRole(String role);
}
