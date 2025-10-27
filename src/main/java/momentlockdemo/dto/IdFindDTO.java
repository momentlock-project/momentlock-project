package momentlockdemo.dto;

import java.io.Serializable;

<<<<<<< HEAD
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
=======
import lombok.Getter;
>>>>>>> 8db9c49 (add social login)
import lombok.Setter;

@Getter
@Setter
<<<<<<< HEAD
@NoArgsConstructor
@AllArgsConstructor
=======
>>>>>>> 8db9c49 (add social login)
public class IdFindDTO implements Serializable {
	
	public static final long serialVersionUID = 31415921674L;
	
	private String name;
	private String phonenumber;

}