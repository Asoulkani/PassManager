package security;

import java.io.File;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import beans.Account;
import beans.AccountDataBase;

public class Authentification {
	
	public static AccountDataBase accountDataBase = new AccountDataBase();
	
	public static void initAccountDataBase() throws JAXBException
	{
		JAXBContext context = JAXBContext.newInstance(AccountDataBase.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		File file = new File("Data/MasterPass.xml");
		accountDataBase = (AccountDataBase) unmarshaller.unmarshal(file);
	}
	
	public static boolean createAccount(String userId, String userEnteredMasterPass) throws NoSuchAlgorithmException
	{
		Account account = new Account();
		account.setUserId(DatatypeConverter.printHexBinary(Utilities.hash256(userId)));
		account.setMasterPass(DatatypeConverter.printHexBinary(Utilities.hash256(
				PassManagement.createMasterPass(userEnteredMasterPass))));
		accountDataBase.getAccount().add(account);
		return true;
	}
	
	public static Account authentifyAccount(String userId,String userEnteredMasterPass) throws NoSuchAlgorithmException
	{
		Account account = null;
		for (Account ac : accountDataBase.getAccount()) {
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
