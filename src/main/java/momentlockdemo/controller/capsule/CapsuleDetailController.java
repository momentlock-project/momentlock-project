package momentlockdemo.controller.capsule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import momentlockdemo.entity.Afile;
import momentlockdemo.entity.Capsule;
import momentlockdemo.service.AfileService;
import momentlockdemo.service.CapsuleService;

@Controller("capsuleDetailController")
@RequestMapping("/momentlock")
public class CapsuleDetailController {

	@Autowired
	CapsuleService capsuleService;

	@Autowired
	AfileService afileService;

	// 오픈 캡슐 클릭 시 상세
	@GetMapping("/opencapsuledetail/{capid}")
	public String opencapsuledetailPage(@PathVariable("capid") Long capid, Model model) 
			throws JsonProcessingException {

		Capsule capsule = capsuleService.getCapsuleById(capid).get();
		List<Afile> fileList = afileService.getAfilesByCapsule(capsule);
		
	    model.addAttribute("afiles", fileList);
		model.addAttribute("capsule", capsule);
		
		return "html/capsule/opencapsuledetail";
	}
	
	// 나의 캡슐 리스트에서 캡슐 클릭 시 상세
	@GetMapping("/mycapsuledetail")
	public String mycapsuledetailPage() {
		return "html/capsule/mycapsuledetail";
	}

	@ResponseBody
	@GetMapping("/capsulelikeaction")
	public Integer dbCapLikeCountIncrease(@RequestParam(name = "capid") Long capid,
			@RequestParam(name = "action") String action) {
		Integer count = capsuleService.capsuleLikeCountUpdate(capid, action);
		return count;
	}

}
