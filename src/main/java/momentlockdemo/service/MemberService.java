package momentlockdemo.service;

import java.util.List;
import java.util.Optional;

import momentlockdemo.entity.Member;

public interface MemberService {

	// 기본 CRUD
	public abstract Member createMember(Member member);

	public abstract Optional<Member> getMemberByUsername(String username);

	public abstract List<Member> getAllMembers();

	public abstract Member updateMember(Member member);

	public abstract void deleteMember(String username);

	// 추가 비즈니스 로직
	public abstract Optional<Member> getMemberByNickname(String nickname);

	public abstract Optional<Member> getMemberByPhonenumber(String phonenumber);

	public abstract boolean existsByNickname(String nickname);

	public abstract boolean existsByPhonenumber(String phonenumber);

	public abstract Optional<Member> getMemberByMemcode(String memcode);
	
	public abstract List<Member> getMembersByDecCountDesc();
}
