import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

import org.eclipse.jdt.core.dom.*;





public class DirectoryParser {
	
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
						sb.append(System.lineSeparator());
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
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		System.out.print("Enter directory pathname: ");
		String pathname = kb.next();
		System.out.print("Enter name of Java type to count (Ex: 'SIMPLE_NAME', 'TRY_STATEMENT'); ");
		String javaType = kb.next();
		
		
	}
}
