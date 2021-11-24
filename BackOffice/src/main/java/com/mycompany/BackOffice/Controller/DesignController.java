package com.mycompany.BackOffice.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignController {
	
	@RequestMapping("/layoutSetting")
	public String layoutSetting() {
		return "/design/layoutSetting";
	}
}
