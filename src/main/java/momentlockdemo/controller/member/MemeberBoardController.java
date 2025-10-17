package momentlockdemo.controller.member;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import momentlockdemo.service.DeclarationService;
import momentlockdemo.service.InquiryService;
import momentlockdemo.service.NoticeQaService;

@Controller("MemberBoardController")
@RequestMapping("/momentlock")
public class MemeberBoardController {
	
	@Autowired
	private InquiryService inquiryService;
	
	@Autowired
	private DeclarationService declarationService;
	
	@Autowired
	private NoticeQaService noticeQaService;
	
	// 문의게시판
	@GetMapping("/memberinquirylist")
	public String memberinquirylistPage(Model model,
			@PageableDefault(page = 0, size = 10, sort = "inqid", direction = Sort.Direction.DESC)Pageable pageable) {
		model.addAttribute("inquirylist", inquiryService.getAllInquiries(pageable));
		return "html/member/memberinquirylist";
	}
	
	// 문의게시판 상세
	// map 생성해서 front에서 json으로 받음
	@GetMapping("/memberinquirylist/{inqid}")
	public ResponseEntity<Map<String, Object>> memberinquirylistPage(@PathVariable Long inqid) {
		return inquiryService.getInquiryById(inqid)
				.map(inquiry -> {
					Map<String, Object> data = new HashMap<String, Object>();
					data.put("inqtitle", inquiry.getInqtitle());
					data.put("inqcontent", inquiry.getInqcontent());
					data.put("inqregdate", inquiry.getInqregdate());				
					data.put("inqcomplete", inquiry.getInqcomplete());				
					return ResponseEntity.ok(data);
				}).orElse(ResponseEntity.notFound().build());
	}
	
	// 신고게시판
	@GetMapping("/memberdeclarlist")
	public String memberdeclarlistPage(Model model,
			@PageableDefault(page = 0, size = 10, sort = "decid", direction = Sort.Direction.DESC)Pageable pageable) {
		model.addAttribute("declarlist", declarationService.getAllDeclarations(pageable));
		return "html/member/memberdeclarlist";
	}
	
	// 신고게시판 상세
	// map 생성해서 front에서 json으로 받음
	@GetMapping("/memberdeclarlist/{decid}")
	public ResponseEntity<Map<String, Object>> memberdeclarlistPage(@PathVariable Long decid) {
		return declarationService.getDeclarationById(decid)
				.map(dec -> {
					Map<String, Object> data = new HashMap<String, Object>();
					data.put("deccategory", dec.getDeccategory());
					data.put("deccontent", dec.getDeccontent());
					return ResponseEntity.ok(data);
				}).orElse(ResponseEntity.notFound().build());
	}
	
	// 공지사항
	@GetMapping("/membernoticelist")
	public String membernoticelistPage(
			Model model,
			@RequestParam(required = false) String type,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
		if(type == null || type.isBlank()) {
			model.addAttribute("noticelist", noticeQaService.getAllNoticeQa(pageable));
		} else if (type != null) {
			model.addAttribute("noticelist", noticeQaService.getPageNoticeQaByType(type, pageable));
			model.addAttribute("type", type);
		}
		return "html/member/membernoticelist";
	}	
	
	// 공지사항, QnA 상세
	// map 생성해서 front에서 json으로 받음
	@GetMapping("/membernoticelist/{id}")
	public ResponseEntity<Map<String, Object>> membernoticelistPage(@PathVariable Long id) {
		return noticeQaService.getNoticeQaById(id)
				.map(notice -> {
					Map<String, Object> data = new HashMap<String, Object>();
					data.put("title", notice.getTitle());
					data.put("content", notice.getContent());
					data.put("type", notice.getType());
					return ResponseEntity.ok(data);
				}).orElse(ResponseEntity.notFound().build());
	}
	

}
