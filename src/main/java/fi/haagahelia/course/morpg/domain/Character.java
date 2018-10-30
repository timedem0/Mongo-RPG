package fi.haagahelia.course.morpg.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class Character {
	@Id
	@Field
	private String charName;
	@Field
	private String type;
	@Field
	private int level;
	@Field
	private String weapon;
	@Field
	private boolean isDeleted;
	
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

	public void setTypeName(String type) {
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
}
