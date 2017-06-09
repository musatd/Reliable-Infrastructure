package org.reliable.infrastructure.AMQPReceiver.messagesTasks;

public interface Constants {
	
	Integer USER_INVALID_NUMBER = 21211;
	Integer USER_SMS_INCAPABLE = 21614;
	
	Integer ADMIN_UNREACHABLE_CLIENT = 21612;
	Integer ADMIN_PERMISSION_DISABLED = 21408;
	
	Integer ADMIN_INVALID_NUMBER = 21212;
	Integer ADMIN_SMS_INCAPABLE = 21606;
	
	String USER_SUBTITLE_NOTIFICATION = "Verificare setari";
	String USER_MESSAGE_NOTIFICATION = "Verificati numarul de telefon introdus in sectiunea 'Optiuni'. "
											+ "La numarul de telefon curent nu putem sa va contactam";

}
