package presentation;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import beans.Account;
import business.Authentification;
import business.PassManagement;
import business.Utilities;
import dao.DaoXmlAccount;

public class PlayGround {

	public static void main(String[] args) {
		//clipboardTest();
		//hashTest();
		authentificationTest();
		//masterPassCreateTest();
	}
	
	public static void authentificationTest()
	{
		DaoXmlAccount dao = new DaoXmlAccount();
		dao.init();
		
		String userEnteredMasterPass = "pass1";
		String userId = "User1";
		
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
		String userEnteredMasterPass = "pass1";
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
		String userId = "User1";
		String hashedUserIdToBeCompared = "27A534A25CF745B6C985EB782079A6FE8641B00003DADA14F392A2D01B9C790A";
		try {
			String hashedUserID = DatatypeConverter.printHexBinary(Utilities.hash256(userId));
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
