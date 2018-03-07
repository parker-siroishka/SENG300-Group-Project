import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

import org.eclipse.jdt.core.dom.*;

public class DirectoryParser {
	
	int classDeclarationCounter = 0;
	int annotationDeclarationCounter = 0;
	int interfaceDeclarationCounter = 0;
	int enumDeclarationCounter = 0;
	
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
	
	public int getClassDeclarationCount(String pathname, ASTParser parser) throws IOException {
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		cu.accept(new ASTVisitor() {
			
			public boolean visit(TypeDeclaration node) {
				classDeclarationCounter++;
				//ITypeBinding binding = node.resolveBinding();
				//System.out.println("Binding: "+binding);
				return true;
			}
		});
		return classDeclarationCounter;
	}
	
	public int getEnumDeclarationCount(String pathname, ASTParser parser) throws IOException {
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		cu.accept(new ASTVisitor() {
			
			public boolean visit(EnumConstantDeclaration node) {
				enumDeclarationCounter++;
				return true;
			}
		});
		return enumDeclarationCounter;
	}
	
	
	public int getAnnotationDeclarationCount(String pathname, ASTParser parser) throws IOException {
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		cu.accept(new ASTVisitor() {
			
			public boolean visit(MarkerAnnotation node) {
				annotationDeclarationCounter++;
				return false;
			}
		});
		return annotationDeclarationCounter;
	}
	
	public int parseSpecifiedType(int type, String pathname, ASTParser parser) throws IOException {
		if(type == 1) {
			System.out.println("\nClass Type." + "Declarations: " + this.getClassDeclarationCount(pathname, parser) + 
					" References: " + "N/A");
		}else if (type == 2) {
			System.out.println("Interface Declaration not implemented yet");
		}else if (type == 3) {
			System.out.println("\nEnumeration Type." + "Declarations: " + this.getEnumDeclarationCount(pathname, parser) + 
					" References: " + "N/A");
		}else {
			System.out.println("\nAnnotation Type." + "Declarations: " + this.getAnnotationDeclarationCount(pathname, parser) + 
					" References: " + "N/A");
		}
		return 0;
	}
	
	public String getDirectoryPathname(Scanner kb) {
		System.out.print("Enter directory pathname: ");
		String tempString = kb.next();
		
		return tempString;
	}
	public int getSpecifiedType(Scanner kb) {
		
		System.out.print("Count (1)Class Declarations, \n      "
				+ "(2)Interface Declarations, \n      "
				+ "(3)Enum Declaration, or \n      "
				+ "(4) Annotation Type Declaration: ");
		int tempInt = kb.nextInt();
		
		return tempInt;
	}
	
	private static ASTParser buildParser(char[] directoryContent, String pathname) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(directoryContent);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		String[] classpathEntries = new String[] {pathname};
	    String[] sourcepathEntries = new String[] {pathname};
		parser.setEnvironment(classpathEntries, sourcepathEntries, null, false);
		
		return parser;
	}
	
	
	public static void main(String[] args) throws IOException {
		
		DirectoryParser dp = new DirectoryParser();
		Scanner kb = new Scanner(System.in);
		
		String pathname = dp.getDirectoryPathname(kb);
		int specifiedType = dp.getSpecifiedType(kb);
		
		char[] directoryContent = convertFilesToString(pathname).toCharArray();
		ASTParser parser = buildParser(directoryContent, pathname);
		
		dp.parseSpecifiedType(specifiedType, pathname, parser);
		kb.close();
	}
}
