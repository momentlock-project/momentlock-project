package momentlockdemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import momentlockdemo.entity.Capsule;
import momentlockdemo.entity.Emoji;

public interface EmojiRepository extends JpaRepository<Emoji, Long> {

	List<Emoji> findByCapsule(Capsule capsule);

	List<Emoji> findByCapsuleOrderByEmoorderAsc(Capsule capsule);

	List<Emoji> findByEmocotent(String emocotent);

	long countByCapsule(Capsule capsule);

}
