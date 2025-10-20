package momentlockdemo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "BOX")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Box implements Serializable {

	public static final long serialVersionUID = 9987818918L;

	@Id
	@Column(name = "BOXID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "box_seq")
	@SequenceGenerator(name = "box_seq", sequenceName = "BOX_SEQ", allocationSize = 1)
	private Long boxid;

	@Column(name = "BOXNAME", length = 1000)
	private String boxname;

	@Column(name = "BOXLOCATION", length = 1000)
	private String boxlocation;

	@Column(name = "LATITUDE", length = 200)
	private String latitude;

	@Column(name = "LONGITUDE", length = 200)
	private String longitude;

	@Column(name = "BOXOPENDATE")
	private LocalDateTime boxopendate;

	@Column(name = "BOXREGDATE")
	private LocalDateTime boxregdate;

	@Column(name = "BOXINVITECODE", length = 500)
	private String boxinvitecode;

	@Column(name = "BOXBURYCODE", length = 200)
	private String boxburycode;

	@Column(name = "BOXDELCODE", length = 200)
	private String boxdelcode;

	@Column(name = "BOXRELEASECODE", length = 200)
	private String boxreleasecode;

	@Column(name = "BOXMEMCOUNT")
	private Long boxmemcount;

	@Column(name = "BOXCAPCOUNT")
	private Long boxcapcount;

	@OneToMany(mappedBy = "box", cascade = CascadeType.ALL)
	@Builder.Default
	private List<Capsule> capsules = new ArrayList<>();

	@OneToMany(mappedBy = "box", cascade = CascadeType.ALL)
	@Builder.Default
	private List<MemberBox> memberBoxes = new ArrayList<>();

	@PrePersist
	protected void boxCreate() {
		this.boxregdate = LocalDateTime.now();
		this.boxburycode = "BBN";
		this.boxdelcode = "BDN";
		this.boxcapcount = 0L;
	}

}
