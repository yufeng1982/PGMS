/**
 * 
 */
package com.photo.bas.core.utils;

import java.util.Random;
import java.util.UUID;

/**
 * @author FengYu
 *
 */
public class GenerateRandomChar {

	private static final String CHAR = "_abcdefghigklmnopkrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789";

	public static String getRandomString(int length) {
		Random random = new Random();
		StringBuffer sf = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(CHAR.length());
			sf.append(CHAR.charAt(number));
		}
		return sf.toString();
	}
	
	public static void main (String args[]) {
		System.out.println(getRandomString(8));
		String uuid = UUID.randomUUID().toString();
		System.out.println(uuid);
	}
}
