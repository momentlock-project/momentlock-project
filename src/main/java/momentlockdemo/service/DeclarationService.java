package momentlockdemo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import momentlockdemo.entity.Capsule;
import momentlockdemo.entity.Declaration;
import momentlockdemo.entity.Member;

public interface DeclarationService {
	// 기본 CRUD
	public abstract Declaration createDeclaration(Declaration declaration);

	public abstract Optional<Declaration> getDeclarationById(Long decid);

	public abstract Page<Declaration> getAllDeclarations(Pageable pageable);

	public abstract Declaration updateDeclaration(Declaration declaration);

	public abstract void deleteDeclaration(Long decid);

	// 추가 비즈니스 로직
	public abstract List<Declaration> getDeclarationsByMember(Member member);

	public abstract List<Declaration> getDeclarationsByCapsule(Capsule capsule);

	public abstract List<Declaration> getDeclarationsByCategory(String deccategory);

	public abstract List<Declaration> getDeclarationsByComplete(String deccomplete);

	public abstract List<Declaration> getDeclarationsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

	public abstract long countDeclarationsByComplete(String deccomplete);
}
