package momentlockdemo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import momentlockdemo.entity.Box;
import momentlockdemo.entity.Capsule;
import momentlockdemo.entity.Member;

public interface CapsuleService {

   // 기본 CRUD
   public abstract Capsule createCapsule(Capsule capsule);

   public abstract Optional<Capsule> getCapsuleById(Long capid);

   public abstract List<Capsule> getAllCapsules();
   
   public abstract Capsule insertCapsule(Capsule capsule);
   
   public abstract Capsule updateCapsule(Capsule capsule);

   public abstract void deleteCapsule(Long capid);

   // 추가 비즈니스 로직
   public abstract List<Capsule> getCapsulesByBox(Box box);

   public abstract List<Capsule> getCapsulesByBoxOrderByDateDesc(Box box);
   

   public abstract List<Capsule> getCapsulesByMember(Member member);

   public abstract List<Capsule> searchCapsulesByTitle(String captitle);

   public abstract List<Capsule> searchCapsulesByBoxAndTitle(Box box, String captitle);

   public abstract List<Capsule> getCapsulesByDateRange(LocalDateTime startDate, LocalDateTime endDate);

   public abstract long countCapsulesByBox(Box box);

   public abstract long countCapsulesByMember(Member member);

   public abstract List<Capsule> getCapsulesOrderByLikeDesc();
   
   public abstract List<Capsule> getCapsulesTopLike();
   
}
