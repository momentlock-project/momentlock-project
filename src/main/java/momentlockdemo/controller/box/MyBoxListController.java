package momentlockdemo.controller.box;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import momentlockdemo.entity.Box;
import momentlockdemo.entity.Member;
import momentlockdemo.entity.MemberBox;
import momentlockdemo.service.BoxService;
import momentlockdemo.service.MemberBoxService;
import momentlockdemo.service.MemberService;

@Controller("myBoxListController")
@RequestMapping("/momentlock")
public class MyBoxListController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private BoxService boxService;

    @Autowired
    private MemberBoxService memberBoxService;

    // 나의 상자 리스트
    @GetMapping("/myboxlist")
    public String myboxlistPage(Model model) {

        // 1. 임시 회원 생성 (DB에 없으면 저장)
        String username = "gmldnjs1616@gmail.com";
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

        // 2. 해당 회원이 만든 상자 리스트 조회
        List<MemberBox> memberBoxes = memberBoxService.getBoxesByMember(memberService.getMemberByNickname("길동이").get());
        List<Box> myBoxes = memberBoxes.stream()
                .map(MemberBox::getBox)
                .collect(Collectors.toList());

        // 3. 모델에 담아서 Thymeleaf로 전달
        model.addAttribute("myBoxes", myBoxes);

        return "html/box/myboxlist";
    }
    
}
