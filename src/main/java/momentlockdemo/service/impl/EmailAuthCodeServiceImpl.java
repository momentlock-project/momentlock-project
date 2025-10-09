package momentlockdemo.service.impl;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import momentlockdemo.entity.EmailAuthCode;
import momentlockdemo.repository.EmailAuthCodeRepository;
import momentlockdemo.service.EmailAuthCodeService;
import momentlockdemo.service.MailService;

@Service("emailAuthCodeService")
@RequiredArgsConstructor
public class EmailAuthCodeServiceImpl implements EmailAuthCodeService {
	
	private final EmailAuthCodeRepository emailAuthCodeRepository;
	private final MailService mailService;

	@Override
	@Transactional
	public void sendAuthCode(String email) {
		String code = createAuthCode();
		
		// 기존 이메일 삭제
		emailAuthCodeRepository.deleteByEmail(email);

		EmailAuthCode emailAuthCode = EmailAuthCode.builder()
			.email(email)
			.code(code)
			.expiresAt(LocalDateTime.now().plusMinutes(5)) // 5분 유효
            .build();
		
		emailAuthCodeRepository.save(emailAuthCode);
		
		// 이메일 발송
		mailService.sendPasswordResetMail(email, code);
	}

	
	/*
	 인증번호 검증
	 */
	@Override
	@Transactional
	public boolean verifyAuthCode(String email, String code) {
		return emailAuthCodeRepository.findByEmailAndCodeAndUsedIsFalse(email, code)
                .filter(ac -> ac.getExpiresAt().isAfter(LocalDateTime.now())) // 만료 안 됐는지 체크
                .map(ac -> {
                    ac.setUsed(true); 
                    return true;
                })
                .orElse(false);
	}
	
	
	/*
	 랜덤 인증번호 6자리 생성
	 */
	private String createAuthCode() {
		Random random = new Random();
		int number = 100000 + random.nextInt(900000);
		return String.valueOf(number);
	}

}
