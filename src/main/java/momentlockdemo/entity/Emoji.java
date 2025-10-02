package momentlockdemo.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "EMOJI")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Emoji implements Serializable {

	public static final long serialVersionUID = 11238918L;

	@Id
	@Column(name = "EMOID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emoji_seq")
	@SequenceGenerator(name = "emoji_seq", sequenceName = "EMOJI_SEQ", allocationSize = 1)
	private Long emoid;

	@Column(name = "EMOCOTENT", length = 200)
	private String emocotent;

	@Column(name = "EMOXAXIS")
	private Long emoxaxis;

	@Column(name = "EMOYAXIS")
	private Long emoyaxis;

	@Column(name = "EMOORDER")
	private Long emoorder;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CAPID")
	private Capsule capsule;
}
