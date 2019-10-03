package security;

public class Authentification {
	
	public static boolean createAccount(String userId, String userEnteredMasterPass)
	{
		//hash(userId)
		//padd(userEnteredMasterPass) padding with hardCoded Padd => 16 ASCII character
		//convertAsciiToHex(userEnteredMasterPass)
		//blockL = 16 left hex
		//blockR = 16 right hex
		//xor(blockL,blockR)
		//paddAfterXor(xor) padding with hardCoded padd => 32 hex character
		//oddParity
		//hash result then save to MasterPass.xml
		return true;
	}
	
	public static boolean authentifyAccount(String userId,String userEnteredMasterPass)
	{
		//hash(userId) and search for it in the database
		//padd(userEnteredMasterPass) padding with hardCoded Padd => 16 ASCII character
		//convertAsciiToHex(userEnteredMasterPass)
		//blockL = 16 left hex
		//blockR = 16 right hex
		//xor(blockL,blockR)
		//paddAfterXor(xor) padding with hardCoded padd => 32 hex character
		//oddParity
		//hash result then compare it with userId hashed masterPass
		return false;
	}

}
