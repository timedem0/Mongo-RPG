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
import fi.haagahelia.course.morpg.domain.Weapon;
import fi.haagahelia.course.morpg.domain.WeaponRepository;

@Component
public class Seeder implements CommandLineRunner {
	private UserRepository userRepo;
	private TypeRepository typeRepo;
	private WeaponRepository weapRepo;
	private LocationRepository locoRepo;

	public Seeder(UserRepository userRepo, WeaponRepository weapRepo, TypeRepository typeRepo, LocationRepository locoRepo) {
		this.userRepo = userRepo;
		this.weapRepo = weapRepo;
		this.typeRepo = typeRepo;
		this.locoRepo = locoRepo;
	}

	@Override
	public void run(String... args) throws Exception {
		
		// create new character types
		Type druid = new Type("Druid", 50, 50, "nature", "fire", "Forest");
		Type warrior = new Type("Warrior", 20, 70, "physical", "nature", "Ruins");
		Type mage = new Type("Mage", 70, 20, "fire", "nature", "Forest");
		
		// create new weapons
		Weapon stick = new Weapon("Stick", "Broken Stick", 1);
		Weapon hammer = new Weapon("Hammer", "Rusty Old Hammer", 2);
		Weapon sword = new Weapon("Sword", "Sword of Justice", 5);
		Weapon staff = new Weapon("Staff", "Staff of the World Tree", 10);
		Weapon skull = new Weapon("Skull", "Skull of Odin", 20);
		
		// create new monsters
		Monster harpy = new Monster("Harpy", 50, 50, "nature", "physical");
		Monster vampire = new Monster("Vampire", 80, 60, "physical", "fire");
		Monster skeleton = new Monster("Skeleton", 1, 1, "physical", "nature");
		
		Monster wolf = new Monster("Wolf", 20, 10, "physical", "physical");
		Monster ancient = new Monster("Ancient", 10, 50, "fire", "nature");
		Monster satyr = new Monster("Satyr", 70, 20, "nature", "fire");
		
		Monster rat = new Monster("Rat", 10, 20, "physical", "fire");
		Monster maniac = new Monster("Maniac", 70, 5, "nature", "fire");
		Monster ripper = new Monster("Ripper", 60, 80, "physical", "nature");
		
		// create new locations
		Location ruins = new Location("Ruins", "A dark and hollow place", "url", Arrays.asList(harpy, skeleton, vampire));
		Location forest = new Location("Forest", "An enchanted elvish forest", "url", Arrays.asList(ancient, wolf, satyr));
		Location hospital = new Location("Hospital", "An abandoned mental institution", "url", Arrays.asList(rat, maniac, ripper));
		
		// create new characters
		Character cerise = new Character("Cerise", "A sweet girl from a small village", druid.getTypeName(), stick.getWeaponName(), 0, 0);
		Character peticel = new Character("Peticel", "A mighty warrior from Thunder Bluff", warrior.getTypeName(), sword.getWeaponName(), 7, 2);
		Character ellymoo = new Character("Ellymoo", "A wise spell weaver from the plains of Mulgore", mage.getTypeName(), staff.getWeaponName(), 3, 0);
		
		// create new users
		User admin = new User("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN", Arrays.asList());
		User user = new User("testuser", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER",  Arrays.asList(cerise, peticel, ellymoo));
		
		// drop all previous users and types, if any
		this.userRepo.deleteAll();
		this.typeRepo.deleteAll();
		this.weapRepo.deleteAll();
		this.locoRepo.deleteAll();
		
		// add all to the database
		this.weapRepo.save(stick);
		this.weapRepo.save(hammer);
		this.weapRepo.save(sword);
		this.weapRepo.save(staff);
		this.weapRepo.save(skull);
		
		this.typeRepo.save(druid);
		this.typeRepo.save(warrior);
		this.typeRepo.save(mage);
		
		this.locoRepo.save(ruins);
		this.locoRepo.save(forest);
		this.locoRepo.save(hospital);
		
		this.userRepo.save(admin);
		this.userRepo.save(user);
	}	
}
