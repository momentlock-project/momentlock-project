package momentlockdemo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import momentlockdemo.entity.Capsule;
import momentlockdemo.entity.Emoji;
import momentlockdemo.repository.EmojiRepository;
import momentlockdemo.service.EmojiService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmojiServiceImpl implements EmojiService {
    
    private final EmojiRepository emojiRepository;
    
    @Override
    @Transactional
    public Emoji createEmoji(Emoji emoji) {
        return emojiRepository.save(emoji);
    }
    
    @Override
    public Optional<Emoji> getEmojiById(Long emoid) {
        return emojiRepository.findById(emoid);
    }
    
    @Override
    public List<Emoji> getAllEmojis() {
        return emojiRepository.findAll();
    }
    
    @Override
    @Transactional
    public Emoji updateEmoji(Emoji emoji) {
        return emojiRepository.save(emoji);
    }
    
    @Override
    @Transactional
    public void deleteEmoji(Long emoid) {
        emojiRepository.deleteById(emoid);
    }
    
    @Override
    public List<Emoji> getEmojisByCapsule(Capsule capsule) {
        return emojiRepository.findByCapsule(capsule);
    }
    
    @Override
    public List<Emoji> getEmojisByCapsuleOrderByOrder(Capsule capsule) {
        return emojiRepository.findByCapsuleOrderByEmoorderAsc(capsule);
    }
    
    @Override
    public List<Emoji> getEmojisByContent(String emocotent) {
        return emojiRepository.findByEmocotent(emocotent);
    }
    
    @Override
    public long countEmojisByCapsule(Capsule capsule) {
        return emojiRepository.countByCapsule(capsule);
    }
}
