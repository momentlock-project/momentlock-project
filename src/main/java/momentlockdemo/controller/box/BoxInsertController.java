package momentlockdemo.controller.box;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import momentlockdemo.dto.BoxCreateDto;
import momentlockdemo.entity.Box;
import momentlockdemo.entity.Member;
import momentlockdemo.service.BoxService;
import momentlockdemo.service.MemberBoxService;
import momentlockdemo.service.MemberService;

@Controller("boxInsertController")
@RequestMapping("/momentlock")
public class BoxInsertController {

    @Autowired
    private MemberBoxService memberBoxService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private BoxService boxService;

    // 상자 추가/수정 폼
    @GetMapping("/boxinsert")
    public String boxinsertPage(
            @RequestParam(required = false, name = "boxid") Long boxid, 
            Model model) {
        
        if (boxid != null) {
            model.addAttribute("box", boxService.getBoxById(boxid).orElse(null));
        }
        
        return "html/box/boxinsert";
    }

    // 상자 추가
    @PostMapping("/boxadd")
    public String addBox(
            @ModelAttribute BoxCreateDto dto, HttpSession session) {
        
    	String username = session.getAttribute("username").toString();
        String boxreleasecode = dto.getIsPublic() != null && dto.getIsPublic() ? "B00" : "B01";
        
        Box box = Box.builder()
                .boxname(dto.getBoxName())
                .boxlocation(dto.getBoxlocation())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .boxopendate(dto.getOpenDateTime())
                .boxreleasecode(boxreleasecode)
                .boxmemcount(dto.getMemberCount())
                .build();
        
        Member member = memberService.getMemberByUsername(username).get();
        memberBoxService.createBoxWithMember(box, member);
        
        return "redirect:/momentlock/myboxlist";
    }


    // 상자 수정
    @PostMapping("/boxupdate")
    public String updateBox(
            @ModelAttribute BoxCreateDto dto) {
        
        Box box = boxService.getBoxById(dto.getBoxid())
                .orElseThrow(() -> new RuntimeException("Box not found"));
        
        // 데이터 업데이트
        box.setBoxname(dto.getBoxName());
        box.setBoxlocation(dto.getBoxlocation());
        box.setLatitude(dto.getLatitude());
        box.setLongitude(dto.getLongitude());
        box.setBoxopendate(dto.getOpenDateTime());
        box.setBoxreleasecode(dto.getIsPublic() != null && dto.getIsPublic() ? "B00" : "B01");
        box.setBoxmemcount(dto.getMemberCount());
        
        boxService.updateBox(box);
        
        return "redirect:/momentlock/myboxlist";
    }
}