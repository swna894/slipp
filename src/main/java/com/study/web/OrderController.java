package com.study.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {

	@GetMapping("/{supplier}")
	public String updateForm(@PathVariable String supplier, Model model, HttpSession session) {
		
		session.setAttribute("supplier", supplier);
		
		return "/user/form";
	}
}
