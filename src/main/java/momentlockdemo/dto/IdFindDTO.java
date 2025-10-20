package momentlockdemo.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdFindDTO implements Serializable {
	
	public static final long serialVersionUID = 31415921674L;
	
	private String name;
	private String phonenumber;

}