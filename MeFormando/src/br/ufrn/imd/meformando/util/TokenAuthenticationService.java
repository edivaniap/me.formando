  
package br.ufrn.imd.meformando.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticationService {
	private static final String SECRET = "EuEstouMeFormando";
	private static final String TOKEN_PREFIX = "MeFormando";
	public static final String HEADER_STRING = "Authorization";
	
	public static String addAuthentication(String email) {
		String JWT = Jwts.builder()
				.setSubject(email)
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
		return JWT;
	}
	
	public static String getAuthentication(String token) {
		if (token != null) {
			// faz parse do token
			String email = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
					.getBody()
					.getSubject();
			
			if (email != null) {
				return email;
			}
		}
		//token inválido
		return null;
	}
}