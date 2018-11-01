package fi.haagahelia.course.morpg.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Character {
	@Id
	@Field
	private String charName;
	@Field
	private String type;
	@Field
	private int level = 1;
	@Field
	private String weapon;
	@Field
	private boolean isDeleted = false;
	
	public Character() {
		
	}
	
	public Character(String charName, String type, int level, String weapon, boolean isDeleted) {
		this.charName = charName;
		this.type = type;
		this.level = level;
		this.weapon = weapon;
		this.isDeleted = isDeleted;
	}

	public String getCharName() {
		return charName;
	}

	public void setCharName(String charName) {
		this.charName = charName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getWeapon() {
		return weapon;
	}

	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}
	
	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
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
