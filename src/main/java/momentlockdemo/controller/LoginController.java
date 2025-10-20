package momentlockdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/html/login")
	public String loginP() {
		
		return "html/login";
		
	};

}