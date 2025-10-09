package momentlockdemo.service;

public interface InvitationService {

	public abstract void sendInvitation(String inviterNickname, String inviteeNickname, Long boxId);
	
}
