package momentlockdemo.entity.master;

import org.hibernate.annotations.Nationalized;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "CODE")
public class Code {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CODE_SEQ_GEN")
	@SequenceGenerator(name = "CODE_SEQ_GEN", sequenceName = "CODE_SEQ", allocationSize = 1)
	@Column(name = "CODEID")
	private Long id;

	@Nationalized
	@Column(name = "CODECATEGORY", length = 50)
	private String category;

	@Column(name = "CODENAME", length = 100)
	private String name;

	@Nationalized
	@Column(name = "CODECONTENT", length = 500)
	private String content;
}
