package momentlockdemo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "AFILE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Afile implements Serializable {

	public static final long serialVersionUID = 11238918L;

	@Id
	@Column(name = "AFID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "afile_seq")
	@SequenceGenerator(name = "afile_seq", sequenceName = "AFILE_SEQ", allocationSize = 1)
	private Long afid;

	@Column(name = "AFSNAME", length = 1000)
	private String afsname;

	@Column(name = "AFCNAME", length = 1000)
	private String afcname;

	@Column(name = "AFCONTENTTYPE", length = 1000)
	private String afcontenttype;

	@Column(name = "AFREGDATE")
	private LocalDateTime afregdate;

	@Column(name = "AFDELYN", length = 5)
	private String afdelyn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CAPID")
	private Capsule capsule;

	@PrePersist
	protected void afileCreate() {
		this.afregdate = LocalDateTime.now();
		this.afdelyn = "ADDN";
	}
}
