package momentlockdemo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MEMBER_BOX")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberBox implements Serializable {

	public static final long serialVersionUID = 196818918L;

	@EmbeddedId // @IdClass 대신 @EmbeddedId 사용
	private MemberBoxId id;

	@MapsId("username") // MemberBoxId의 username과 매핑
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USERNAME")
	private Member member;

	@MapsId("boxId") // MemberBoxId의 boxId와 매핑
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BOXID")
	private Box box;

	@Column(name = "PARTYDATE")
	private LocalDateTime partydate;

	@Column(name = "READYCODE", length = 200)
	private String readycode;

	@Column(name = "BOXMATERCODE", length = 200)
	private String boxmatercode;
}
