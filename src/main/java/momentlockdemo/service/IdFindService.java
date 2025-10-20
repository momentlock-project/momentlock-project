package momentlockdemo.service;

import java.util.Optional;

import momentlockdemo.dto.IdFindDTO;
import momentlockdemo.entity.Member;

public interface IdFindService {
	
	public Optional<Member> idFindProcess(IdFindDTO idFindDTO);	
	
}