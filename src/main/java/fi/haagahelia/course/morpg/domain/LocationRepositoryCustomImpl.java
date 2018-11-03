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
	
	public long updateLocation(Location locationToEdit) {
		
		Query query = new Query(Criteria.where("name").is(locationToEdit.getName()));
		
        Update update = new Update();
        update.set("description", locationToEdit.getDescription());
        update.set("picture", locationToEdit.getPicture());

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
	
    public long insertMonster(String locationName, Monster newMonster) {
	       
        Query query = new Query(Criteria.where("name").is(locationName));
        
        Update update = new Update();
        update.push("monsters", newMonster);
 
        UpdateResult result = this.mongoTemplate.updateFirst(query, update, Location.class);
 
        if (result != null) {
            return result.getModifiedCount();
        }
        return 0;
    }
    
    public long updateMonster(String locationName, Monster monsterToEdit) {
    	
        Query query = new Query(new Criteria().andOperator(
      		  Criteria.where("name").is(locationName),
      		  Criteria.where("monsters").elemMatch(Criteria.where("monsterName").is(monsterToEdit.getMonsterName()))
      		));
        
        Update update = new Update();
        update.set("monsters.$.attack", monsterToEdit.getAttack());
        update.set("monsters.$.defence", monsterToEdit.getDefence());
        update.set("monsters.$.attackType", monsterToEdit.getAttackType());
        update.set("monsters.$.vulnerability", monsterToEdit.getVulnerability());
 
        UpdateResult result = this.mongoTemplate.updateFirst(query, update, Location.class);
 
        if (result != null) {
            return result.getModifiedCount();
        }
        return 0;
    }

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
}
