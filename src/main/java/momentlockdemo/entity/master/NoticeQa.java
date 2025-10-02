package momentlockdemo.entity.master;

import java.time.LocalDateTime;

import org.hibernate.annotations.Nationalized;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "NOTICE_QA")
public class NoticeQa {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NOTICE_QA_SEQ_GEN")
	@SequenceGenerator(name = "NOTICE_QA_SEQ_GEN", sequenceName = "NOTICE_QA_SEQ", allocationSize = 1)
	@Column(name = "NOTID")
	private Long id;

	@Nationalized
	@Column(name = "NOTTITLE", length = 1000)
	private String title;

	@Lob
	@Nationalized
	@Column(name = "NOTCONTENT")
	private String content;

	@Column(name = "NOTREGDATE")
	private LocalDateTime regDate;

	@Column(name = "NOTTYPE", length = 10)
	private String type;
	
	@PrePersist
	protected void noticeQaCreate() {
		this.regDate = LocalDateTime.now();
	}
}
