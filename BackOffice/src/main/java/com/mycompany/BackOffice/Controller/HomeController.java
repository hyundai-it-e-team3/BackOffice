package com.mycompany.BackOffice.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class HomeController {
	
	@RequestMapping("/")
	public String home() {
		log.info("Run home");
		return "home";
	}
	
	@RequestMapping("/loginFail")
	public String loginFail() {
		log.info("Run LoginFail");
		return "loginFail";
	}
}