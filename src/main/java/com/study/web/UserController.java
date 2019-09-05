package com.study.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.domain.UserRepository;
import com.study.model.User;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	
	@PostMapping("login")
	public String login(User user, HttpSession session) {

		User savedUser = userRepository.findByUserId(user.getUserId());
		//System.out.println(savedUser);
		if (user.getUserId().isEmpty()) {
			return "redirect:/users/login";
		}

		if (!user.matchPassword(savedUser)) {
			return "redirect:/users/login";
		}
		session.setAttribute("user", savedUser);
		
		return "redirect:/";
	}

	@GetMapping("/login")
	public String login(Model model) {
		return "/user/login";
	}

	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		session.removeAttribute("supplier");
		return "redirect:/";
	}
	
	
	@PostMapping("")
	public String create(User user) {
		if (user != null)
			userRepository.save(user);
		return "redirect:/users";
	}

	@GetMapping("/register")
	public String form(Model model) {
		return "/user/form";
	}

	@GetMapping("")
	public String list(Model model) {
		List<User> users = userRepository.findAll();
		model.addAttribute("users", users);
		return "/user/list";
	}

	@GetMapping("{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {

		if(!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/login";
		}
		
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		if(!sessionUser.matchId(id)) {
			//throw new IllegalStateException("You can't update the another user.");
			return "redirect:/users";
		}
		
		User user = userRepository.findById(id).orElse(new User());
		model.addAttribute("user", user);

		return "/user/form";
	}
}
