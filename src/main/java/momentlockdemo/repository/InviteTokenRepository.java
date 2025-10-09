package momentlockdemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import momentlockdemo.entity.InviteToken;

public interface InviteTokenRepository extends JpaRepository<InviteToken, String> {
    Optional<InviteToken> findByTokenAndUsedYn(String token, String usedYn);
}

