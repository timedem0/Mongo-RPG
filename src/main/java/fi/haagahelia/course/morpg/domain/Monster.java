package fi.haagahelia.course.morpg.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Document(collection = "Monsters")
public class Monster {
	@Id
	@Field
	private String monsterName;
	@Field
	private int attack;
	@Field
	private int defence;
	@Field
	private String attackType;
	@Field
	private String vulnerability;
	
	public Monster() {
		
	}
	
	public Monster(String monsterName, int attack, int defence, String attackType, String vulnerability) {
		this.monsterName = monsterName;
		this.attack = attack;
		this.defence = defence;
		this.attackType = attackType;
		this.vulnerability = vulnerability;
	}
	
	public String getMonsterName() {
		return monsterName;
	}

	public void setMonsterName(String typeName) {
		this.monsterName = typeName;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefence() {
		return defence;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}

	public String getAttackType() {
		return attackType;
	}

	public void setAttackType(String attackType) {
		this.attackType = attackType;
	}
	
	public String getVulnerability() {
		return vulnerability;
	}

	public void setVulnerability(String vulnerability) {
		this.vulnerability = vulnerability;
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
