package security;

import javax.xml.bind.DatatypeConverter;

public class PassManagement {

	public static final String PADDING_1 = "PADDINGPADDINGPA";
	public static final String PADDING_2_L = "FFFFFFFF";
	public static final String PADDING_2_R = "F0F0F0F0";

	public static String applyPADDING_1(StringBuilder data) {
		data.append(PADDING_1.substring(data.length(), 16));
		return data.toString();
	}

	public static String applyingPADDING_2(StringBuilder data)
	{
		data.append(PADDING_2_R);
		data.insert(0, PADDING_2_L);
		return data.toString();
	}
	
	public static String createMasterPass(String userEnteredMasterPass) {
		String paddedMasterPass = applyPADDING_1(new StringBuilder(userEnteredMasterPass));
		String hexMasterPass = Utilities.convertStringToHex(paddedMasterPass);
		byte[] xored = Utilities.xor(DatatypeConverter.parseHexBinary(hexMasterPass.substring(0, 16)),
				DatatypeConverter.parseHexBinary(hexMasterPass.substring(16)));
		String paddedXored = applyingPADDING_2(new StringBuilder(DatatypeConverter.printHexBinary(xored)));
		return paddedXored;
	}

}
