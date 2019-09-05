package com.study.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.study.domain.QuestionRepository;

@Controller
public class WelconeController {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	
	@GetMapping("/")
	public String welcome(Model model, HttpSession session) {
		
		Map<String, String> mapSupplier = new HashMap<String, String>();  
		mapSupplier.put("FSGL", "Feel so Good Ltd");
		mapSupplier.put("DOUB", "Double Grand");
		mapSupplier.put("FERF", "Performance Products Ltd");
		mapSupplier.put("JSLE", "From USA");
		mapSupplier.put("ADRT", "ADR Trading Ltd");
      
		session.setAttribute("mapSupplier", mapSupplier);
		session.removeAttribute("supplier");
		
        model.addAttribute("questions", questionRepository.findAll());   
		return "index"; 
	}
}
