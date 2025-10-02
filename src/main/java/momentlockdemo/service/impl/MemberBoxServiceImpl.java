package momentlockdemo.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import momentlockdemo.entity.Box;
import momentlockdemo.entity.Member;
import momentlockdemo.entity.MemberBox;
import momentlockdemo.entity.MemberBoxId;
import momentlockdemo.repository.BoxRepository;
import momentlockdemo.repository.MemberBoxRepository;
import momentlockdemo.service.MemberBoxService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberBoxServiceImpl implements MemberBoxService {

	private final MemberBoxRepository memberBoxRepository;

	private final BoxRepository boxRepository;
	
	@Override
	@Transactional
	public MemberBox createMemberBox(MemberBox memberBox) {
		return memberBoxRepository.save(memberBox);
	}

	@Override
	public Optional<MemberBox> getMemberBoxById(MemberBoxId id) {
		return memberBoxRepository.findById(id);
	}

	@Override
	public List<MemberBox> getAllMemberBoxes() {
		return memberBoxRepository.findAll();
	}

	@Override
	@Transactional
	public MemberBox updateMemberBox(MemberBox memberBox) {
		return memberBoxRepository.save(memberBox);
	}

	@Override
	@Transactional
	public void deleteMemberBox(MemberBoxId id) {
		memberBoxRepository.deleteById(id);
	}

	@Override
	public List<MemberBox> getBoxesByMember(Member member) {
		return memberBoxRepository.findByMember(member);
	}

	@Override
	public List<MemberBox> getMembersByBox(Box box) {
		return memberBoxRepository.findByBox(box);
	}

	@Override
	public boolean existsMemberBox(Member member, Box box) {
		return memberBoxRepository.existsByMemberAndBox(member, box);
	}

	@Override
	public Optional<MemberBox> getMemberBox(Member member, Box box) {
		return memberBoxRepository.findByMemberAndBox(member, box);
	}

	@Override
	public List<MemberBox> getMemberBoxesByReadyCode(String readycode) {
		return memberBoxRepository.findByReadycode(readycode);
	}

	@Override
	public long countMembersByBox(Box box) {
		return memberBoxRepository.countByBox(box);
	}

	@Override
	public List<MemberBox> getBoxManagers(Box box, String matercode) {
		return memberBoxRepository.findBoxManagers(box, matercode);
	}

	@Override
	@Transactional
	public Box createBoxWithMember(Box box, Member member) {
		// 1. Box 먼저 저장
		Box savedBox = boxRepository.save(box);

		// 2. MemberBox 관계 생성 (생성자를 박스 멤버로 추가)
		MemberBoxId memberBoxId =
				MemberBoxId.builder()
				.username(member.getUsername())
				.boxId(savedBox.getBoxid())
				.build();
		
		MemberBox memberBox = MemberBox.builder().id(memberBoxId).member(member).box(savedBox)
				.partydate(LocalDateTime.now()) // 참여 날짜를 현재 시간으로
				.readycode("MRN") // 준비 미완료 상태로 시작
				.boxmatercode("MCB") // 생성자는 관리자
				.build();

		memberBoxRepository.save(memberBox);

		// 3. Box의 멤버 카운트 증가
		savedBox.setBoxmemcount(1L);

		return savedBox;
	}

	@Override
	@Transactional
	public Box joinBoxWithMember(Box box, Member member) {

		// 1. MemberBox 관계 생성 (생성자를 박스 멤버로 추가)
		MemberBoxId memberBoxId = 
				MemberBoxId.builder()
				.username(member.getUsername())
				.boxId(box.getBoxid())
				.build();

		MemberBox memberBox = MemberBox.builder().id(memberBoxId).member(member).box(box)
				.partydate(LocalDateTime.now()) // 참여 날짜를 현재 시간으로
				.readycode("MRN") // 준비 미완료 상태로 시작
				.boxmatercode(null) // 참가자는 null로
				.build();

		memberBoxRepository.save(memberBox);

		// 3. Box의 멤버 카운트 증가
		box.setBoxmemcount(box.getBoxmemcount() + 1);
		
		return box;
	}
	
}






