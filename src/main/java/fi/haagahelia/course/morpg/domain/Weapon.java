package fi.haagahelia.course.morpg.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Weapons")
public class Weapon {
	@Id
	@Field
	private String weaponName;
	@Field
	private String weaponText;
	@Field
	private int power;
	
	public Weapon() {
		
	}
	
	public Weapon(String weaponName, String weaponText, int power) {
		this.weaponName = weaponName;
		this.weaponText = weaponText;
		this.power = power;
	}

	public String getWeaponName() {
		return weaponName;
	}

	public void setWeaponName(String weaponName) {
		this.weaponName = weaponName;
	}

	public String getWeaponText() {
		return weaponText;
	}

	public void setWeaponText(String weaponText) {
		this.weaponText = weaponText;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}
}
