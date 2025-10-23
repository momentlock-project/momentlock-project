package momentlockdemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import momentlockdemo.entity.Member;

public interface MemberService {

	// 기본 CRUD
	public abstract void createMember(Member member);

	public abstract Optional<Member> getMemberByUsername(String username);

	public abstract List<Member> getAllMembers();

	public abstract Member updateMember(Member member);

	public abstract void deleteMember(String username);
	
	public abstract Member passwordResetMember(Member member);
	
	public abstract Member declarCountIncrease(Member member);

	// 추가 비즈니스 로직
	public abstract Optional<Member> getMemberByNickname(String nickname);

	public abstract Optional<Member> getMemberByPhonenumber(String phonenumber);

	public abstract boolean existsByNickname(String nickname);

	public abstract boolean existsByPhonenumber(String phonenumber);

	public abstract Optional<Member> getMemberByMemcode(String memcode);
	
	public abstract List<Member> getMembersByDecCountDesc();
	
	public abstract Page<Member> getAllMemberPage(Pageable pageable);
	
	public abstract Page<Member> getMemberPage(String nickname, Pageable pageable);
	
	public abstract void updateMemberToMDY(String username);
	
	public abstract Member updateLastlogindate(String username);
	
}
