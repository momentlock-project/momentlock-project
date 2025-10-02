package momentlockdemo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MEMBER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member implements Serializable {

	public static final long serialVersionUID = 7845918L;

	@Id
	@Column(name = "USERNAME", length = 500)
	private String username;

	@Column(name = "NAME", length = 200)
	private String name;

	@Column(name = "PASSWORD", length = 500)
	private String password;

	@Column(name = "NICKNAME", length = 200)
	private String nickname;

	@Column(name = "PHONENUMBER", length = 20)
	private String phonenumber;

	@Column(name = "MEMCAPCOUNT")
	private Long memcapcount;

	@Column(name = "MEMREGDATE")
	private LocalDateTime memregdate;

	@Column(name = "MEMCODE", length = 200)
	private String memcode;

	@Column(name = "MEMDELDATE")
	private LocalDateTime memdeldate;

	@Column(name = "MEMDECCOUNT")
	private Long memdeccount;

	@Column(name = "SUBCODE", length = 200)
	private String subcode;

	@Column(name = "SUBSTARTDAY")
	private LocalDateTime substartday;

	@Column(name = "SUBENDDAY")
	private LocalDateTime subendday;

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	@Builder.Default
	private List<Payment> payments = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	@Builder.Default
	private List<Capsule> capsules = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	@Builder.Default
	private List<Declaration> declarations = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	@Builder.Default
	private List<Inquiry> inquiries = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	@Builder.Default
	private List<MemberBox> memberBoxes = new ArrayList<>();

	@PrePersist
	protected void memberCreate() {
		this.memcapcount = 0L;
		this.memregdate = LocalDateTime.now();
		this.memcode = "MDN";
		this.memdeccount = 0L;
		this.subcode = "MNORMAL";
	}

}
