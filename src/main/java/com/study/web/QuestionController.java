package com.study.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.domain.QuestionRepository;
import com.study.model.Question;
import com.study.model.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {

	private static final String ERROR_MESSAGE_LOGIN = "로그인이 필요합니다.";
	@Autowired
	QuestionRepository questionRepository;

	@GetMapping("/form")
	public String form(HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/user/login";
		}

		return "qna/form";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {

		model.addAttribute("question", questionRepository.findById(id).orElse(new Question()));
		return "/qna/show";
	}
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, String title, String contents, Model model, HttpSession session) {
		
		Question question = questionRepository.findById(id).orElse(new Question());
		if (!hasPermission(session, question)) {
			model.addAttribute("error", ERROR_MESSAGE_LOGIN);
			return "/user/login";
		}		
		question.update(title, contents);
		questionRepository.save(question);
		
		return String.format("redirect:/questions/%d", id);
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		Question question = questionRepository.findById(id).orElse(new Question());
		if (!hasPermission(session, question)) {
			model.addAttribute("error", ERROR_MESSAGE_LOGIN);
			return "/user/login";
		}	
	
		model.addAttribute("question", question);
		return "/qna/updateForm";
	}

	@PostMapping("")
	public String cerate(String title, String contents, Model model, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			model.addAttribute("error", ERROR_MESSAGE_LOGIN);
			return "/user/login";
		}

		User sessionUser = HttpSessionUtils.getUserFromSession(session);

		Question newQuestion = new Question(sessionUser, title, contents);
		questionRepository.save(newQuestion);

		return "redirect:/";
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id, Model model, HttpSession session) {
		Question question = questionRepository.findById(id).orElse(new Question());
		if (!hasPermission(session, question)) {
			model.addAttribute("error", ERROR_MESSAGE_LOGIN);
			return "/user/login";
		}	
		questionRepository.deleteById(id);
		return "redirect:/";
	}
	
	private boolean hasPermission(HttpSession session, Question question) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return false;
		}		
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if(!question.isSameWriter(loginUser)) {
			return false;
		}	
		return true;
	}
}
