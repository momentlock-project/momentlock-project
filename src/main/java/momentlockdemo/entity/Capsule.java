package momentlockdemo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
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
@Table(name = "CAPSULE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Capsule implements Serializable {

	public static final long serialVersionUID = 999112318918L;

	@Id
	@Column(name = "CAPID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "capsule_seq")
	@SequenceGenerator(name = "capsule_seq", sequenceName = "CAPSULE_SEQ", allocationSize = 1)
	private Long capid;

	@Column(name = "CAPTITLE", length = 1000)
	private String captitle;

	@Lob
	@Column(name = "CAPCONTENT")
	private String capcontent;

	@Column(name = "CAPREGDATE")
	private LocalDateTime capregdate;

	@Column(name = "CAPDELCODE", length = 200)
	private String capdelcode;

	@Column(name = "CAPLIKECOUNT")
	private Long caplikecount;

	@Column(name = "CAPAFILECOUNT")
	private Long capafilecount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BOXID")
	private Box box;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USERNAME")
	private Member member;

	@OneToMany(mappedBy = "capsule", cascade = CascadeType.ALL)
	@Builder.Default
	private List<Emoji> emojis = new ArrayList<>();

	@OneToMany(mappedBy = "capsule", cascade = CascadeType.ALL)
	@Builder.Default
	private List<Afile> afiles = new ArrayList<>();

	@OneToMany(mappedBy = "capsule", cascade = CascadeType.ALL)
	@Builder.Default
	private List<Declaration> declarations = new ArrayList<>();

	@PrePersist
	protected void capsuleCreate() {
		this.capregdate = LocalDateTime.now();
		this.capdelcode = "TDN";
		this.caplikecount = 0L;
		this.capafilecount = 0L;
	}

}
