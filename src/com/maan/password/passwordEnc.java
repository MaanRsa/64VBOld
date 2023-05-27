package com.maan.password;



import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//import com.maan.auth.PasswordEnc;
import com.maan.common.LogManager;

import sun.misc.BASE64Encoder;

public class passwordEnc {

	private static passwordEnc instance;

	private String loginName="";

	private String displayName="";

	private String loginPassword;

	public void LoginInfo(final String userName,final String password) {
		try {
			if (!(this.loginPassword.equals(crypt(password.substring(0, 3),
					password)))) {
				this.loginName = null;
			}
		} catch (Exception exception) {
			this.loginName = null;
			this.displayName = null;
			this.loginPassword = null;
		}
	}
	public static void main(String args[]) {
      /** Sample usage java jcrypt password */
    	LogManager.push(crypt("upl", "upload123"));
      
    }

	public static String crypt(final String salt,final String original) {
		 String encryptedString = "";
		
		try {
			encryptedString = encrypt(original);
		} catch (SystemUnavailableException e) {
			LogManager.push("password crypt "+e.getMessage());LogManager.logEnter();
		}
		return encryptedString;

	}

	public static String encrypt(final String plaintext) throws SystemUnavailableException {
		MessageDigest msgd = null;
		try {
			msgd = MessageDigest.getInstance("SHA"); // step 2
			msgd.update(plaintext.getBytes("UTF-8")); // step 3

		} catch (NoSuchAlgorithmException e) {
			throw new SystemUnavailableException(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			throw new SystemUnavailableException(e.getMessage());
		}
		final byte raw[] = msgd.digest(); // step 4
		final String hash = new BASE64Encoder().encode(raw); // step 5
		return hash; // step 6
	}

	public static synchronized passwordEnc getInstance() 
		{ // step 1
		if (instance == null) {
			instance = new passwordEnc();
		}
		return instance;
	}
}