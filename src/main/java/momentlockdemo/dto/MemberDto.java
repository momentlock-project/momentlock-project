package momentlockdemo.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto implements Serializable {
	
	public static final long serialVersionUID = 21321312321312321L;
	
	private String username;
	
	private String password;
	
	private String subcode;
	
	private String nickname;
	
	private String name;
	
	private String memcode;
	
	private String phonenumber;
	
	private LocalDateTime substartday;
	
	private LocalDateTime subendday;
	
	private LocalDateTime memregdate;
	
	private LocalDateTime memdeldate;
	
	private Long memdeccount;
	
	private Long memcapcount;

}
