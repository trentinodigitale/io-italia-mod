package it.tndigit.iot.common;

import it.tndigit.iot.web.rest.utils.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.regex.Pattern;

/**
 * @author Mirko Pianetti
 * Utility class for the project
 *
 *
 */
public class UtilityIot {


	private static final String REG_PAGAMENTO = "^[0123][0-9]{17}$";

	@Autowired
	protected JavaMailSender javaMailSender;

	/**
	 *
	 * @return String
	 *
	 * get the username from the principal
	 * see JwtAuthenticationTokenBeforeFilter class for the specific string
	 *
	 */

	private UtilityIot() {
		throw new IllegalStateException("Utility class");
	}

	public static String getUserName() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication.getPrincipal() instanceof User) {
				User user = (User) authentication.getPrincipal();
				return user.getUsername();
			}else if (authentication.getPrincipal() instanceof JwtUser) {
				JwtUser user = (JwtUser) authentication.getPrincipal();
				return user.getUsername();
			} else {
				return authentication.getName();
			}

		} catch (Exception e) {
			return "";
		}
	}

	public static Boolean checkCodicePagamento(String codice){
		return  Pattern.matches(REG_PAGAMENTO, codice);
	}




}
