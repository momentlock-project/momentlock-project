package momentlockdemo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import momentlockdemo.dto.IdFindDTO;
import momentlockdemo.entity.Member;
import momentlockdemo.repository.MemberRepository;
import momentlockdemo.service.IdFindService;

@Service
public class IdFindServiceImpl implements IdFindService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Override
	public Optional<Member> idFindProcess(IdFindDTO idFindDTO) {
		
		Optional<Member> user = memberRepository.findByNameAndPhonenumber(
				idFindDTO.getName(), idFindDTO.getPhonenumber());
		
		return user;
		
	};
	
}