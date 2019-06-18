package com.photo.bas.core.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;

/**
 * 
 * @author FengYu
 * 
 */
public class FileUtils {
	public static boolean copyBinaryFile(String sourceFilePath,
			String destinationFilePath) {
		BufferedInputStream inBuffer = null;
		BufferedOutputStream outBuffer = null;
		try {
			byte[] data = new byte[4096];
			int len = 0;
			File unitFileName = new File(sourceFilePath);
			inBuffer = new BufferedInputStream(
					new FileInputStream(unitFileName));
			outBuffer = new BufferedOutputStream(new FileOutputStream(new File(
					destinationFilePath)));
			while (len != -1) {
				len = inBuffer.read(data);
				if (len != -1)
					outBuffer.write(data, 0, len);
			}
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			try {
				inBuffer.close();
				outBuffer.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public static boolean copyFile(String sourceFilePath,
			String destinationFilePath) {
		BufferedReader inBuffer = null;
		BufferedWriter outBuffer = null;

		try {
			String buffer;
			File unitFileName = new File(sourceFilePath);
			File destFile = new File(destinationFilePath);
			if (destFile != null && !destFile.getParentFile().exists())
				destFile.getParentFile().mkdirs();
			inBuffer = new BufferedReader(new FileReader(unitFileName));
			outBuffer = new BufferedWriter(new FileWriter(destFile));
			while (((buffer = inBuffer.readLine()) != null)
					&& ((buffer = inBuffer.readLine()).length() != 0)) {
				outBuffer.write(buffer);
				outBuffer.write("\r\n");
			}
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			try {
				inBuffer.close();
				outBuffer.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public static void copySimpleFile(String sourceFilePath,
			String destinationFilePath) {
		FileChannel srcChannel = null;
		FileChannel dstChannel = null;
		try {
			File unitFileName = new File(sourceFilePath);
			File destFile = new File(destinationFilePath);
			if (unitFileName.exists()) {
				if (destFile != null && !destFile.getParentFile().exists()) {
					destFile.getParentFile().mkdirs();
				}
				srcChannel = new FileInputStream(sourceFilePath).getChannel();
				dstChannel = new FileOutputStream(destinationFilePath)
						.getChannel();
				dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

			}
		} catch (IOException e) {
		} finally {
			try {
				srcChannel.close();
				dstChannel.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	public static boolean deleteDirContent(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				if (new File(dir, children[i]).isDirectory())
					deleteDirContent(new File(dir, children[i]));
				boolean success = new File(dir, children[i]).delete();
				if (!success)
					return false;
			}
		}
		return dir.delete();
	}

	static public String formatFileSize(double fl) {
		double fileLength = (float) fl / (float) 1024;
		DecimalFormat df = new DecimalFormat("#,##0.00");
		String displayLength = "";
		if (fileLength > 1024)
			displayLength = ""
					+ Double.parseDouble(df.format((float) fileLength
							/ (float) 1024)) + " MB";
		else
			displayLength = "" + Double.parseDouble(df.format(fileLength))
					+ " KB";

		return displayLength;
	}

	public static String getFileExtension(File file) {
		if (file == null)
			return "";
		return getFileExtension(file.getName());
	}

	public static String getFileExtension(String fileName) {
		if (Strings.isEmpty(fileName)) {
			return Strings.EMPTY;
		}
		int lastDot = fileName.lastIndexOf(".");
		if (lastDot == -1)
			return "";

		return fileName.substring(lastDot + 1);
	}

	public static boolean moveSimpleFile(String sourceFilePath,
			String destinationFilePath) {

		try {
			File unitFileName = new File(sourceFilePath);
			File destFile = new File(destinationFilePath);
			if (unitFileName.exists()) {
				if (destFile != null && !destFile.getParentFile().exists()) {
					destFile.getParentFile().mkdirs();
				}
				unitFileName.renameTo(destFile);
			}
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public static boolean writeToFile(String filePath, String fileContent) {
		BufferedWriter outBuffer = null;
		try {
			File destFile = new File(filePath);
			if (destFile != null && !destFile.getParentFile().exists())
				destFile.getParentFile().mkdirs();
			outBuffer = new BufferedWriter(new FileWriter(destFile));
			outBuffer.write(fileContent);
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			try {
				outBuffer.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private FileUtils() {
	}
}
