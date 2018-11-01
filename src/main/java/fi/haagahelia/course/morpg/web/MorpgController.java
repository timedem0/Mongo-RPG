package fi.haagahelia.course.morpg.web;

import java.util.List;

import javax.validation.Valid;

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

import fi.haagahelia.course.morpg.domain.User;
import fi.haagahelia.course.morpg.domain.UserRepository;
import fi.haagahelia.course.morpg.domain.UserRepositoryCustom;
import fi.haagahelia.course.morpg.domain.Character;
import fi.haagahelia.course.morpg.domain.TypeRepository;
import fi.haagahelia.course.morpg.domain.SignForm;

@Controller
public class MorpgController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserRepositoryCustom userRepoCustom;
	
	@Autowired
	private TypeRepository typeRepo;
	
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
    
    // character save function
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
	
	// character delete
    @RequestMapping(value = "/delete/{userName}/{charName}", method = RequestMethod.GET)
    public String deleteCharacter(@PathVariable("userName") String userName, @PathVariable("charName") String charName, Model model) {
    	
    	userRepoCustom.deleteChar(userName, charName);
    	
        return "redirect:../../main";
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
    
    // character update function
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateCharacter(String userName, Character charToEdit) {
    	
    	userRepoCustom.updateChar(userName, charToEdit);
    	
    	return "redirect:main";
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
