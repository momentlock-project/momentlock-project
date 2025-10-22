package momentlockdemo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberBoxDto {
	
	private Long boxid; 

	private String boxname;
	
	private LocalDateTime boxopendate;
	
	private String code;
	
	private String burycode;
}
