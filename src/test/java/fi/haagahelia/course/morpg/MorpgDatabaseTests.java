package fi.haagahelia.course.morpg;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fi.haagahelia.course.morpg.domain.User;
import fi.haagahelia.course.morpg.domain.UserRepository;
import fi.haagahelia.course.morpg.domain.UserRepositoryCustom;
import fi.haagahelia.course.morpg.domain.Character;
import fi.haagahelia.course.morpg.domain.Weapon;
import fi.haagahelia.course.morpg.domain.WeaponRepository;
import fi.haagahelia.course.morpg.domain.Monster;
import fi.haagahelia.course.morpg.domain.Location;
import fi.haagahelia.course.morpg.domain.LocationRepository;
import fi.haagahelia.course.morpg.domain.LocationRepositoryCustom;
import fi.haagahelia.course.morpg.domain.Type;
import fi.haagahelia.course.morpg.domain.TypeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MorpgDatabaseTests {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserRepositoryCustom userRepoCustom;
	
	@Autowired
	private LocationRepository locoRepo;
	
	@Autowired
	private LocationRepositoryCustom locoRepoCustom;
	
	@Autowired
	private TypeRepository typeRepo;
	
	@Autowired
	private WeaponRepository weapRepo;
	
	// User Repository
    @Test
    public void userRepository() {
    	
    	// clear the repository
    	userRepo.deleteAll();
    	
    	// insert new user
    	List<Character> newCharList = new ArrayList<Character>();
    	User newUser = new User("new-test-user", "password", "TEST-USER", newCharList);
    	userRepo.save(newUser);
    	assertThat(newUser.getId()).isNotNull();
    	
    	// find new user
        List<User> users = userRepo.findByRole("TEST-USER");
        assertThat(users).hasSize(1);	
        
        // delete new user
        userRepo.deleteById(users.get(0).getId());
        List<User> newUsers = userRepo.findAll();
    	assertThat(newUsers).hasSize(0);
    }
    
	// User Custom Repository
    @Test
    public void userCustomRepository() {
    	
    	// clear the repository
    	userRepo.deleteAll();
    	
    	// insert new character for a specific user
    	List<Character> newCharList = new ArrayList<Character>();
    	Character newChar = new Character();
    	newChar.setCharName("TEST-CHAR");
    	newCharList.add(newChar);
    	User newUser = new User("new-test-user", "password", "TEST-USER", newCharList);
    	userRepo.save(newUser);
    	assertThat(newUser.getId()).isNotNull();
    	assertThat(newUser.getCharacters().contains(newChar));
    	
    	// find character belonging to certain user by character name
    	assertThat(userRepoCustom.findCharByName("new-test-user", "TEST-CHAR")).isNotNull();
        
        // delete new character
        userRepoCustom.deleteChar("new-test-user", "TEST-CHAR");
        User userToProbe = userRepo.findByName("new-test-user");
    	assertThat(userToProbe.getCharacters()).hasSize(0);
    }   
    
	// Location Repository
    @Test
    public void locationRepository() {
    	
    	// clear the repository
    	locoRepo.deleteAll();
    	
    	// insert new location
    	List<Monster> newMonsterList = new ArrayList<Monster>();
    	Location newLocation = new Location("new-test-location", "a", "b", newMonsterList);
    	locoRepo.save(newLocation);
    	assertThat(newLocation.getId()).isNotNull();
    	
    	// find new location
        Location locationToFind = locoRepo.findByName("new-test-location");
        assertThat(locationToFind.getId()).isNotNull();	
        
        // delete locations
        locoRepo.deleteAll();
        List<Location> newLocations = locoRepo.findAll();
    	assertThat(newLocations).hasSize(0);
    }
    
	// Location Custom Repository
    @Test
    public void locationRepositoryCustom() {
    	
    	// clear the repository
    	locoRepo.deleteAll();
    	
    	// insert new monster at location
    	Monster newMonster = new Monster();
    	newMonster.setMonsterName("TEST-MONSTER");
    	List<Monster> newMonsterList = new ArrayList<Monster>();
    	newMonsterList.add(newMonster);
    	Location newLocation = new Location("new-test-location", "a", "b", newMonsterList);
    	locoRepo.save(newLocation);
    	assertThat(newLocation.getId()).isNotNull();
    	assertThat(newLocation.getMonsters().contains(newMonster));
    	
    	// find monster by location
        Location locationToSearch = locoRepo.findByName("new-test-location");
        List<Monster> monsterList = locoRepoCustom.findMonsterByLocation(locationToSearch.getName());
        Monster monsterToFind = monsterList.get(0);
        assertThat(monsterToFind.getMonsterName()).isEqualTo("TEST-MONSTER");
        
        // delete monster
        locoRepoCustom.deleteMonster("new-test-location", "TEST-MONSTER");
        Location locationToProbe = locoRepo.findByName("new-test-location");
    	assertThat(locationToProbe.getMonsters()).hasSize(0);
    }
 
	// Type Repository
    @Test
    public void typeRepository() {
    	
    	// clear the repository
    	typeRepo.deleteAll();
    	
    	// insert new type
    	Type newType = new Type();
    	newType.setTypeName("new-test-type");
    	typeRepo.save(newType);
    	
    	// find new type
        List<Type> types = typeRepo.findByTypeName("new-test-type");
        assertThat(types).hasSize(1);
        assertThat(types.get(0).getTypeName()).isEqualTo("new-test-type");
        
        // delete type
        typeRepo.deleteByTypeName(types.get(0).getTypeName());
        List<Type> newTypes = typeRepo.findAll();
        assertThat(newTypes).hasSize(0);
    }
 
	// Weapon Repository
    @Test
    public void weaponRepository() {
    	
    	// clear the repository
    	weapRepo.deleteAll();
    	
    	// insert new weapon
    	Weapon newWeapon = new Weapon();
    	newWeapon.setWeaponName("new-test-weapon");
    	weapRepo.save(newWeapon);
    	
    	// find new weapon
        List<Weapon> weapons = weapRepo.findByWeaponName("new-test-weapon");
        assertThat(weapons).hasSize(1);
        assertThat(weapons.get(0).getWeaponName()).isEqualTo("new-test-weapon");
        
        // delete weapon
        weapRepo.deleteByWeaponName(weapons.get(0).getWeaponName());
        List<Weapon> newWeapons = weapRepo.findAll();
        assertThat(newWeapons).hasSize(0);
    }
}
