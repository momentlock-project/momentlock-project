package momentlockdemo.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@Table(name = "PAYMENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment implements Serializable {

	public static final long serialVersionUID = 8718918L;

	@Id
	@Column(name = "PAYID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_seq")
	@SequenceGenerator(name = "payment_seq", sequenceName = "PAYMENT_SEQ", allocationSize = 1)
	private Long payid;

	@Column(name = "PAYNUMBER")
	private Long paynumber;

	@Column(name = "AMOUNT")
	private BigDecimal amount;

	@Column(name = "PAYCODE", length = 200)
	private String paycode;
	
	@Column(name = "PAYREGDATE")
	private LocalDateTime payregdate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USERNAME")
	private Member member;
	
	@PrePersist
	protected void paymentCreate() {
		this.payregdate = LocalDateTime.now();
	}
	
	
}






