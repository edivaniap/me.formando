  
package br.ufrn.imd.meformando.util;

import org.mindrot.jbcrypt.BCrypt;

public class CryptService {
	public static String getHashedPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt(16));
	}
	public static Boolean verifyPasswords(String candidate, String hashedPassword) {
		return BCrypt.checkpw(candidate, hashedPassword);
	}
}