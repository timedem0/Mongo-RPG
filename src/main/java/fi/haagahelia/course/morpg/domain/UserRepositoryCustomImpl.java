package fi.haagahelia.course.morpg.domain;

import java.util.List;

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
    
    public List<Character> findCharsByUser(String userName) {
    	
		Query query = new Query(Criteria.where("name").is(userName));
		
		User user = new User();
		
		user = mongoTemplate.findOne((query), User.class);
    	
    	if (user == null) {
    		return null;
    	} else {
    		List<Character> characters = user.getCharacters();
    		return characters;
    	}
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
         
    public long updateChar(String userName, Character charToEdit) {
    	
        Query query = new Query(new Criteria().andOperator(
      		  Criteria.where("name").is(userName),
      		  Criteria.where("characters").elemMatch(Criteria.where("charName").is(charToEdit.getCharName()))
      		));
        
        Update update = new Update();
        update.set("characters.$.flavourText", charToEdit.getFlavourText());
        update.set("characters.$.type", charToEdit.getType());
        update.set("characters.$.weapon", charToEdit.getWeapon());
        update.set("characters.$.victories", charToEdit.getVictories());
        update.set("characters.$.defeats", charToEdit.getDefeats());
 
        UpdateResult result = this.mongoTemplate.updateFirst(query, update, User.class);
 
        if (result != null) {
            return result.getModifiedCount();
        }
        return 0;
    }
    
    public long updateCharStats(String userName, Character charToEdit, int victoriesUpdate, int defeatsUpdate) {
    	
        Query query = new Query(new Criteria().andOperator(
      		  Criteria.where("name").is(userName),
      		  Criteria.where("characters").elemMatch(Criteria.where("charName").is(charToEdit.getCharName()))
      		));
        
        Update update = new Update();
        update.set("characters.$.victories", charToEdit.getVictories() + victoriesUpdate);
        update.set("characters.$.defeats", charToEdit.getDefeats() + defeatsUpdate);
 
        UpdateResult result = this.mongoTemplate.updateFirst(query, update, User.class);
 
        if (result != null) {
            return result.getModifiedCount();
        }
        return 0;
    }
    
    public long deleteChar(String userName, String charName) {
 	   
		Query query = new Query(Criteria.where("name").is(userName));
		
		Update update = new Update();
		update.pull("characters", Query.query(Criteria.where("charName").is(charName)));
 
        UpdateResult result = this.mongoTemplate.updateFirst(query, update, User.class);
 
        if (result != null) {
            return result.getModifiedCount();
        }
        return 0;
    }
}
