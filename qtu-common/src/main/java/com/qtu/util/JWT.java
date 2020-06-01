package com.qtu.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWT {
	 
	private static final String SECRET = "slfjsdlfsjd[[][]1312312";
	
	private static final String EXP = "exp";
	
	private static final String PAYLOAD = "payload";
 
	/**
	 * get jwt String of object
	 * @param object
	 *            the POJO object
	 * @param maxAge
	 *            the milliseconds of life time
	 * @return the jwt token
	 */
	public static <T> String sign(T object, long maxAge) {
		try {
			final JWTSigner signer = new JWTSigner(SECRET);
			final Map<String, Object> claims = new HashMap<String, Object>();
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(object);
			claims.put(PAYLOAD, jsonString);
			claims.put(EXP, System.currentTimeMillis() + maxAge);
			return signer.sign(claims);
		} catch(Exception e) {
			return null;
		}
	}
	
	
	/**
	 * get the object of jwt if not expired
	 * @param jwt
	 * @return POJO object
	 */
	public static<T> T unsign(String jwt, Class<T> classT) {
		final JWTVerifier verifier = new JWTVerifier(SECRET);
	    try {
			final Map<String,Object> claims= verifier.verify(jwt);
			if (claims.containsKey(EXP) && claims.containsKey(PAYLOAD)) {
				long exp = (Long)claims.get(EXP);
				long currentTimeMillis = System.currentTimeMillis();
				if (exp > currentTimeMillis) {
					String json = (String)claims.get(PAYLOAD);
					ObjectMapper objectMapper = new ObjectMapper();
					return objectMapper.readValue(json, classT);
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public static void main(String[] args) {
		//对称加密
//		User  u = new User();
//		u.setId(1L);
//		String token = sign(u, 1000*60*30);
//		System.out.println(token);
//		User u1 = unsign(token, User.class);
//		System.out.println(u1.toString());
//
//		String uuid = UUID.randomUUID().toString();
//		System.out.println(uuid);
		// redis key : ac3d0b97-e74d-441b-99ee-77d7aaa7dad1
		// redis value : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NzU5NDg3MTk4MDYsInBheWxvYWQiOiJ7XCJpZFwiOjEsXCJ1c2VybmFtZVwiOm51bGwsXCJwYXNzd29yZFwiOm51bGwsXCJwaG9uZVwiOm51bGwsXCJlbWFpbFwiOm51bGwsXCJjcmVhdGVkXCI6bnVsbCxcInVwZGF0ZWRcIjpudWxsfSJ9.xDXBT6p4sktYSqqxMVQMVS86fP9bgQH472mDNxE2wOo
		// redis value : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NzU5NDg4MjI3NjMsInBheWxvYWQiOiJ7XCJpZFwiOjEsXCJ1c2VybmFtZVwiOm51bGwsXCJwYXNzd29yZFwiOm51bGwsXCJwaG9uZVwiOm51bGwsXCJlbWFpbFwiOm51bGwsXCJjcmVhdGVkXCI6bnVsbCxcInVwZGF0ZWRcIjpudWxsfSJ9.bi4DvNz1MBJ4H9PlA8gCw1PN4ana7YDt8ThWWj5S_uk
		
		
		
	}
}