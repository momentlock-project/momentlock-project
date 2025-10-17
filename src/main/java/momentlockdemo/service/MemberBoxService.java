package momentlockdemo.service;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import momentlockdemo.entity.Box;
import momentlockdemo.entity.Member;
import momentlockdemo.entity.MemberBox;
import momentlockdemo.entity.MemberBoxId;

public interface MemberBoxService {

	// 기본 CRUD
	public abstract MemberBox createMemberBox(MemberBox memberBox);

	public abstract Optional<MemberBox> getMemberBoxById(MemberBoxId id);

	public abstract List<MemberBox> getAllMemberBoxes();

	public abstract MemberBox updateMemberBox(MemberBox memberBox);

	public abstract void deleteMemberBox(MemberBoxId id);

	// 추가 비즈니스 로직
	public abstract List<MemberBox> getBoxesByMember(Member member);

	public abstract List<MemberBox> getMembersByBox(Box box);

	public abstract boolean existsMemberBox(Member member, Box box);

	public abstract Optional<MemberBox> getMemberBox(Member member, Box box);

	public abstract List<MemberBox> getMemberBoxesByReadyCode(String readycode);

	public abstract long countMembersByBox(Box box);

	public abstract List<MemberBox> getBoxManagers(Box box, String matercode);
	
	 // 회원이 박스를 추가할때 MemberBox 테이블에 추가 메서드 추가
	public abstract Box createBoxWithMember(Box box, Member member);
	
	// 회원이 박스에 가입할때 
	public abstract Box joinBoxWithMember(Box box, Member member);
	
	public abstract MemberBox getMemberBoxByBox(Box box);

	// 방장이 제일 위로 간뒤 정렬
	public abstract List<MemberBox> getMembersByBoxSorted(Box box);

//	전송된 박스 소유자 갱신(전달된 박스의 기존 소유자 삭제 -> 박스 수신자를 새로운 소유자로 생성? 임명)
	void renewMemberBox(Box transmitedBox, Member recipient);
	
}
