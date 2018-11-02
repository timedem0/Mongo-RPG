package fi.haagahelia.course.morpg.domain;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

@Repository
public class LocationRepositoryCustomImpl implements LocationRepositoryCustom {
	
	@Autowired
    MongoTemplate mongoTemplate;
	
	public long deleteMonster(String locationName, String monsterName) {
	
		Query query = new Query(Criteria.where("name").is(locationName));
		
		Update update = new Update();
		update.pull("monsters", Query.query(Criteria.where("monsterName").is(monsterName)));
		
		UpdateResult result = this.mongoTemplate.updateFirst(query, update, Location.class);
		
		if (result != null) {
		    return result.getModifiedCount();
		}
		return 0;
	}
	
	public List<Monster> findMonsterByLocation(String locationName) {
		
		Query query = new Query(Criteria.where("name").is(locationName));
		
		Location location = new Location();
		
    	location = mongoTemplate.findOne((query), Location.class);
    	
    	if (location == null) {
    		return null;
    	} else {
    		List<Monster> monsters = location.getMonsters();
    		return monsters;
    	}
	}
}
