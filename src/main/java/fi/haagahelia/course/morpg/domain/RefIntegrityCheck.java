package fi.haagahelia.course.morpg.domain;

import java.util.ArrayList;
import java.util.List;

public class RefIntegrityCheck {
	
    public static void check(UserRepository userRepo, UserRepositoryCustom userRepoCustom, TypeRepository typeRepo, WeaponRepository weapRepo, LocationRepository locoRepo, LocationRepositoryCustom locoRepoCustom) {
        
    	// initialize everything
        List<User> allUsers = userRepo.findAll();
        List<Type> allTypes = typeRepo.findAll();
        List<Weapon> allWeapons = weapRepo.findAll();
        List<Location> allLocations = locoRepo.findAll();
        List<String> allTypeNames = new ArrayList<String>();
        List<String> allWeaponNames = new ArrayList<String>();
        
        // create a default type
        Type defaultType = new Type();
        defaultType.setTypeName("Peasant");
        defaultType.setAttack(1);
        defaultType.setDefence(1);
        defaultType.setAttackType("physical");
        defaultType.setVulnerability("physical");
        defaultType.setTerrain("Air");
        
        // create a default weapon
        Weapon defaultWeapon = new Weapon("Knife", "Dull knife", 1);
        
        // create a default monster
        Monster defaultMonster = new Monster("Rat", 1, 1, "physical", "fire");
        
        // check if someone deleted everything from Types and Weapons Collections
        if (allTypes == null) {
        	typeRepo.insert(defaultType);
        }
        if (allWeapons == null) {
        	weapRepo.insert(defaultWeapon);
        }
        
        // get a list of all current Types and Weapons for future comparisons
		for (Type t : allTypes) {
			allTypeNames.add(t.getTypeName());
		}
		for (Weapon w : allWeapons) {
			allWeaponNames.add(w.getWeaponName());
		}
        
		// iterate through all Characters and check for missing Types and Weapons
		// if found, insert defaults
        for (User u : allUsers) {
        	List<Character> userChars = userRepoCustom.findCharsByUser(u.getName());
        	for (Character c : userChars) {
        		if (!allTypeNames.contains(c.getType())) {
        			if (!allTypeNames.contains("Default")) { // check if Default Type already exists - if not, insert
        				typeRepo.insert(defaultType);
        			}
        			userRepoCustom.insertDefaultType(u.getName(), c.getCharName()); // add Default Type to Character
        		}
        		if (!allWeaponNames.contains(c.getWeapon())) {
        			if (!allWeaponNames.contains("Default")) { // check if Default Weapon already exists - if not, insert
        				weapRepo.insert(defaultWeapon);
        			}
        			userRepoCustom.insertDefaultWeapon(u.getName(), c.getCharName()); // add Default Weapon to Character
        		}
        	}
        }
		// iterate through all Locations and check for missing Monsters
		// if found, insert default
        for (Location l : allLocations) {
        	List<Monster> monstersAtLocation = locoRepoCustom.findMonsterByLocation(l.getName());
        	if (monstersAtLocation.isEmpty()) {
        		locoRepoCustom.insertMonster(l.getName(), defaultMonster);
        	}
        }
    }
}
