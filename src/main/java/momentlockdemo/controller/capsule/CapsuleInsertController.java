package momentlockdemo.controller.capsule;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import momentlockdemo.entity.Afile;
import momentlockdemo.entity.Capsule;
import momentlockdemo.service.AfileService;
import momentlockdemo.service.CapsuleService;

@Controller("capsuleInsertController")
@RequestMapping("/momentlock")
public class CapsuleInsertController {
	
	@Autowired
	CapsuleService capService;
	
	@Autowired
	AfileService afileService;
	
	// 캡슐 작성 폼
	@GetMapping("/capsuleinsert")
	public String capsuleinsertPage() {
		return "html/capsule/capsuleinsert";
	}
	
//	파일 insert test
	@GetMapping("/afileinserttest/{capid}")
	public String capinsert(@PathVariable(name = "capid") Long capid) {
		
		Capsule cap = capService.getCapsuleById(capid).get();
		
		afileService.createAfile(Afile.builder()
				.afid(null)
				.afsname("/img/cute.jpg")
				.afcname("cute.jpg")
				.afcontenttype("image/jpg")
				.afregdate(LocalDateTime.now())
				.afdelyn("N")
				.capsule(cap)
				.build()
				);
		
		
		
		return "/momentlock/";
	}

}
