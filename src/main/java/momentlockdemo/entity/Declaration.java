package momentlockdemo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "DECLARATION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Declaration implements Serializable {

	public static final long serialVersionUID = 334818918L;

	@Id
	@Column(name = "DECID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "declaration_seq")
	@SequenceGenerator(name = "declaration_seq", sequenceName = "DECLARATION_SEQ", allocationSize = 1)
	private Long decid;

	@Column(name = "DECCATEGORY", length = 500)
	private String deccategory;

	@Lob
	@Column(name = "DECCONTENT")
	private String deccontent;

	@Column(name = "DECREGDATE")
	private LocalDateTime decregdate;

	@Column(name = "DECCOMPLETE", length = 10)
	private String deccomplete;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USERNAME")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CAPID")
	private Capsule capsule;

	@PrePersist
	protected void declarationCreate() {
		this.decregdate = LocalDateTime.now();
		this.deccomplete = "DECN";
	}

}
