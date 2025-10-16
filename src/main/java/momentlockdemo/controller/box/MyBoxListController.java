package momentlockdemo.controller.box;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import momentlockdemo.entity.Box;
import momentlockdemo.entity.Member;
import momentlockdemo.service.BoxService;
import momentlockdemo.service.MemberService;
import momentlockdemo.service.MemberBoxService;

@Controller("myBoxListController")
@RequestMapping("/momentlock")
public class MyBoxListController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private BoxService boxService;

    @Autowired
    private MemberBoxService memberBoxService;

    // 나의 상자 리스트 (페이지네이션 적용)
    @GetMapping("/myboxlist")
    public String myboxlistPage(
            Model model,
            @PageableDefault(page = 0, size = 9, sort = "boxid", direction = Sort.Direction.DESC) Pageable pageable) {

        // 1. 임시 회원 생성 (DB에 없으면 저장)
        String username = "leesunsin555@bububu.com";
        Optional<Member> optionalMember = memberService.getMemberByUsername(username);
        Member member;
        if (optionalMember.isPresent()) {
            member = optionalMember.get();
        } else {
            member = Member.builder()
                    .username(username)
                    .name("홍길동")
                    .password("1234")
                    .nickname("희원굿")
                    .phonenumber("01044487754")
                    .memcapcount(10L)
                    .build();
            memberService.createMember(member);
        }

        // 2. Page<Box>로 상자 리스트 조회 (페이지네이션 적용)
        Page<Box> myBoxes = boxService.getPagedBoxList(pageable.getPageNumber(), pageable.getPageSize());

        // 3. 모델에 담아서 Thymeleaf로 전달
        model.addAttribute("myBoxes", myBoxes);

        return "html/box/myboxlist";
    }

    // 상자 삭제 및 나가기
    @GetMapping("/boxdelete/{boxid}")
    public String deleteBox(@PathVariable Long boxid) {
        // 현재 로그인한 회원 지정
        String currentUsername = "hong@hong.com"; 
        Optional<Member> memberOpt = memberService.getMemberByUsername(currentUsername);
        if (memberOpt.isEmpty()) return "redirect:/momentlock/myboxlist";

        Member member = memberOpt.get();

        // 1. Box 객체 가져오기
        Optional<Box> boxOpt = boxService.getBoxById(boxid);
        if (boxOpt.isEmpty()) return "redirect:/momentlock/myboxlist";
        Box box = boxOpt.get();

        // 2. MemberBox 조회
        memberBoxService.getMemberBox(member, box).ifPresent(memberBox -> {
            if ("MCB".equals(memberBox.getBoxmatercode())) {
                // 상자 주인 → Box 삭제
                boxService.deleteBox(boxid);
            } else {
                // 초대받은 사용자 → MemberBoxId로 삭제 (상자 나가기)
                memberBoxService.deleteMemberBox(memberBox.getId());
            }
        });

        // 삭제 후 리스트 페이지로 이동
        return "redirect:/momentlock/myboxlist";
    }
}
