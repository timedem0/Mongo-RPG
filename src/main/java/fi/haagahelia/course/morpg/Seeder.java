package fi.haagahelia.course.morpg;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fi.haagahelia.course.morpg.domain.Character;
import fi.haagahelia.course.morpg.domain.Type;
import fi.haagahelia.course.morpg.domain.User;
import fi.haagahelia.course.morpg.domain.UserRepository;

@Component
public class Seeder implements CommandLineRunner {
	private UserRepository userRepo;

	public Seeder(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public void run(String... args) throws Exception {
		
		// create new character types
		Type druid = new Type("Druid", 50, 50, "nature", "fire", "forest");
		Type warrior = new Type("Warrior", 20, 70, "physical", "frost", "plains");
		Type mage = new Type("Mage", 70, 20, "fire", "nature", "city");
		
		// create new characters
		Character drucilla = new Character("Drucilla", druid, 1, "none");
		Character hammer = new Character("Hammer", warrior, 1, "none");
		Character ciri = new Character("Hammer", mage, 1, "stick");
		
		// create new users
		User admin = new User("admin", "omgwtf", 0, Arrays.asList());
		User katja = new User("katja", "123", 1,  Arrays.asList(drucilla, hammer));
		User tudor = new User("tudor", "123", 1,  Arrays.asList(ciri));
		
		// drop all previous users, if any
		this.userRepo.deleteAll();
		
		// add the users to the database
		this.userRepo.save(admin);
		this.userRepo.save(katja);
		this.userRepo.save(tudor);
	}	
}
