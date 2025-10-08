package momentlockdemo.controller.box;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import momentlockdemo.dto.BoxLikeCountDto;
import momentlockdemo.entity.Box;
import momentlockdemo.service.BoxService;

@Controller("openBoxMapController")
@RequestMapping("/momentlock")
public class OpenBoxMapController {
	
	@Autowired
	private BoxService boxService;
	
	// 오픈 상자 지도
	@GetMapping("/openboxmap")
	public String openboxmapPage() {
		return "html/box/openboxmap";
	}

	// api로 box list 받아오기 추후에 api 패키지를 만들어서 RestController로 설정해도 좋을듯
	@GetMapping("/api/boxdata")
	@ResponseBody
	public List<BoxLikeCountDto> getBoxData(){
		
		// Box 객체를 담은 list를 Json으로 보내려고하면 Box entity안에 순환참조 때문에 객체List를 못보냄
		// Box 데이터를 받아와서 boxDto로 변경후 boxDtoList를 보내기
		List<Box> boxList = boxService.getAllBoxes();
		List<BoxLikeCountDto> boxDtoList = new ArrayList<>();
		
		for (Box box : boxList) {
			BoxLikeCountDto boxDto
				= new BoxLikeCountDto(
						box.getBoxid(),
						box.getBoxcapcount(),
						box.getBoxmemcount(),
						box.getBoxopendate(),
						box.getBoxregdate(),
						box.getLatitude(),
						box.getLongitude(),
						box.getBoxlocation(),
						box.getBoxname(),
						null);
			boxDtoList.add(boxDto);
		}
		
		return  boxDtoList;
	}
	
}




