package br.ufrn.imd.meformando.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidaEmail {

	/*
	 * !
	 * 
	 * @brief Verifica se email esta no formato example@something.com
	 */
	public static boolean isEmailValido(String email) {
	
		boolean isEmailIdValid = false;
		if (email != null && email.length() > 0) {
			String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(email);
			if (matcher.matches()) {
				isEmailIdValid = true;
			}
		}
		return isEmailIdValid;
	}
}
