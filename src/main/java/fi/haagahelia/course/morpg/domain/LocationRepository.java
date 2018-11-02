package fi.haagahelia.course.morpg.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends MongoRepository<Location, String> {
	
	public Location findByName(String name);
	
	@Query(value="{ 'name' : ?0 }", delete = true)
	public Long deleteByLocationName(String name);
}
