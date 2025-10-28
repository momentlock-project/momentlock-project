package momentlockdemo.controller.member;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import momentlockdemo.dto.IdFindDTO;
import momentlockdemo.entity.Member;
import momentlockdemo.service.IdFindService;

@Controller("memberFindIdController")
@RequestMapping("/momentlock")
public class MembeIdFindController {

	@Autowired
	private IdFindService idFindService;
	
	@GetMapping("/member/idFind")
	public String idFindP() {
		
		return "html/member/idFind";
		
	};
	
	@PostMapping("/idFindProc")
	public String idFindProcess(Model model, IdFindDTO idFindDTO) {
		
		Optional<Member> member = idFindService.idFindProcess(idFindDTO);
		if (member.isPresent() != false) {
			
			model.addAttribute("username", member.get().getUsername());
			
			return "html/member/idFindResult";
			
		}else {
			
			System.out.println("null member. idFindProcess() return with no member");
			
			return "html/member/idFind";
			
		}
		
	};
	
}