package presentation;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import beans.Account;
import beans.Password;
import business.Authentification;
import business.Cryptographie;
import business.PassManagement;
import business.Utilities;

public class PlayGround {

	public static void main(String[] args) {
		//clipboardTest();
		//hashTest();
		//authentificationTest();
		//masterPassCreateTest();
		cryptPassTest();
	}
	
	public static void cryptPassTest()
	{
		Account account = new Account();
		account.setUserId("User2");
		account.setMasterPass("pass2");
		Password password = new Password();
		password.setDescription("Facebook");
		password.setValue("FacebookPassFacebookPassFacebookPass");
		account.getPassword().add(password);
		String MasterPass = PassManagement.createMasterPass(account.getMasterPass());
		System.out.println("MasterPass : "+MasterPass);
		SecretKey key = new SecretKeySpec(DatatypeConverter.parseHexBinary(
				MasterPass), "AES");
		System.out.println("clear");
		try {
			StringBuilder paddedPass = new StringBuilder(password.getValue());
			while(paddedPass.length() % 16 != 0)
				paddedPass.append("A");
			System.out.println("paddedPass : "+paddedPass+" paddedPass lentgh : "+paddedPass.length());
			String cryptedPass = DatatypeConverter.printHexBinary(Cryptographie.crypteAES(
					DatatypeConverter.parseHexBinary(Utilities.convertStringToHex(paddedPass.toString())), 
					key));
			System.out.println("cryptedPass : "+cryptedPass);
			String decryptedPass = DatatypeConverter.printHexBinary(Cryptographie.decrypte(DatatypeConverter.parseHexBinary(cryptedPass), 
					key));
			System.out.println("decryptedPass : "+Utilities.convertHexToString(decryptedPass));
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.out.println("InvalidKeyException");
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.out.println("NoSuchAlgorithmException");
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			System.out.println("NoSuchPaddingException");
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.out.println("IllegalBlockSizeException");
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.out.println("BadPaddingException");
			e.printStackTrace();
		}
	}
	
	public static void authentificationTest()
	{
		String userEnteredMasterPass = "pass2";
		String userId = "User2";
		
		try {
			Account account = Authentification.authentifyAccount(userId, userEnteredMasterPass);
			if(account == null)
				System.out.println("authentification failed");
			else
				System.out.println("welcom "+userId);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void clipboardTest()
	{
		String myString = "This text will be copied into clipboard";
		StringSelection stringSelection = new StringSelection(myString);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}
	
	public static void masterPassCreateTest()
	{
		String userEnteredMasterPass = "pass2";
		try {
			System.out.println(DatatypeConverter.printHexBinary(Utilities.hash256(
					PassManagement.createMasterPass(userEnteredMasterPass))));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void hashTest()
	{
		String userId = "User2";
		String hashedUserIdToBeCompared = "27A534A25CF745B6C985EB782079A6FE8641B00003DADA14F392A2D01B9C790A";
		try {
			String hashedUserID = DatatypeConverter.printHexBinary(Utilities.hash256(userId));
			System.out.println(hashedUserID);
			if(hashedUserID.equals(hashedUserIdToBeCompared))
				System.out.println("Correct user ID");
			else
				System.out.println("Incorrect user ID");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
