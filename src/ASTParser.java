import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

import org.eclipse.jdt.core.dom.*;



public class ASTParser {
	
	public static String convertFilesToString(String directoryPath)throws IOException {
		String target_dir = directoryPath;
		StringBuilder sb = new StringBuilder();
		File dir = new File(target_dir);
		File[] files = dir.listFiles();
		
		for (File f : files) {
			if(f.isFile()) {
				BufferedReader inputStream = null;
				
				try {
					inputStream = new BufferedReader(new FileReader(f));
					String line;
					
					while((line = inputStream.readLine()) != null) {
						sb.append(line);
					}
				}
				finally {
					if(inputStream != null) {
						inputStream.close();
					}
				}
			}
		}
		return sb.toString();
	}
	
	public static void main(String[] args) throws IOException {
		Scanner kb = new Scanner(System.in);
		System.out.print("Enter directory pathname: ");
	}
}
