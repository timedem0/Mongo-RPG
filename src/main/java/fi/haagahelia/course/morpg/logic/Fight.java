package fi.haagahelia.course.morpg.logic;

import java.util.List;

import fi.haagahelia.course.morpg.domain.Character;
import fi.haagahelia.course.morpg.domain.Monster;
import fi.haagahelia.course.morpg.domain.Type;

public class Fight {
	
	private String userName;
	private String charName;
	private String locationName;
	
	public Fight() {
		
	}
	
	public Fight(String userName, String charName, String locationName) {
		this.userName = userName;
		this.charName = charName;
		this.locationName = locationName;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getCharName() {
		return charName;
	}

	public void setCharName(String charName) {
		this.charName = charName;
	}
	
	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	public int getFightResult (Character charToFight, Monster monsterToFight, List<Type> allTypes, int dice) {
		
		int result = 0;
		int charAttack = 0;
		
	    for (Type i : allTypes) {
	        if (i.getTypeName().equals(charToFight.getType())) {
	        	charAttack = i.getAttack();
	        }
	    }
		
		result = charToFight.getLevel() + charAttack + monsterToFight.getAttack() + dice;
		
		return result;
		
	}
}
