package momentlockdemo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

	@ModelAttribute
	public void addUserToModel(Model model) {
		
		model.addAttribute(
				"user",
				SecurityContextHolder.getContext().getAuthentication().getName()
				);
		
	};
	
}