package gateway.config.customComponent;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomPasswordEncoder implements PasswordEncoder {

	public String encode(CharSequence rawPassword) {
		String demoSalt = "demoSalt";

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			
			byte[] tmpByteArray = digest.digest((rawPassword + demoSalt).getBytes("UTF-8"));
			
			String tmpStr = Base64.getEncoder().encodeToString(tmpByteArray);
			
			return tmpStr;
			
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			return null;
		}
	}

	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		
		if (encodedPassword == null || encodedPassword.length() == 0) {
			return false;
		}

		return encode(rawPassword).equals(encodedPassword); 
	}
}