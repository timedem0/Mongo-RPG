package fi.haagahelia.course.morpg.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Types")
public class Type {
	@Id
	@Field
	private String typeName;
	@Field
	private int attack;
	@Field
	private int defence;
	@Field
	private String attackType;
	@Field
	private String vulnerability;
	@Field
	private String terrain;
	
	public Type() {
		
	}
	
	public Type(String typeName, int attack, int defence, String attackType, String vulnerability, String terrain) {
		this.typeName = typeName;
		this.attack = attack;
		this.defence = defence;
		this.attackType = attackType;
		this.vulnerability = vulnerability;
		this.terrain = terrain;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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

	public String getTerrain() {
		return terrain;
	}

	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}
}
