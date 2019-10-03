package testPackage;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBException;

import security.Authentification;
import security.Utilities;

public class PlayGround {

	public static void main(String[] args) {
		//clipboardTest();
		//hashTest();
		authentificationTest();
	}
	
	public static void authentificationTest()
	{
		try {
			Authentification.initAccountDataBase();
		} catch (JAXBException e) {
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
