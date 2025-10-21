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
@Table(name = "INQUIRY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inquiry implements Serializable {

	public static final long serialVersionUID = 1124438918L;

	@Id
	@Column(name = "INQID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inquiry_seq")
	@SequenceGenerator(name = "inquiry_seq", sequenceName = "INQUIRY_SEQ", allocationSize = 1)
	private Long inqid;

	@Column(name = "INQTITLE", length = 1000)
	private String inqtitle;

	@Lob
	@Column(name = "INQCONTENT")
	private String inqcontent;

	@Column(name = "INQREGDATE")
	private LocalDateTime inqregdate;

	@Column(name = "INQCOMPLETE", length = 10)
	private String inqcomplete;
	
	@Lob
	@Column(name = "INQANSWER")
	private String inqanswer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USERNAME")
	private Member member;

	@PrePersist
	protected void inquiryCreate() {
		this.inqregdate = LocalDateTime.now();
		this.inqcomplete = "QNAN";
	}

}
