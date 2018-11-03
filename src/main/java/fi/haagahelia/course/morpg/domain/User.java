package fi.haagahelia.course.morpg.domain;

import java.util.ArrayList;
// import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Document(collection = "Users")
public class User {
	@Id
	@Indexed
	@Field
	private String id;
	@Field
	private String name;
	@JsonIgnore
	@Field
	private String password;
	@Field
	private String role;
	@Field
	private List<Character> characters;
	
	public User() {
		this.characters = new ArrayList<>();
	}
	
	public User(String name, String password, String role, List<Character> characters) {
		this.name = name;
		this.password = password;
		this.role = role;
		this.characters = characters;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Character> getCharacters() {
		return characters;
	}

	public void setCharacters(List<Character> characters) {
		this.characters = characters;
	}	
	
	// method to return a single Character Object from the nested documents of the Users Collection
	public Character getCharByName(String charName) {
		
	    for (Character i : characters) {
	        if (i.getCharName().equals(charName)) {
	        	return i;
	        }
	    }
	    return null;
	}
	
	// method to return a JSON String
    @Override
    public String toString() {
    	ObjectMapper mapper = new ObjectMapper();
    	
    	String jsonString = "";
		try {
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			jsonString = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	return jsonString;
    }
}
