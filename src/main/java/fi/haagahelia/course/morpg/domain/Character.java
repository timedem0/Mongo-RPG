package fi.haagahelia.course.morpg.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class Character {
	@Id
	@Field
	private String charName;
	@Field
	private Type type;
	@Field
	private int level;
	@Field
	private String weapon;
	
	public Character(String charName, Type type, int level, String weapon) {
		this.charName = charName;
		this.type = type;
		this.level = level;
		this.weapon = weapon;
	}

	public String getCharName() {
		return charName;
	}

	public void setCharName(String charName) {
		this.charName = charName;
	}

	public Type getType() {
		return type;
	}

	public void setTypeName(Type type) {
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
	
}
