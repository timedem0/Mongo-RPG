package fi.haagahelia.course.morpg.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
	
	@Autowired
    MongoTemplate mongoTemplate;
	
    public long deleteChar(String userName, String charName) {
    	   
        Query query = new Query(new Criteria().andOperator(
        		  Criteria.where("name").is(userName),
        		  Criteria.where("characters").elemMatch(Criteria.where("charName").is(charName))
        		));
        
        Update update = new Update();
        update.set("characters.$.isDeleted", true);
 
        UpdateResult result = this.mongoTemplate.updateFirst(query, update, User.class);
 
        if (result != null) {
            return result.getModifiedCount();
        }
        return 0;
    }
    
    public long insertChar(String userName, Character newChar) {
 	       
        Query query = new Query(Criteria.where("name").is(userName));
        
        Update update = new Update();
        update.push("characters", newChar);
 
        UpdateResult result = this.mongoTemplate.updateFirst(query, update, User.class);
 
        if (result != null) {
            return result.getModifiedCount();
        }
 
        return 0;
    }
     
    public Character findCharByName(String userName, String charName) {
    	
        Query query = new Query(new Criteria().andOperator(
      		  Criteria.where("name").is(userName),
      		  Criteria.where("characters").elemMatch(Criteria.where("charName").is(charName))
      		));
        
        User user = new User();
        Character charToEdit = new Character();
        
    	user = mongoTemplate.findOne((query), User.class);
    	
    	if (user == null) {
    		return null;
    	} else {
    		charToEdit = user.getCharByName(charName);
    		return charToEdit;
    	}
    }
    
    public long updateChar(String userName, Character charToEdit) {
    	
        Query query = new Query(new Criteria().andOperator(
      		  Criteria.where("name").is(userName),
      		  Criteria.where("characters").elemMatch(Criteria.where("charName").is(charToEdit.getCharName()))
      		));
        
        Update update = new Update();
        update.set("characters.$.weapon", charToEdit.getWeapon());
        update.set("characters.$.type", charToEdit.getType());
        update.set("characters.$.level", charToEdit.getLevel());
        update.set("characters.$.isDeleted", false);
 
        UpdateResult result = this.mongoTemplate.updateFirst(query, update, User.class);
 
        if (result != null) {
            return result.getModifiedCount();
        }
        return 0;
    }
}
