import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

import org.eclipse.jdt.core.dom.*;

public class DirectoryParser {
	
	int counter = 0;
	TypeDeclaration tempNode;
	
	/**
	 * Iterates through all files in directory 
	 * 
	 * @param directoryPath - location to directory for parsing
	 * @return String - String of java code in directory
	 * @throws IOException - If invalid directoryPath
	 */
	public static String convertFilesToString(String directoryPath)throws IOException, FileNotFoundException {
		String target_dir = directoryPath;
		StringBuilder sb = new StringBuilder();
		File dir = new File(target_dir);
		File[] files = dir.listFiles();
		
		for (File f : files) {
			
			if(f.isFile() && f.getName().toLowerCase().endsWith(".java")) {
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
	
	public int getClassDeclarationCount(String pathname) throws IOException {
		char[] directoryContent = convertFilesToString(pathname).toCharArray();
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(directoryContent);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		cu.accept(new ASTVisitor() {
			
			public boolean visit(TypeDeclaration node) {
				counter++;
				return false;
			}
		});
		return counter;
	}
	
	public int parseSpecifiedType(int type, String pathname) throws IOException {
		if(type == 1) {
			System.out.println("\nClass Type." + "Declarations: " + this.getClassDeclarationCount(pathname) + 
					" References: " + "N/A");
		}else if (type == 2) {
			System.out.println("Interface Declaration not implemented yet");
		}else if (type == 3) {
			System.out.println("Enum Declaration not implemented yet");
		}else {
			System.out.println("Annotation Type Declaration not implemented yet");
		}
		return 0;
	}
	
	
	public static void main(String[] args) throws IOException {
		DirectoryParser dp = new DirectoryParser();
		
		Scanner kb = new Scanner(System.in);
		System.out.print("Enter directory pathname: ");
		String pathname = kb.next();
		System.out.print("Count (1)Class Declarations, \n      "
				+ "(2)Interface Declarations, \n      "
				+ "(3)Enum Declaration, or \n      "
				+ "(4) Annotation Type Declaration: ");
		int specifiedType = kb.nextInt();
		dp.parseSpecifiedType(specifiedType, pathname);
	}
}
