package business;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

import exception.InvalidLengthException;

public class PassManagement {

	private static SecretKey masterPass;

	public static SecretKey getMasterPass() {
		return masterPass;
	}

	public static void setMasterPass(SecretKey masterPass) {
		PassManagement.masterPass = masterPass;
	}

	/*
	 * =================================================
	 * 
	 * Padding methode 1 : padding value
	 * 
	 * =================================================
	 */

	public static final String PADDING_1 = "PADDINGPADDINGPA";

	/*
	 * =================================================
	 * 
	 * Padding methode 2 : padding value
	 * 
	 * =================================================
	 */

	public static final String PADDING_2_L = "FFFFFFFF";
	public static final String PADDING_2_R = "F0F0F0F0";

	/*
	 * =================================================
	 * 
	 * padding the given String with padding methode 1
	 * 
	 * =================================================
	 */

	public static String applyPADDING_1(StringBuilder data) {
		data.append(PADDING_1.substring(data.length(), 16));
		return data.toString();
	}

	/*
	 * =================================================
	 * 
	 * padding the given String with padding methode 2
	 * 
	 * =================================================
	 */

	public static String applyingPADDING_2(StringBuilder data) {
		data.append(PADDING_2_R);
		data.insert(0, PADDING_2_L);
		return data.toString();
	}

	/*
	 * =================================================
	 * 
	 * Create the master pass from the value provided by the user
	 * 
	 * =================================================
	 */

	public static String createMasterPass(String userEnteredMasterPass) {
		String paddedMasterPass = applyPADDING_1(new StringBuilder(userEnteredMasterPass));
		String hexMasterPass = Utilities.convertStringToHex(paddedMasterPass);
		byte[] xored = Utilities.xor(DatatypeConverter.parseHexBinary(hexMasterPass.substring(0, 16)),
				DatatypeConverter.parseHexBinary(hexMasterPass.substring(16)));
		String paddedXored = applyingPADDING_2(new StringBuilder(DatatypeConverter.printHexBinary(xored)));
		return paddedXored;
	}

	/*
	 * =================================================
	 * 
	 * generate a random password, the length must be 
	 * a multiple of 16
	 * 
	 * =================================================
	 */

	public static String generatePassword(int length) throws InvalidLengthException {
		if(length % 16 == 0)
		{
			StringBuilder pass = new StringBuilder();
			Random random = new Random();
			for (int i = 0; i < length; i++) {
				pass.append((char) (random.nextInt(125 - 32)+32));  //125 the ascii code of } and 32 is the code of space
			}
			return pass.toString();
		}
		else
			throw new InvalidLengthException();
	}
	
	/*
	 * =================================================
	 * 
	 * encrypt password using masterPass
	 * 
	 * =================================================
	 */
	
	public static String cryptPass(String clearPass) throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

		return DatatypeConverter.printHexBinary(Cryptographie
				.crypteAES(DatatypeConverter.parseHexBinary(Utilities.convertStringToHex(clearPass)), masterPass));

	}
	
	/*
	 * =================================================
	 * 
	 * decrypt a password encrypted using masterPass
	 * 
	 * =================================================
	 */
	
	public static String deCryptPass(String cryptedPass) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
	{
		return Utilities.convertHexToString(DatatypeConverter
				.printHexBinary(Cryptographie.decrypte(DatatypeConverter.parseHexBinary(cryptedPass), masterPass)));
	}
	
	/*
	 * =================================================
	 * 
	 * decrypt a password encrypted using masterPass 
	 * then copy it to clipboard.
	 * 
	 * =================================================
	 */
	
	public static void copyPassToClipBoard(String cryptedPass) throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		StringSelection stringSelection = new StringSelection(deCryptPass(cryptedPass));
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}

}
