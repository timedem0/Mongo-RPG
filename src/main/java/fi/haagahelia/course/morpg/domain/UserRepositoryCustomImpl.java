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
	
    public List<User> testQuery (String nameGet) {
    	
        Query query = new Query(Criteria.where("name").is(nameGet));
        List<User> users = mongoTemplate.find(query,User.class);
        
        return users;
        
        /*
        Update update = new Update();
        update.set("fullName", fullName);
        update.set("hireDate", hireDate);
         
 
        UpdateResult result = this.mongoTemplate.updateFirst(query, update, User.class);
        
       */
    }
    
    public long deleteChar (String userName, String charName) {
    	   
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
}
