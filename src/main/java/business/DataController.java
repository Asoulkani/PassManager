package business;

import java.util.List;

import beans.Account;
import dao.DataAccesObject;
import dao.Factory;

public class DataController {

	/*=================================================
	 * 
	 * initialization of data access object 
	 * at this level the object is initialized without 
	 * knowing which persistence method is 
	 * used(file, XML, ORACL, MYSQL, ...)
	 * 
	 =================================================*/
	
	private static DataAccesObject<Account> dao = Factory.daoAccountGetInstance();
	
	public static void save(Account account) { dao.save(account);}
	public static List<Account> getAll() { return dao.getAll();}
	
}
