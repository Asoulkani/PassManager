package security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Utilities {

	/*
	 * Xor function
	 */
	public static byte[] xor(byte[] b1, byte[] b2) {
		byte[] xor = new byte[b1.length];
		for (int i = 0; i < b1.length; i++) {
			xor[i] = (byte) ((int) b1[i] ^ (int) b2[i]);
		}
		return xor;
	}

	/*
	 * application de la parite ODD sur un array of bytes
	 */
	public static void adjustParity(byte[] bytes) {
		for (int i = 0; i < bytes.length; i++) {
			int b = bytes[i];
			bytes[i] = (byte) ((b & 0xfe)
					| ((((b >> 1) ^ (b >> 2) ^ (b >> 3) ^ (b >> 4) ^ (b >> 5) ^ (b >> 6) ^ (b >> 7)) ^ 0x01) & 0x01));
		}
	}

	/*
	 * verification de parite
	 */
	public static boolean isParityAdjusted(byte[] bytes) {
		byte[] correct = (byte[]) bytes.clone();
		adjustParity(correct);
		return Arrays.equals(bytes, correct);
	}
	
	public static byte[] hash256(String data) throws NoSuchAlgorithmException
	{
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		return digest.digest(data.getBytes());
	}
	
	public static String convertStringToHex(String str) {

		char[] chars = str.toCharArray();
		StringBuilder hex = new StringBuilder();
		for (char ch : chars) {
			if (Integer.toHexString((int) ch).length() < 2)
				hex.append("0" + Integer.toHexString((int) ch));
			else
				hex.append(Integer.toHexString((int) ch));
		}

		return hex.toString().toUpperCase();
	}

	public static String convertHexToString(String hex) {

		StringBuilder output = new StringBuilder("");

		for (int i = 0; i < hex.length(); i += 2) {
			String str = hex.substring(i, i + 2);
			output.append((char) Integer.parseInt(str, 16));
		}

		return output.toString();
	}
	
}
