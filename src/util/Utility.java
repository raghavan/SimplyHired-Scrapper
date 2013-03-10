package util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

public class Utility {

	public static String convertInputStreamAsString(InputStream stream) {
		BufferedReader rd = new BufferedReader(new InputStreamReader(stream));
		String line = new String();
		StringBuilder content = new StringBuilder();
		try {
			while ((line = rd.readLine()) != null) {
				content.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content.toString();
	}

	public static void showInputStream(InputStream stream) {
		BufferedReader rd = new BufferedReader(new InputStreamReader(stream));
		String line = new String();
		try {
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public static File writeToFile(Set<String> userNames, String path) {
		FileWriter fw = null;
		File file = new File(path);
		try {
			fw = new FileWriter(file);
			for (String userName : userNames) {
				fw.write(userName+",");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return file;
	}
	
	public static File writeToFile(String content, String path) {
		FileWriter fw = null;
		File file = new File(path);
		try {
			fw = new FileWriter(file);
			fw.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return file;
	}
	
	public static File appendToFile(String content, String path) {
		FileWriter fw = null;
		File file = new File(path);
		try {
			fw = new FileWriter(file,true);
			fw.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return file;
	}

	public static String getFileContents(File file,boolean todisplay) {
		StringBuffer content = new StringBuffer();
		FileInputStream fstream = null;
		try {
			fstream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		try {
			while ((strLine = br.readLine()) != null) {
				content.append(strLine);
				if(todisplay)
					System.out.println(strLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content.toString();
	}


}
