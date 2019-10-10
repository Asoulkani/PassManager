package dao;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import beans.Account;
import beans.AccountDataBase;
import beans.Password;

public class DaoXmlAccount implements DataAccesObject<Account> {
	
	private static AccountDataBase accountDataBase = new AccountDataBase();
	
	private static void refreshDataBaseXmlFile() throws JAXBException
	{
		JAXBContext context = JAXBContext.newInstance(AccountDataBase.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(accountDataBase, new File("Data/MasterPass.xml"));
	}
	
	@Override
	public List<Account> getAll() {
		return accountDataBase.getAccount();
	}

	@Override
	public void save(Account t) {
		accountDataBase.getAccount().add(t);
		try {
			refreshDataBaseXmlFile();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(Account t) {
		for (Account account : accountDataBase.getAccount()) {
			if(account.getUserId().equals(t.getUserId()))
			{
				accountDataBase.getAccount().set(accountDataBase.getAccount().indexOf(account), t);
				break;
			}
		}
		try {
			refreshDataBaseXmlFile();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Account t) {
		for (Account account : accountDataBase.getAccount()) {
			if(account.getUserId().equals(t.getUserId()))
			{
				accountDataBase.getAccount().remove(account);
				break;
			}
		}
	}

	@Override
	public void init()
	{
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(AccountDataBase.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			File file = new File("Data/MasterPass.xml");
			accountDataBase = (AccountDataBase) unmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void addPasswordToExistantAccount(Account account,Password pass) {
		account.getPassword().add(pass);
		this.update(account);
	}

}
