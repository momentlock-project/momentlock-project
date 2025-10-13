package momentlockdemo.service;

public interface EmailAuthCodeService {
	
	public abstract void sendAuthCode(String email);
	
	public abstract boolean verifyAuthCode(String email, String code);

}
