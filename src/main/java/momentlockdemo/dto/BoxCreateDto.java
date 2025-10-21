package momentlockdemo.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class BoxCreateDto {

	private Long boxid; // 수정 시 필요

	private String boxName;

	private Long memberCount;

	private Boolean isPublic;

	private LocalDate openDate;

	private String boxlocation;

	private String latitude;

	private String longitude;

	// box entity 에서 필요한 LocalDateTime 타입을 메서드를 통해 여기에서 변환
	// dto에서는 dto.getOpenDateTime() 이렇게 사용한다. 이유는 html 쪽에서는 LocalDate 이렇게 받아야해서
	public LocalDateTime getOpenDateTime() {
		return openDate != null ? openDate.atStartOfDay() : null;
	}
}