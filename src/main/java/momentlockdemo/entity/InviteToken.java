package momentlockdemo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "INVITE_TOKEN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InviteToken {

	@Id
	@Column(name = "TOKEN", length = 64)
	private String token;

	@Column(name = "BOX_ID", nullable = false)
	private Long boxId;

	@Column(name = "INVITER_USER_ID", nullable = false)
	private String inviterUserId;

	@Column(name = "INVITEE_NICKNAME", nullable = false, length = 50)
	private String inviteeNickname;

	@Column(name = "INVITEE_USER_ID")
	private String inviteeUserId;

	@Column(name = "EXPIRES_AT", nullable = false)
	private LocalDateTime expiresAt;

	@Column(name = "USED_YN", length = 1)
	private String usedYn;

	@Column(name = "USED_AT")
	private LocalDateTime usedAt;

	@Column(name = "CREATED_AT")
	private LocalDateTime createdAt;

	@PrePersist
	public void prePersist() {
		this.usedYn = "N";
		this.createdAt = LocalDateTime.now();
	}
}
