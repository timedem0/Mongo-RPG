package fi.haagahelia.course.morpg.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.haagahelia.course.morpg.domain.UserRepository;
import fi.haagahelia.course.morpg.domain.CharacterRepository;
import fi.haagahelia.course.morpg.domain.SignForm;
import fi.haagahelia.course.morpg.domain.User;

@Controller
public class MorpgController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CharacterRepository charRepo;
	
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
		    	newUser.setRole("user");
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
        model.addAttribute("characters", charRepo.findAll());
        return "main";
    }
    
    
    // RESTful services
    
    @RequestMapping(value="/users", method = RequestMethod.GET)
    public @ResponseBody List<User> getAllUsers () {
    	return (List<User>) userRepo.findAll();
    }

}
