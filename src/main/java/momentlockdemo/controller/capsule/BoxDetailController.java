package momentlockdemo.controller.capsule;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import momentlockdemo.entity.Box;
import momentlockdemo.entity.Capsule;
import momentlockdemo.service.BoxService;
import momentlockdemo.service.CapsuleService;

@Controller("boxDetailController")
@RequestMapping("/momentlock")
@RequiredArgsConstructor
public class BoxDetailController {
	
    private final BoxService boxService;
    private final CapsuleService capsuleService;

    // 박스 상세 + 캡슐 리스트
    @GetMapping("/boxdetail")
    public String boxdetailPage(@RequestParam("boxid") Long boxid, Model model) {

        Box box = boxService.getBoxById(boxid)
                .orElseThrow(() -> new IllegalArgumentException("해당 박스를 찾을 수 없습니다. ID=" + boxid));

        List<Capsule> capsules = capsuleService.getCapsulesByBox(box);

        model.addAttribute("box", box);
        model.addAttribute("capsules", capsules);

        return "html/box/boxdetail";
    }
}
