package com.rr.springboot.scheduleinterceptor.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

	@Value("${config.schedule.begin}")
	private int begin;
	@Value("${config.schedule.end}")
	private int end;

	@GetMapping({ "/", "/index" })
	public String index(Model model) {
		model.addAttribute("title", "Welcome to the customer schedule");
		return "index";
	}

	@GetMapping("/closed")
	public String closed(Model model) {
		StringBuilder closedMessage = new StringBuilder(
				"Closed, please visit us from " + begin + " am to " + end + " pm.");
		model.addAttribute("title", "Out of business hours");
		model.addAttribute("closedMessage", closedMessage);

		return "closed";
	}

}
