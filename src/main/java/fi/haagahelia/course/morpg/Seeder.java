package fi.haagahelia.course.morpg;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fi.haagahelia.course.morpg.domain.Character;
import fi.haagahelia.course.morpg.domain.Monster;
import fi.haagahelia.course.morpg.domain.Location;
import fi.haagahelia.course.morpg.domain.LocationRepository;
import fi.haagahelia.course.morpg.domain.Type;
import fi.haagahelia.course.morpg.domain.TypeRepository;
import fi.haagahelia.course.morpg.domain.User;
import fi.haagahelia.course.morpg.domain.UserRepository;

@Component
public class Seeder implements CommandLineRunner {
	private UserRepository userRepo;
	private TypeRepository typeRepo;
	private LocationRepository locoRepo;

	public Seeder(UserRepository userRepo, TypeRepository typeRepo, LocationRepository locoRepo) {
		this.userRepo = userRepo;
		this.typeRepo = typeRepo;
		this.locoRepo = locoRepo;
	}

	@Override
	public void run(String... args) throws Exception {
		
		// create new character types
		Type druid = new Type("Druid", 50, 50, "nature", "fire", "Forest");
		Type warrior = new Type("Warrior", 20, 70, "physical", "nature", "plains");
		Type mage = new Type("Mage", 70, 20, "fire", "nature", "Ruins");
		
		// create new monsters
		Monster harpy = new Monster("Harpy", 50, 50, "nature", "physical");
		Monster vampire = new Monster("Vampire", 80, 60, "physical", "fire");
		Monster skeleton = new Monster("Skeleton", 1, 1, "physical", "nature");
		
		Monster wolf = new Monster("Naga", 20, 10, "physical", "physical");
		Monster ancient = new Monster("Ancient", 10, 50, "fire", "nature");
		Monster satyr = new Monster("Satyr", 70, 20, "nature", "fire");
		
		// create new locations
		Location ruins = new Location("Ruins", "A dark and hollow place", "url", Arrays.asList(harpy, skeleton, vampire));
		Location forest = new Location("Forest", "An enchanted elvish forest", "url", Arrays.asList(ancient, wolf, satyr));
		
		// create new characters
		Character drucilla = new Character("Drucilla", druid.getTypeName(), 1, "none", false);
		Character hammer = new Character("Doomhammer", warrior.getTypeName(), 1, "Hammer of Justice", false);
		Character ciri = new Character("Ciri", mage.getTypeName(), 1, "Staff of the World Tree", false);
		
		// create new users
		User admin = new User("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN", Arrays.asList());
		User user = new User("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER",  Arrays.asList(drucilla, hammer, ciri));
		
		// drop all previous users and types, if any
		this.typeRepo.deleteAll();
		this.locoRepo.deleteAll();
		this.userRepo.deleteAll();
		
		
		// add all to the database
		this.typeRepo.save(druid);
		this.typeRepo.save(warrior);
		this.typeRepo.save(mage);
		
		this.locoRepo.save(ruins);
		this.locoRepo.save(forest);
		
		this.userRepo.save(admin);
		this.userRepo.save(user);
	}	
}
