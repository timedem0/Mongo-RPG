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
	private String flavourText;
	@Field
	private String type;
	@Field
	private String weapon;
	@Field
	private int victories = 0;
	@Field
	private int defeats = 0;
	
	public Character() {
		
	}
	
	public Character(String charName, String flavourText, String type, String weapon, int victories, int defeats) {
		this.charName = charName;
		this.flavourText = flavourText;
		this.type = type;
		this.weapon = weapon;
		this.victories = victories;
		this.defeats = defeats;
	}

	public String getCharName() {
		return charName;
	}

	public void setCharName(String charName) {
		this.charName = charName;
	}
	
	public String getFlavourText() {
		return flavourText;
	}

	public void setFlavourText(String flavourText) {
		this.flavourText = flavourText;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWeapon() {
		return weapon;
	}

	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}
	
	public int getVictories() {
		return victories;
	}

	public void setVictories(int victories) {
		this.victories = victories;
	}
	
	public int getDefeats() {
		return defeats;
	}

	public void setDefeats(int defeats) {
		this.defeats = defeats;
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
