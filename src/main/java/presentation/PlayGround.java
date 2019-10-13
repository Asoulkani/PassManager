package presentation;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

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
		//masterPassCreateTest();
		//cryptPassTest();
		mainApp();
	}
	
	public static void mainApp()
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("==================================\n"
				 		 + "          PassManager\n"
				 		 + "==================================");
		System.out.println("1 ==> Create Account : ");
		System.out.println("2 ==> Loggin : ");
		switch (Integer.parseInt(sc.next())) {
		case 1:
			String newAccountUserID;
			String newAccountMasterPass;
			do {
				System.out.print("User : ");
				newAccountUserID = sc.next();
				System.out.print("Master Pass : ");
				newAccountMasterPass = sc.next();
				System.out.println("Re-Enter the master pass : ");
				if(newAccountMasterPass.equals(sc.next()))
					break;
				else
					System.out.println("not the same master pass !!!");
			} while (true);
			try {
				Account newAccount = Authentification.createAccount(newAccountUserID, newAccountMasterPass);
				PassManagement.setMasterPass(new SecretKeySpec(DatatypeConverter.parseHexBinary(
						PassManagement.createMasterPass(newAccountMasterPass)), "AES"));
				Authentification.setAccount(newAccount);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("==================================\n"
					 		 + "          Welcom "+newAccountUserID+"\n"
					 		 + "==================================");
			
			break;
		case 2:
			do {
				System.out.println("Login : \n"
						 + "User : ");
				String userID = sc.next();
				System.out.println("Master pass : ");
				String masterPass = sc.next();
				Account account = authentification(userID, masterPass);
				if(account != null)
				{
					Authentification.setAccount(account);
					PassManagement.setMasterPass(new SecretKeySpec(DatatypeConverter.parseHexBinary(
							PassManagement.createMasterPass(masterPass)), "AES"));
					System.out.println("==================================\n"
							 		 + "          Welcom "+userID+"\n"
							 		 + "==================================");
					break;
				}
				else
					System.out.println("wrong user or master pass");
			} while (true);
			break;
		default:
			break;
		}
		System.out.println("1 ==> Passwords List : ");
		System.out.println("2 ==> New password : ");
		switch (Integer.parseInt(sc.next())) {
		case 1:
			int i = 0;
			for (Password password : Authentification.getAccount().getPassword()) {
				System.out.println("********************************\n"
				 		 		 + "       "+i+" . "+password.getDescription()+"\n"
				 		 		 + "********************************");
				++i;
			}
			System.out.println("to copy a pass to the clipboard : the index of the password/1 | example : 0/1");
			System.out.println("to update a pass : the index of the password/2 | example : 0/2");
			System.out.println("to delete a pass : the index of the password/3 | example : 0/3");
			String choice = sc.next();
			int index = Integer.parseInt(choice.charAt(0)+"");
			int action = Integer.parseInt(choice.charAt(2)+"");
			switch (action) {
			case 1:
				try {
					PassManagement.copyPassToClipBoard(Authentification.getAccount().getPassword().get(index).getValue());
				} catch (InvalidKeyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalBlockSizeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
			break;

		default:
			break;
		}
		
		
		sc.close();
	}
	
	public static void cryptPassTest()
	{
		Account account = new Account();
		account.setUserId("User2");
		account.setMasterPass("pass1");
		Password password = new Password();
		password.setDescription("Facebook");
		password.setValue("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		account.getPassword().add(password);
		String MasterPass = PassManagement.createMasterPass(account.getMasterPass());
		System.out.println("MasterPass : "+MasterPass);
		SecretKey key = new SecretKeySpec(DatatypeConverter.parseHexBinary(
				MasterPass), "AES");
		System.out.println("clear");
		try {
			StringBuilder paddedPass = new StringBuilder(password.getValue());
			/*while(paddedPass.length() % 16 != 0)
				paddedPass.append("A");*/ // only needed if the pass is generated without the generatePass methode
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
	
	public static Account authentification(String userId,String userEnteredMasterPass)
	{
		Account account = null;
		try {
			account = Authentification.authentifyAccount(userId, userEnteredMasterPass);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return account;
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
