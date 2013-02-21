package ccw.hook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Utils {

	public static String readFile(File file) {
		FileInputStream fis = null;
		BufferedReader in = null;
		try {
			fis = new FileInputStream(file);
		    in = new BufferedReader(new InputStreamReader(fis, "UTF-8"));

		    String inputLine;
		    StringBuilder ret = new StringBuilder();
		    while ((inputLine = in.readLine()) != null) {
		    	ret.append(inputLine);
		    }
		    return ret.toString();
		} catch (IOException e) {
		    e.printStackTrace();
		    return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (in != null) { try { in.close(); } catch (IOException e) {} }
			if (fis != null) { try { fis.close(); } catch (IOException e) {} }
		}

	}
	public static List<String> readFileLines(File file) {
		
		FileInputStream fis = null;
		BufferedReader in = null;
		try {
			fis = new FileInputStream(file);
		    in = new BufferedReader(new InputStreamReader(fis, "UTF-8"));

		    List<String> ret = new ArrayList<String>();
		    String inputLine;
		    while ((inputLine = in.readLine()) != null) {
		    	ret.add(inputLine);
		    }
		    return ret;
		} catch (IOException e) {
		    e.printStackTrace();
		    return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (in != null) { try { in.close(); } catch (IOException e) {} }
			if (fis != null) { try { fis.close(); } catch (IOException e) {} }
		}
	}

	public static List<String> readFileLines(InputStream is) {
		
		BufferedReader in = null;
		try {
		    in = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		    List<String> ret = new ArrayList<String>();
		    String inputLine;
		    while ((inputLine = in.readLine()) != null) {
		    	ret.add(inputLine);
		    }
		    return ret;
		} catch (IOException e) {
		    e.printStackTrace();
		    return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (in != null) { try { in.close(); } catch (IOException e) {} }
			if (is != null) { try { is.close(); } catch (IOException e) {} }
		}
	}
	
	public static String readFile(InputStream is) {
		
		BufferedReader in = null;
		try {
		    in = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		    StringBuilder ret = new StringBuilder();
		    String inputLine;
		    while ((inputLine = in.readLine()) != null) {
		    	ret.append(inputLine);
		    }
		    return ret.toString();
		} catch (IOException e) {
		    e.printStackTrace();
		    return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (in != null) { try { in.close(); } catch (IOException e) {} }
			if (is != null) { try { is.close(); } catch (IOException e) {} }
		}
	}
}
