package fi.haagahelia.course.morpg.domain;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileNameRepository extends MongoRepository<FileName, String> {
	
	public List<FileName> findAll();
}
