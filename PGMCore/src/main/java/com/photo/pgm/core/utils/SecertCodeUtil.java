package com.photo.pgm.core.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author FengYu
 *
 */
public class SecertCodeUtil {
	private static List<String> source = new ArrayList<String>();
	//private static Integer[] weights = {1,3,2,4,6,5,9}; // this one should be changed.
	private static String[] alphabets = {"0","1", "2", "3", "4", "5", "6", "7", "8", "9", 
			"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", 
			"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
//			"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
	
	
	
	private static List<String> getSource() {
		if(source.size() > 0) return source;
		return Arrays.asList(alphabets);
	}
	
	public static String getSecertCode(int length) {
		List<String> list = getSource();
		Collections.shuffle(list);
		StringBuffer sf = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sf.append(list.get(i));  
		}
		return appendCheckSumDigit(sf.toString());
	}
	
	private static String appendCheckSumDigit(String s) {
		//TODO
		return s;
	}
	
	public static void main (String args[]) {
		for (int i = 0; i < 100; i++) {
			System.out.println(getSecertCode(8));
		}
	}
	
}
