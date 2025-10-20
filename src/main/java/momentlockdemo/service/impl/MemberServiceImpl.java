package momentlockdemo.service.impl;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import momentlockdemo.entity.Member;
import momentlockdemo.repository.MemberRepository;
import momentlockdemo.service.MemberService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    
	@Autowired
    private MemberRepository memberRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
    
    @Override
    @Transactional
    public void createMember(Member member) {
    	
    	if(memberRepository.existsByUsername(member.getUsername())
    		|| memberRepository.existsByNickname(member.getNickname())) {
    		throw new RuntimeException("이미 존재하는 아이디 또는 닉네임입니다.");
    	}
    	
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        
        memberRepository.save(member);
        
    }
    
    @Override
    public Optional<Member> getMemberByUsername(String username) {
        return memberRepository.findById(username);
    }
    
    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
    
    @Override
    @Transactional
    public Member updateMember(Member member) {
    	
//    	if(memberRepository.existsByNickname(member.getNickname())) {
//    		throw new RuntimeException("이미 존재하는 닉네임입니다.");
//    	}
    	
    	member.setPassword(passwordEncoder.encode(member.getPassword()));
        return memberRepository.save(member);
    }
    
    @Override
    @Transactional
    public void deleteMember(String username) {
        memberRepository.deleteById(username);
    }
    
    @Override
    public Optional<Member> getMemberByNickname(String nickname) {
        return memberRepository.findByNickname(nickname);
    }
    
    @Override
    public Optional<Member> getMemberByPhonenumber(String phonenumber) {
        return memberRepository.findByPhonenumber(phonenumber);
    }
    
    @Override
    public boolean existsByNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }
    
    @Override
    public boolean existsByPhonenumber(String phonenumber) {
        return memberRepository.existsByPhonenumber(phonenumber);
    }
    
    @Override
    public Optional<Member> getMemberByMemcode(String memcode) {
        return memberRepository.findByMemcode(memcode);
    }
    
    @Override
    public List<Member> getMembersByDecCountDesc() {
    	return memberRepository.findAllByOrderByMemdeccountDesc();
    }
    
    @Override
    public Page<Member> getAllMemberPage(Pageable pageable) {
    	return memberRepository.findAll(pageable);
    }
    
    @Override
    public Page<Member> getMemberPage(String nickname, Pageable pageable) {
    	return memberRepository.findByNicknameContainingIgnoreCase(nickname, pageable);
    }
    
    @Override
    @Transactional
    public void updateMemberToMDY(String username) {
    	Member member = memberRepository.findById(username)
    	.orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. username: " + username));

        member.setMemcode("MDY");
    	
    }
    
}
