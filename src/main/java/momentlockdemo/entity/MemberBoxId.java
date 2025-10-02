package momentlockdemo.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
@Builder
public class MemberBoxId implements Serializable {
    
	public static final long serialVersionUID = 45645818918L;
	
    @Column(name = "USERNAME", length = 500)
    private String username;
    
    @Column(name = "BOXID")
    private Long boxId;
}
