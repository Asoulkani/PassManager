package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import beans.Account;

/*=================================================
 * 
 * this class is where all the data access object
 * are created. it reads from a config file to know
 * which type of persistence is used 
 * example if the persistence type is XML in 
 * the config file we will find a line with 
 * the following :
 * 		DAO = XML 
 * 
 =================================================*/

public class Factory {

	public static final String DAO_FROM_XML = "XML";
	
	public static DataAccesObject<Account> daoAccountGetInstance()
	{
		
		try {
			File config = new File("config");
			Reader fileReader;
			fileReader = new FileReader(config);
			BufferedReader bufReader = new BufferedReader(fileReader);
			String line = bufReader.readLine();
			while(line != null)
			{
				if(line.substring(0,3).equals("DAO"))
				{
					if(line.substring(6).equals(DAO_FROM_XML))
					{
						bufReader.close();
						DaoXmlAccount daoXmlAccount = new DaoXmlAccount();
						daoXmlAccount.init();
						return daoXmlAccount; 
					}
					//else if() new type of persistence
				}
			}
			bufReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
