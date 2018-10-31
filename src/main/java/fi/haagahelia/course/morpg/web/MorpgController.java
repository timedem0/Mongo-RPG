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

import fi.haagahelia.course.morpg.domain.User;
import fi.haagahelia.course.morpg.domain.UserRepository;
import fi.haagahelia.course.morpg.domain.UserRepositoryCustom;
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
	
	// Security and User Creation
	
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
		    	}
		    	else {
	    			bindingResult.rejectValue("name", "err.name", "Username already exists");    	
	    			return "signup";		    		
		    	}
    		}
    		else {
    			bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords does not match");    	
    			return "signup";
    		}
    	}
    	else {
    		return "signup";
    	}
    	return "redirect:/success";    	
    }    
    
    // Main pages
    
	@RequestMapping(value = "/main")
    public String charList(Model model) {	
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("types", typeRepo.findAll());
        return "main";
    }
     
    @RequestMapping(value = "/edit/{userName}/{charName}", method = RequestMethod.GET)
    public String editCharacter(@PathVariable("userName") String userName, @PathVariable("charName") String charName, Model model) {

    	model.addAttribute("user", userRepoCustom.getEdit(userName, charName));
    	model.addAttribute("types", typeRepo.findAll());
    	
    	System.out.println("==========New Test:==========");
    	System.out.println("user name = " + userName + ", char name = " + charName);
    	// User test = userRepo.findByName(userName);
    	// List<User> users = null;
		// users = userRepoCustom.getEdit(userName, charName);
		// System.out.println("==========New Test:==========");
		// users.forEach(System.out::println);
    	// System.out.println(userName + ' ' + charName);
    	
    	return "characteredit";
    }
        
    @RequestMapping(value = "/delete/{userName}/{charName}", method = RequestMethod.GET)
    public String deleteCharacter(@PathVariable("userName") String userName, @PathVariable("charName") String charName, Model model) {
    	
    	userRepoCustom.deleteChar(userName, charName);
        return "redirect:../../main";
    }
    
    /* WORK IN PROGRESS PAST THIS POINT
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateCharacter(User user) {
    	return "redirect:../../main";
    }
    
    @RequestMapping(value = "/create/{userName}/{charName}")
    public String addCharacter(@PathVariable("userName") String userName, @PathVariable("charName") String charName, Model model) {
    	
    	// model.addAttribute("char", new Character());
    	// model.addAttribute("types", typeRepo.findAll());
    	System.out.println("test");
        return "charactercreate";
    }
    */
    
    // RESTful services
    
    @RequestMapping(value="/users", method = RequestMethod.GET)
    public @ResponseBody List<User> getAllUsers () {
    	return (List<User>) userRepo.findAll();
    }

}
