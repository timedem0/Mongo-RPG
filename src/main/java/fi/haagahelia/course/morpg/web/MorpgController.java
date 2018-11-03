package fi.haagahelia.course.morpg.web;

import java.util.List;

import javax.validation.Valid;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fi.haagahelia.course.morpg.logic.Fight;
import fi.haagahelia.course.morpg.domain.SignForm;

import fi.haagahelia.course.morpg.domain.User;
import fi.haagahelia.course.morpg.domain.UserRepository;
import fi.haagahelia.course.morpg.domain.UserRepositoryCustom;
import fi.haagahelia.course.morpg.domain.Character;
import fi.haagahelia.course.morpg.domain.Monster;
import fi.haagahelia.course.morpg.domain.Location;
import fi.haagahelia.course.morpg.domain.LocationRepository;
import fi.haagahelia.course.morpg.domain.LocationRepositoryCustom;
import fi.haagahelia.course.morpg.domain.Type;
import fi.haagahelia.course.morpg.domain.TypeRepository;

@Controller
public class MorpgController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserRepositoryCustom userRepoCustom;
	
	@Autowired
	private TypeRepository typeRepo;
	
	@Autowired
	private LocationRepository locoRepo;
	
	@Autowired
	private LocationRepositoryCustom locoRepoCustom;
	
	// security and user creation
	
    @RequestMapping(value = {"/index", "/", "/login"})
    public String login() {	
        return "login";
    }
    
    @RequestMapping(value = "/signup")
    public String addUser(Model model){
    	model.addAttribute("signform", new SignForm());
        return "signup";
    }
    
    @RequestMapping(value = "/success")
    public String loginsuccess() {
        return "forward:/login?success";
    }
    
    @RequestMapping(value = "/saveuser", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("signform") SignForm signForm, BindingResult bindingResult) {
    	if (!bindingResult.hasErrors()) { // validation errors
    		if (signForm.getPassword().equals(signForm.getPasswordCheck())) { // check password match		
	    		String pwd = signForm.getPassword();
		    	BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		    	String hashPwd = bc.encode(pwd); // hash the password
	
		    	User newUser = new User();
		    	newUser.setPassword(hashPwd);
		    	newUser.setName(signForm.getName());
		    	newUser.setRole("USER");
		    	
		    	if (userRepo.findByName(signForm.getName()) == null) { // check if user exists
		    		userRepo.save(newUser);
		    	} else {
	    			bindingResult.rejectValue("name", "err.name", "Username already exists");    	
	    			return "signup";		    		
		    	}
    		} else {
    			bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords does not match");    	
    			return "signup";
    		}
    	} else {
    		return "signup";
    	}
    	return "redirect:/success";    	
    }    
    
    // MAIN pages
    
    // character list
	@RequestMapping(value = "/main")
    public String charList(Model model) {
		
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("types", typeRepo.findAll());
        model.addAttribute("locations", locoRepo.findAll());
        model.addAttribute("fight", new Fight());
        
        return "main";
    }
	
	// character creation page
    @RequestMapping(value = "/create/{userName}")
    public String addCharacter(@PathVariable("userName") String userName, Model model) {
    	
    	model.addAttribute("newChar", new Character());
    	model.addAttribute("userName", userName);
    	model.addAttribute("types", typeRepo.findAll());

        return "charactercreate";
    }
    
    // character creation function
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveCharacter(String userName, Character charToCreate, RedirectAttributes ra) {
	  	    	
    	if (userRepoCustom.findCharByName(userName, charToCreate.getCharName()) == null) { // check if character name exists
    		userRepoCustom.insertChar(userName, charToCreate);
    	} else {
	        ra.addFlashAttribute("errorMessage", "Character already exists");
			return "redirect:create/" + userName;		    		
    	}
	    	
    	return "redirect:main";
    }
	 
    // character edit page
    @RequestMapping(value = "/edit/{userName}/{charName}", method = RequestMethod.GET)
    public String editCharacter(@PathVariable("userName") String userName, @PathVariable("charName") String charName, Model model) {
    	
    	Character charToEdit = userRepoCustom.findCharByName(userName, charName);
    	model.addAttribute("charToEdit", charToEdit);
    	model.addAttribute("userName", userName);
    	model.addAttribute("types", typeRepo.findAll());
    	   
    	return "characteredit";
    }
    
    // character edit function
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateCharacter(String userName, Character charToEdit) {
    	
    	userRepoCustom.updateChar(userName, charToEdit);
    	
    	return "redirect:main";
    }
    
	// character delete function
    @RequestMapping(value = "/delete/{userName}/{charName}", method = RequestMethod.GET)
    public String deleteCharacter(@PathVariable("userName") String userName, @PathVariable("charName") String charName) {
    	
    	userRepoCustom.deleteChar(userName, charName);
    	
        return "redirect:../../main";
    }
    
    // fight function
    @RequestMapping(value = "/fight", method = RequestMethod.POST)
    public String fight(String userName, String charName, String locationName, RedirectAttributes ra) {
    	
    	Fight fight = new Fight();
    	int dice = ThreadLocalRandom.current().nextInt(1, 7);
    	Character charToFight = userRepoCustom.findCharByName(userName, charName);
    	List<Type> allTypes = typeRepo.findAll();
    	
    	List<Monster> allMonsters = locoRepoCustom.findMonsterByLocation(locationName);
    	int monsterNumber = allMonsters.size();
    	int randomMonster = ThreadLocalRandom.current().nextInt(0, monsterNumber);
    	Monster monsterToFight = allMonsters.get(randomMonster);
    	
    	int fightResult = fight.getFightResult(charToFight, monsterToFight, allTypes, dice);
    	    	    	
    	ra.addFlashAttribute("result", fightResult);

    	return "redirect:fightresult";
    }
    
    // fight results
    @RequestMapping(value = "/fightresult")
    public String fightResults() {
    	
    	return "fightresult";
    }
    
    // ADMIN pages
    
    // main admin page
	@RequestMapping(value = "/admin")
    public String adminPage(Model model) {
		
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("types", typeRepo.findAll());
        model.addAttribute("locations", locoRepo.findAll());
        
        return "admin";
    }
	
	// user delete function
    @RequestMapping(value = "/deleteuser/{userName}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("userName") String userName) {
    	
    	userRepo.deleteByUserName(userName);
    	
        return "redirect:../admin";
    }
    
	// location creation page
    @RequestMapping(value = "/newlocation")
    public String addLocation(Model model) {
    	
    	model.addAttribute("newLocation", new Location());

        return "locationcreate";
    }
    
    // location creation function
    @RequestMapping(value = "/savelocation", method = RequestMethod.POST)
    public String saveLocation(Location locationToCreate, RedirectAttributes ra) {
	  	    	
    	if (locoRepo.findByName(locationToCreate.getName()) == null) { // check if location name exists
    		locoRepo.insert(locationToCreate);
    	} else {
	        ra.addFlashAttribute("errorMessage", "Location already exists");
			return "redirect:newlocation";		    		
    	}

    	return "redirect:admin";
    }
    
    // location edit page
    @RequestMapping(value = "/editlocation/{locationName}", method = RequestMethod.GET)
    public String editLocation(@PathVariable("locationName") String locationName, Model model) {
    	
    	Location locationToEdit = locoRepo.findByName(locationName);
    	model.addAttribute("locationToEdit", locationToEdit);
    	   
    	return "locationedit";
    }
    
    // location edit function
    @RequestMapping(value = "/updatelocation", method = RequestMethod.POST)
    public String updateLocation(Location locationToEdit) {
    	
    	locoRepoCustom.updateLocation(locationToEdit);
    	
    	return "redirect:admin";
    }
    
	// location delete function
    @RequestMapping(value = "/deletelocation/{locationName}", method = RequestMethod.GET)
    public String deleteLocation(@PathVariable("locationName") String locationName) {
    	
    	locoRepo.deleteByLocationName(locationName);
    	
        return "redirect:../admin";
    }
    
	// monster creation page
    @RequestMapping(value = "/newmonster/{locationName}")
    public String addMonster(@PathVariable("locationName") String locationName, Model model) {
    	
    	model.addAttribute("newMonster", new Monster());
    	model.addAttribute("locationName", locationName);

        return "monstercreate";
    }
    
    // monster creation function
    @RequestMapping(value = "/savemonster", method = RequestMethod.POST)
    public String saveMonster(String locationName, Monster monsterToCreate, RedirectAttributes ra) {
    	
		List<Monster> monstersAtLocation = locoRepoCustom.findMonsterByLocation(locationName);
		int count = 0;
		
		for (Monster m : monstersAtLocation) {
			if (m.getMonsterName().equals(monsterToCreate.getMonsterName())) {
				count++;
			}
		}
		
		if (count == 0) {
			locoRepoCustom.insertMonster(locationName, monsterToCreate);
		} else {
	        ra.addFlashAttribute("errorMessage", "Monster already exists");
			return "redirect:newmonster/" + locationName;      			
		}
    		
    	return "redirect:admin";
    }
    
    // monster edit page
    @RequestMapping(value = "/editmonster/{locationName}/{monsterName}", method = RequestMethod.GET)
    public String editMonster(@PathVariable("locationName") String locationName, @PathVariable("monsterName") String monsterName, Model model) {
    	
    	List<Monster> monstersAtLocation = locoRepoCustom.findMonsterByLocation(locationName);  	
    	Monster monsterToEdit = new Monster();
    	
		for (Monster m : monstersAtLocation) {
			if (m.getMonsterName().equals(monsterName)) {
				monsterToEdit = m;
			}
		}    	
    	
    	model.addAttribute("monsterToEdit", monsterToEdit);
    	model.addAttribute("locationName", locationName);
    	   
    	return "monsteredit";
    }
    
    // monster edit function
    @RequestMapping(value = "/updatemonster", method = RequestMethod.POST)
    public String updateMonster(String locationName, Monster monsterToEdit, RedirectAttributes ra) {
    	
		locoRepoCustom.updateMonster(locationName, monsterToEdit);
    	
    	return "redirect:admin";
    }
    
	// monster delete function
    @RequestMapping(value = "/deletemonster/{locationName}/{monsterName}", method = RequestMethod.GET)
    public String deleteMonster(@PathVariable("locationName") String locationName, @PathVariable("monsterName") String monsterName) {
    	
    	locoRepoCustom.deleteMonster(locationName, monsterName);
    	
        return "redirect:../../admin";
    }
    
    // RESTful services
    
    @RequestMapping(value="/users", method = RequestMethod.GET)
    public @ResponseBody List<User> getAllUsers () {
    	return (List<User>) userRepo.findAll();
    }
}

// Testing purposes
// System.out.println("==========New Test:==========");
// System.out.println("user name = " + userName + ", char name = " + charName);
// User test = userRepo.findByName(userName);
// User user = userRepo.findByName(userName);
// System.out.println(userName);
// System.out.println(charToEdit);
// System.out.println("==========New Test:==========");
// users.forEach(System.out::println);
// System.out.println(userName + ' ' + charName);
