package com.ecommerce.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ecommerce.MobileBack.dao.UserDao;

//import com.ecommerce.MobileBack.dao.UserDao;
//import com.ecommerce.MobileBack.model.User;


@Controller
public class HomeController {

	@Autowired
	UserDao userdao;

	String message = "Welcome to Spring MVC!";

	@RequestMapping("/")
	public String Welcome() {

		return "/index";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView printWelcome(Principal principal, HttpServletRequest request,Model model) {
		ModelAndView mv;
		String username = principal.getName();
		System.out.println(username);
		if (request.isUserInRole("ROLE_ADMIN")) {
			System.out.println(request.isUserInRole("ROLE_ADMIN"));
			
			mv = new ModelAndView("adminHome");
			mv.addObject("username", username);
			return mv;
		} else {
			System.out.println(request.isUserInRole("ROLE_USER"));
			System.out.println("user page");
			mv = new ModelAndView("userHome");
			mv.addObject("username", username);
			return mv;
		}
	}

	@RequestMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model) {
		if (error != null) {
			model.addAttribute("error", "Invalid username and password!");
		}
		if (logout != null) {
			model.addAttribute("msg", "You have been logged out successfully.");
		}
		return "login";
	}

	@RequestMapping("/register")
	public ModelAndView showregister()

	{

		ModelAndView mv = new ModelAndView("register");
//		mv.addObject("registration", new User());

		return mv;
	}

	/*@RequestMapping("/userSubmit")
	public ModelAndView submit(@ModelAttribute("registration") User user)

	{
		user.setEnabled(true);
		user.setRole("ROLE_USER");

		userdao.save(user);

		ModelAndView mv = new ModelAndView("login");
		mv.addObject("message", "Thank you for registering now please login");
		return mv;
	}*/

	@RequestMapping("/about")
	public ModelAndView showabout()

	{

		ModelAndView mv = new ModelAndView("about");

		return mv;
	}

	@RequestMapping("/contact")
	public ModelAndView showcontact()

	{

		ModelAndView mv = new ModelAndView("contact");

		return mv;
	}

}
