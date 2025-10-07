package momentlockdemo.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

// 박스 정보와 해당 박스 안에 들어있는 캡슐의 좋아요 수의 합을 가지고 있는 DTO 클래스
@Data
@AllArgsConstructor
public class BoxLikeCountDto {

	private Long boxid;

	private Long boxcapcount;
	
	private Long boxmemcount;
	
	private LocalDateTime boxopendate;
	
	private LocalDateTime boxregdate;
	
	private String latitude;
	
	private String longitude;
	
	private String boxlocation;
	
	private String boxname;

	private Long caplikecount;
	
}
