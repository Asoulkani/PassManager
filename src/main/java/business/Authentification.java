package business;

import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import beans.Account;

public class Authentification {
	
	private static Account logedInAccount;

	public static Account getAccount() {
		return logedInAccount;
	}

	public static void setAccount(Account account) {
		Authentification.logedInAccount = account;
	}
	
	
	/*=================================================
	 * 
	 * New account 
	 * 1. hashing the userID
	 * 2. creating the master pass from the pass 
	 * 		provided from the user
	 * 3. hashing the created MasterPass
	 * 4. saving the account (we do not specify what 
	 * type of persistence is used at this level)
	 * 
	=================================================*/
	
	public static Account createAccount(String userId, String userEnteredMasterPass) throws NoSuchAlgorithmException
	{
		Account account = new Account();
		account.setUserId(DatatypeConverter.printHexBinary(Utilities.hash256(userId)));
		account.setMasterPass(DatatypeConverter.printHexBinary(Utilities.hash256(
				PassManagement.createMasterPass(userEnteredMasterPass))));
		DataController.save(account);
		return account;
	}
	
	/*=================================================
	 * 
	 * authenticate an account 
	 * 1. get all account(without specifying the source(XML, ...)
	 * 2. hashing the userID and search for it in the 
	 * 		account list.
	 * 3. if a correspondence is found 
	 * 3.1 we create a masterPass from the pass provided
	 * 		by the user
	 * 3.2 hashing the result and then compare it with
	 * 		the correspondence pass.
	 * 4 return the account found if all the condition
	 * 	are validated, return null if not
	=================================================*/
	
	public static Account authentifyAccount(String userId,String userEnteredMasterPass) throws NoSuchAlgorithmException
	{
		Account account = null;
		for (Account ac : DataController.getAll()) {
			if(ac.getUserId().equals(DatatypeConverter.printHexBinary(Utilities.hash256(userId))))
			{
				account = ac;
				break;
			}
		}
		if(account != null)
		{
			if(account.getMasterPass().equals(DatatypeConverter.printHexBinary(Utilities.hash256(
				PassManagement.createMasterPass(userEnteredMasterPass)))))
				return account;
		}
		return null;
	}

}
