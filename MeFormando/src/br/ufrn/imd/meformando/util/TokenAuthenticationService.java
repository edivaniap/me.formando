package br.ufrn.imd.meformando.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticationService {
	private static final String SECRET = "EuEstouMeFormando";
	private static final String TOKEN_PREFIX = "MeFormando";
	public static final String HEADER_STRING = "Authorization";
	
	public static String addAuthentication(String id) {
		String JWT = Jwts.builder()
				.setSubject(id)
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
		return JWT;
	}
	
	public static String getAuthentication(String token) {
		if (token != null) {
			// faz parse do token
			String id = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
					.getBody()
					.getSubject();
			
			if (id != null) {
				return id;
			}
		}
		//token inválido
		return null;
	}
}
