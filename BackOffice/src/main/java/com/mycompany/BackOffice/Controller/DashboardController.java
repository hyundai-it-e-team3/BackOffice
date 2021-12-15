package com.mycompany.BackOffice.Controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.BackOffice.service.DashboardService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/dashboard")
public class DashboardController {
	@Resource
	DashboardService dashboardService;
	
	@RequestMapping("/main")
	public String dashboardMain(Model model) {
		model.addAttribute("quater", dashboardService.getQurterSales());
		model.addAttribute("daily", dashboardService.getDailySales());
		model.addAttribute("brand", dashboardService.getBrandSales());
		model.addAttribute("member", dashboardService.getMemberInfo());
		return "dashboard/main";
	}

}
