package com.mycompany.BackOffice.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/dashboard")
public class DashboardController {
	@RequestMapping("/main")
	public String manager() {
		return "dashboard/main";
	}

}
