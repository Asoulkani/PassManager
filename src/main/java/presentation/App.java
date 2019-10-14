package presentation;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import beans.Account;
import beans.Password;
import business.Authentification;
import business.DataController;
import business.PassManagement;
import business.Utilities;
import exception.InvalidLengthException;

public class App {
	
	public static final String EXIT = "E";
	public static final String DISCONNECT = "D";
	public static final String RETURN = "R";
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("==================================\n"
				 		 + "          PassManager\n"
				 		 + "==================================");
		System.out.println("-------- Navigation Options : --------");
		System.out.println("E : exit the PassManager.");
		System.out.println("D : Disconnect.");
		System.out.println("R : Return.");
		
		loginInscription(sc);
		passManagement(sc);
		sc.close();
	}
	
	public static void loginInscription(Scanner sc)
	{
		System.out.println("1 ==> Create Account : ");
		System.out.println("2 ==> Loggin : ");
		String userInput = sc.next();
		if(userInput.equals(EXIT))
			System.exit(0);
		else
		{
			try {
				switch (Integer.parseInt(userInput)) {
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
			} catch (NumberFormatException e) {
				System.out.println("wrong input !!!!");
				loginInscription(sc);
			}
			
		}
	}
	
	public static void passManagement(Scanner sc)
	{
		System.out.println("1 ==> Passwords List : ");
		System.out.println("2 ==> New password : ");
		String userInput = sc.next();
		if(userInput.equals(EXIT))
			System.exit(0);
		else if(userInput.equals(DISCONNECT))
		{
			Authentification.setAccount(null);
			PassManagement.setMasterPass(null);
			loginInscription(sc);
			passManagement(sc);
		}
		else
		{
			switch (Integer.parseInt(userInput)) {
			case 1:
				passwordsList(sc);
				break;
			case 2:
				addNewPass(sc);
				break;
			default:
				break;
			}
		}
	}

	public static void passwordsList(Scanner sc) {
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
		if(choice.equals(EXIT))
			System.exit(0);
		else if(choice.equals(DISCONNECT))
		{
			Authentification.setAccount(null);
			PassManagement.setMasterPass(null);
			loginInscription(sc);
			passManagement(sc);
		}
		else if(choice.equals(RETURN))
		{
			passManagement(sc);
		}
		else
		{
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
			case 2:
				System.out.print("enter your Master Password : ");
				while(true)
					try {
						if(!Authentification.getAccount().getMasterPass().equals(DatatypeConverter.printHexBinary(
							Utilities.hash256(PassManagement.createMasterPass(sc.next())))))
							System.out.print("wrong password !! Re-Enter the password : ");
						else
							break;
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				try {
					Authentification.getAccount().getPassword().get(index)
							.setValue(PassManagement.cryptPass(PassManagement.generatePassword(48)));
					DataController.update(Authentification.getAccount());
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
				} catch (InvalidLengthException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 3:
				try {

					Authentification.getAccount().getPassword().remove(index);
					DataController.update(Authentification.getAccount());
				} catch (IndexOutOfBoundsException e) {
					System.out.println(index + " : no password with the specified index !!!");
					passwordsList(sc);
				}
				break;
			default:
				break;
			}
		}
		passwordsList(sc);
	}
	
	public static void addNewPass(Scanner sc)
	{
		System.out.println("Describe the password : ");
		String userInput = sc.next();
		if(userInput.equals(EXIT))
			System.exit(0);
		else if(userInput.equals(DISCONNECT))
		{
			Authentification.setAccount(null);
			PassManagement.setMasterPass(null);
			loginInscription(sc);
			passManagement(sc);
		}
		else if(userInput.equals(RETURN))
		{
			passManagement(sc);
		}
		else
		{
			Password password = new Password();
			password.setDescription(userInput);
			try {
				password.setValue(PassManagement.cryptPass(PassManagement.generatePassword(48)));
				Authentification.getAccount().getPassword().add(password);
				DataController.update(Authentification.getAccount());
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
			} catch (InvalidLengthException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		passwordsList(sc);
	}
	
	public static Account authentification(String userId, String userEnteredMasterPass) {
		Account account = null;
		try {
			account = Authentification.authentifyAccount(userId, userEnteredMasterPass);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return account;
	}
}
