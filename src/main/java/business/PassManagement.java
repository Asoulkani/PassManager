package business;

import javax.xml.bind.DatatypeConverter;

public class PassManagement {

	/*=================================================
	 * 
	 * Padding methode 1 : padding value
	 * 
	 =================================================*/
	
	public static final String PADDING_1 = "PADDINGPADDINGPA"; 
	
	/*=================================================
	 * 
	 * Padding methode 2 : padding value
	 * 
	 =================================================*/
	
	public static final String PADDING_2_L = "FFFFFFFF";
	public static final String PADDING_2_R = "F0F0F0F0";
	
	/*=================================================
	 * 
	 * padding the given String with padding methode 1
	 * 
	 =================================================*/
	
	public static String applyPADDING_1(StringBuilder data) {
		data.append(PADDING_1.substring(data.length(), 16));
		return data.toString();
	}
	
	/*=================================================
	 * 
	 * padding the given String with padding methode 2
	 * 
	 =================================================*/
	
	public static String applyingPADDING_2(StringBuilder data)
	{
		data.append(PADDING_2_R);
		data.insert(0, PADDING_2_L);
		return data.toString();
	}
	
	/*=================================================
	 * 
	 * Create the master pass from the value provided 
	 * by the user
	 * 
	 =================================================*/
	
	public static String createMasterPass(String userEnteredMasterPass) {
		String paddedMasterPass = applyPADDING_1(new StringBuilder(userEnteredMasterPass));
		String hexMasterPass = Utilities.convertStringToHex(paddedMasterPass);
		byte[] xored = Utilities.xor(DatatypeConverter.parseHexBinary(hexMasterPass.substring(0, 16)),
				DatatypeConverter.parseHexBinary(hexMasterPass.substring(16)));
		String paddedXored = applyingPADDING_2(new StringBuilder(DatatypeConverter.printHexBinary(xored)));
		return paddedXored;
	}
	
	/*=================================================
	 * 
	 * generate a random password at least 48 character
	 * 
	 =================================================*/
	
	public static String generatePassword(int lentgh)
	{
		// to be implemented
		return null;
	}

}
