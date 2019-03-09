package com.frostwolf.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @Author:YYX
 * @Description:
 * @Date:Created in 16:17 2019/3/9
 * @Modified By:
 */
public class JWTUtil {

	private static final Logger logger = Logger.getLogger(JWTUtil.class.getName());
	private static final long EXPIRE_TIME = 15 * 60 * 1000;
	private static final String PRIVATE_KEY = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
	private static final String HEADER_KEY_ALG = "alg";
	private static final String HEADER_KEY_TYPE = "typ";
	private static final String HEADER_VALUE_ALG = "HS256";
	private static final String HEADER_VALUE_TYPE = "JWT";

	public static String sign(String userName, String userId) {
		logger.info("开始生成token");
		Date expireTime = new Date(System.currentTimeMillis() + EXPIRE_TIME);
		try {
			Algorithm algorithm = Algorithm.HMAC256(PRIVATE_KEY);
			Map<String, Object> header = new HashMap<String, Object>(2);
			header.put(HEADER_KEY_ALG, HEADER_VALUE_ALG);
			header.put(HEADER_KEY_TYPE, HEADER_VALUE_TYPE);
			String token = JWT.create()
					.withHeader(header)
					.withClaim("name", userName)
					.withClaim("id", userId)
					.withExpiresAt(expireTime)
					.sign(algorithm);
			logger.info("生成token结束， token = " + token);
			return token;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
