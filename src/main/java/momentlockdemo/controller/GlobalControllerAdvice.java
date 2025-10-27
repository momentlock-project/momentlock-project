package momentlockdemo.controller;

<<<<<<< HEAD
=======
import org.springframework.security.core.context.SecurityContextHolder;
>>>>>>> 8db9c49 (add social login)
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

	@ModelAttribute
<<<<<<< HEAD
	public void addUserToModel(Model model, @AuthenticationPrincipal User user) {

		model.addAttribute("user", user);
	}
=======
	public void addUserToModel(Model model) {
		
		model.addAttribute(
				"user",
				SecurityContextHolder.getContext().getAuthentication().getName()
				);
		
	}
	
>>>>>>> 8db9c49 (add social login)
}
