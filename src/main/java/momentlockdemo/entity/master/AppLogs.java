package momentlockdemo.entity.master;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
@Table(name = "APP_LOGS")
public class AppLogs {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_LOGS_SEQ_GEN")
	@SequenceGenerator(name = "APP_LOGS_SEQ_GEN", sequenceName = "APP_LOGS_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@Column(name = "LOG_DATE")
	private LocalDateTime logDate;

	@Column(name = "LOG_LEVEL", length = 20)
	private String logLevel;

	// Oracle CHAR(18) → 고정길이지만 JPA는 String으로 처리. 길이만 맞춰 둡니다.
	@Column(name = "LOGGER", length = 18, columnDefinition = "CHAR(18)")
	private String logger;

	@Lob
	@Column(name = "MESSAGE")
	private String message;

	@Column(name = "THREAD", length = 100)
	private String thread;

	@Lob
	@Column(name = "EXCEPTION")
	private String exception;
}
