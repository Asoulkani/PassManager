package business;

import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import beans.Account;
import dao.DaoXmlAccount;

public class Authentification {
	private static DaoXmlAccount dao = new DaoXmlAccount();
	public static boolean createAccount(String userId, String userEnteredMasterPass) throws NoSuchAlgorithmException
	{
		Account account = new Account();
		account.setUserId(DatatypeConverter.printHexBinary(Utilities.hash256(userId)));
		account.setMasterPass(DatatypeConverter.printHexBinary(Utilities.hash256(
				PassManagement.createMasterPass(userEnteredMasterPass))));
		dao.save(account);
		return true;
	}
	
	public static Account authentifyAccount(String userId,String userEnteredMasterPass) throws NoSuchAlgorithmException
	{
		Account account = null;
		for (Account ac : dao.getAll()) {
			System.out.println(ac.getUserId() + " | "+DatatypeConverter.printHexBinary(Utilities.hash256(userId)));
			if(ac.getUserId().equals(DatatypeConverter.printHexBinary(Utilities.hash256(userId))))
			{
				System.out.println("userid correct");
				account = ac;
				break;
			}
		}
		if(account != null)
		{
			System.out.println(account.getMasterPass() + " | " +
				PassManagement.createMasterPass(userEnteredMasterPass));
			if(account.getMasterPass().equals(DatatypeConverter.printHexBinary(Utilities.hash256(
				PassManagement.createMasterPass(userEnteredMasterPass)))))
			{
				System.out.println("pass correct");
				return account;
			}
		}
		return null;
	}

}
