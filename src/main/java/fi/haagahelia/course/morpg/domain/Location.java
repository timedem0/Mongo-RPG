package fi.haagahelia.course.morpg.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Document(collection = "Locations")
public class Location {
	@Id
	@Indexed
	@Field
	private String id;
	@Field
	private String name;
	@Field
	private String description;
	@Field
	private String picture;
	@Field
	private List<Monster> monsters;
	
	public Location() {
		this.monsters = new ArrayList<>();
	}
	
	public Location(String name, String description, String picture, List<Monster> monsters) {
		this.name = name;
		this.description = description;
		this.picture = picture;
		this.monsters = monsters;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPicture() {
		return picture;
	}
	
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public List<Monster> getMonsters() {
		return monsters;
	}
	
	public void setMonsters(List<Monster> monsters) {
		this.monsters = monsters;
	}
		
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
