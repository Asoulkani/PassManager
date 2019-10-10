package dao;

import java.util.List;

import beans.Account;
import beans.Password;

public interface DataAccesObject<T> {

	    List<T> getAll();

	    void save(T t);

	    void update(T t);

	    void delete(T t);
	    
	    void init();
	    
	    void addPasswordToExistantAccount(Account account, Password pass);

}
