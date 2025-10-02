package momentlockdemo.service;

import java.util.List;
import java.util.Optional;

import momentlockdemo.entity.Capsule;
import momentlockdemo.entity.Emoji;

public interface EmojiService {

	// 기본 CRUD
	public abstract Emoji createEmoji(Emoji emoji);

	public abstract Optional<Emoji> getEmojiById(Long emoid);

	public abstract List<Emoji> getAllEmojis();

	public abstract Emoji updateEmoji(Emoji emoji);

	public abstract void deleteEmoji(Long emoid);

	// 추가 비즈니스 로직
	public abstract List<Emoji> getEmojisByCapsule(Capsule capsule);

	public abstract List<Emoji> getEmojisByCapsuleOrderByOrder(Capsule capsule);

	public abstract List<Emoji> getEmojisByContent(String emocotent);

	public abstract long countEmojisByCapsule(Capsule capsule);

}
