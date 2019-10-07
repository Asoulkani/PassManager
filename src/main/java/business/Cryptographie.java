package business;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Cryptographie {
	
	/*=================================================
	 * 
	 * crypt a given data with AES algorithm  
	 * (key size 128 192 256)
	 * 
	 =================================================*/
	
	public static byte[] crypteAES(byte[] DataToCrypt, SecretKey key)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException {
		Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(DataToCrypt);
	}
	
	/*=================================================
	 * 
	 * decrypt a given data with AES algorithm  
	 * (key size 128 192 256)
	 * 
	 =================================================*/
	
	public static byte[] decrypte(byte[] DataToDecrypt, SecretKey key)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException {
		Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(DataToDecrypt);
	}

}
