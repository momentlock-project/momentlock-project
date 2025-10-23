package momentlockdemo.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import momentlockdemo.entity.Member;
import momentlockdemo.service.MailService;
import momentlockdemo.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service("mailService")
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

	private final JavaMailSender mailSender;
	private final TemplateEngine templateEngine;
	
	@Autowired
	private MemberService memberService;

	@Override
	@Async
	public void sendInviteMail(String to, String inviterNickname, String boxTitle, String inviteUrl) {
		try {
			Context context = new Context();
			context.setVariable("inviter", inviterNickname);
			context.setVariable("boxTitle", boxTitle);
			context.setVariable("inviteUrl", inviteUrl);

			String html = templateEngine.process("mail/invite", context);

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
			helper.setTo(to);
			helper.setSubject("[MomentLock] " + inviterNickname + "님이 '" + boxTitle + "' 박스에 초대했습니다.");
			helper.setText(html, true);

			mailSender.send(message);
		} catch (MessagingException e) {
			throw new IllegalStateException("초대 메일 발송 실패", e);
		}
	}

	@Override
	@Async
	public void sendPasswordResetMail(String to, String code) {
		try {
			Context context = new Context();
			context.setVariable("code", code);

			String html = templateEngine.process("mail/passwordAuthCode", context);

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
			helper.setTo(to);
			helper.setSubject("[MomentLock] 비밀번호 재설정 인증번호가 전송되었습니다.");
			helper.setText(html, true);

			mailSender.send(message);
		} catch (MessagingException me) {
			me.getStackTrace();
		}
	}

	@Override
	@Async
	public void sendBoxTransmitAlertMail(String to, String senderNickname,
			String boxTitle, String recipientId, String moveToUrl) {
		
		Context context = new Context();
		context.setVariable("senderNickname", senderNickname);
		context.setVariable("boxTitle", boxTitle);
		context.setVariable("username", recipientId);
		context.setVariable("moveToUrl", moveToUrl);
		
		String html = templateEngine.process("mail/boxTransmitAlertMail", context);
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		try {
			helper.setTo(to);
			helper.setSubject(senderNickname+" 님이 타임캡슐을 보냈습니다!");
			helper.setText(html, true);
			
			mailSender.send(message);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		
	}
}
