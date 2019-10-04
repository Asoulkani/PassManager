package dao;

import java.io.File;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import beans.Account;
import beans.AccountDataBase;

public class DaoXmlAccount implements DataAccesObject<Account> {
	
	private static AccountDataBase accountDataBase = new AccountDataBase();
	
	@Override
	public Optional<Account> get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Account> getAll() {
		return accountDataBase.getAccount();
	}

	@Override
	public void save(Account t) {
		accountDataBase.getAccount().add(t);
	}

	@Override
	public void update(Account t, String[] params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Account t) {
		// TODO Auto-generated method stub
		
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

}
