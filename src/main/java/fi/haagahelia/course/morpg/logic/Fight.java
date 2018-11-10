package fi.haagahelia.course.morpg.logic;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import fi.haagahelia.course.morpg.domain.Character;
import fi.haagahelia.course.morpg.domain.Location;
import fi.haagahelia.course.morpg.domain.Monster;
import fi.haagahelia.course.morpg.domain.Type;
import fi.haagahelia.course.morpg.domain.Weapon;

public class Fight {
	
	public static FightResult getFightResult (String userName, Character charToFight, Location location, List<Monster> allMonsters, List<Type> allTypes, List<Weapon> allWeapons, int dice) {
		
		// initiating the result and player statistics
		int battleResult = 0;
		int victoriesUpdate = 0;
		int defeatsUpdate = 0;
		 	
    	// get the location strings
    	String locationName = location.getName();
    	String locationUrl = location.getPicture();
    	
    	// get the player type
    	Type playerType = new Type();
	    for (Type t : allTypes) {
	        if (t.getTypeName().equals(charToFight.getType())) {
	        	playerType = t;
	        }
	    }
    	
	    // get the player weapon
	    Weapon playerWeapon = new Weapon();
	    for (Weapon w : allWeapons) {
	    	if (w.getWeaponName().equals(charToFight.getWeapon())) {
	    		playerWeapon = w;
	    	}
	    }
    	
    	// get a random monster from the location
    	int monsterNumber = allMonsters.size();
    	int randomMonster = ThreadLocalRandom.current().nextInt(0, monsterNumber);
    	Monster monsterToFight = allMonsters.get(randomMonster);
		
		// initiating the character values
		int charAttack = 0;
		int charDefence = 0;
		int charWeaponPower = 0;
		int diceValue = 0;
		String charAttackType = "";
		String charVulnerability = "";
		String charTerrain = "";
		
		// initiating the monster values
		int monsterAttack = 0;
		int monsterDefence = 0;
		String monsterAttackType = "";
		String monsterVulnerability = "";
		
		// initiating the bonuses
		int bonusFromLocation = 0;
		int bonusFromAttack = 0;
		int penaltyFromMonsterAttack = 0;
		
		// populating the values	
    	charAttack = playerType.getAttack();
    	charDefence = playerType.getDefence();
		charAttackType = playerType.getAttackType();
		charVulnerability = playerType.getVulnerability();
		charTerrain = playerType.getTerrain();
	    charWeaponPower = playerWeapon.getPower();
		monsterAttack = monsterToFight.getAttack();
		monsterDefence = monsterToFight.getDefence();
		monsterAttackType = monsterToFight.getAttackType();
		monsterVulnerability = monsterToFight.getVulnerability();
		
		
		// calculating the bonuses
		if (charTerrain.equals(locationName)) {
			bonusFromLocation = 5;
		}
		switch (dice) {
        case 1:
        	diceValue = -12;
            break;
        case 2:
        	diceValue = -10;
            break;
        case 3:
        	diceValue = -8;
            break;
        case 4:
        	diceValue = 8;
            break;
        case 5:
        	diceValue = 10;
            break;
        case 6:
        	diceValue = 12;
            break;
        default:
        	diceValue = 0;
            break;
		}
		switch (charAttackType) {
	        case "fire":
	        	if (monsterVulnerability.equals("fire")) {
	        		bonusFromAttack = 10;
	        	};
	            break;
	        case "nature":
	        	if (monsterVulnerability.equals("nature")) {
	        		bonusFromAttack = 10;
	        	};
	            break;
	        case "physical":
	        	if (monsterVulnerability.equals("physical")) {
	        		bonusFromAttack = 10;
	        	};
	            break;
	        default:
	        	bonusFromAttack = 0;
	            break;
		}
		switch (monsterAttackType) {
	        case "fire":
	        	if (charVulnerability.equals("fire")) {
	        		penaltyFromMonsterAttack = 10;
	        	};
	            break;
	        case "nature":
	        	if (charVulnerability.equals("nature")) {
	        		penaltyFromMonsterAttack = 10;
	        	};
	            break;
	        case "physical":
	        	if (charVulnerability.equals("physical")) {
	        		penaltyFromMonsterAttack = 10;
	        	};
	            break;
	        default:
	        	penaltyFromMonsterAttack = 0;
	            break;
		}
		
		// calculating the player totals
		int playerTotal = charAttack + charDefence + charWeaponPower;
		
		// calculating the monster totals
		int monsterTotal = monsterAttack + monsterDefence;
		
		// calculating the final totals
		battleResult = diceValue + playerTotal + bonusFromLocation + bonusFromAttack - monsterTotal - 5 - penaltyFromMonsterAttack;
		
		// preparing the player statistics update
		if (battleResult > 0) {
			victoriesUpdate = 1;
		} else if (battleResult < 0) {
			defeatsUpdate = 1;
		}
		
		// creating and returning the fight result
		FightResult resultObj = new FightResult(userName, locationName, locationUrl, charToFight, monsterToFight, playerType, playerWeapon,
										dice, diceValue, playerTotal, monsterTotal, bonusFromAttack, bonusFromLocation, penaltyFromMonsterAttack,
										battleResult, victoriesUpdate, defeatsUpdate);
		
		return resultObj;
		
	}
}
