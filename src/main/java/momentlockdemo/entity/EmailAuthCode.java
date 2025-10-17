package momentlockdemo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EMAIL_AUTH_CODE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailAuthCode implements Serializable {
	
	public static final long serialVersionUID = 234234234324L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_auth_code_seq")
	@SequenceGenerator(name = "email_auth_code_seq", sequenceName = "EMAIL_AUTH_CODE_SEQ", allocationSize = 1)
	private Long id;
	
	@Column(nullable = false)
	private String email; // 이메일 주소
	
	 @Column(nullable = false)
    private String code; // 인증번호

    @Column(nullable = false)
    private LocalDateTime expiresAt; // 만료 시간

    @Column(nullable = false)
    private boolean used; // 사용 여부

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // 생성 시간

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.used = false;
    }
    
}
