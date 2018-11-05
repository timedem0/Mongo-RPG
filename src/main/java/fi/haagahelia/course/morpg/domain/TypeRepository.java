package fi.haagahelia.course.morpg.domain;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends MongoRepository<Type, String> {
	
	public List<Type> findByTypeName(String name);
	public void deleteByTypeName(String name);
}
