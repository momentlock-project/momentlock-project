package momentlockdemo.service;

public interface MailService {

	public abstract void sendInviteMail(String to, String inviter, String boxTitle, String inviteUrl);
	
	public abstract void sendPasswordResetMail(String to, String code);

}
