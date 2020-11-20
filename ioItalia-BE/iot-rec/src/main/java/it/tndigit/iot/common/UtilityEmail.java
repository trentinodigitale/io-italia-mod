package it.tndigit.iot.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @author Mirko Pianetti
 * Utility Email class for the project
 *
 *
 */
@Component
public class UtilityEmail {


	@Autowired
	protected JavaMailSender javaMailSender;

    /**
     *
	 * @param simpleMailMessage
	 *
	 * Invia messaggi per Email,
	 */
	public String sendEmail(SimpleMailMessage simpleMailMessage){
		try{
			javaMailSender.send(simpleMailMessage);
			return "";
		}catch (Exception ex){
			return ex.getMessage();
		}


	}

}
