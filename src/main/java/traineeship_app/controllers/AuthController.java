package traineeship_app.controllers;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import traineeship_app.domainmodel.Company;
import traineeship_app.domainmodel.Professor;
import traineeship_app.domainmodel.Role;
import traineeship_app.domainmodel.Student;
import traineeship_app.domainmodel.User;
import traineeship_app.services.StudentService;
import traineeship_app.services.UserService;

@Controller
public class AuthController {
    @Autowired
    UserService userService;
    
	@Autowired
	private PasswordEncoder passwordEncoder;

    @RequestMapping("auth/login")
    public String login(){
        return "auth/login";
    }

    @RequestMapping("auth/signup")
    public String signup(Model model){
        model.addAttribute("user", new User());
        return "auth/signup";
    }

    @PostMapping("auth/save")
    public String signupUser(@ModelAttribute("user") User user,RedirectAttributes redirectAttributes){
       
        if(userService.isUserPresent(user.getUsername())){
        	 redirectAttributes.addFlashAttribute("errorMessage", "User already registered!");
            return "redirect:/auth/signup";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("successMessage", "User registered successfully!"); 
        
        return "redirect:/auth/login";
        
    }

}

