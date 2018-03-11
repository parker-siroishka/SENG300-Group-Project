import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;

public class DirectoryParser {
	
	int classDeclarationCounter = 0;
	int annotationDeclarationCounter = 0;
	int interfaceDeclarationCounter = 0;
	int enumDeclarationCounter = 0;
	
	/**
	 * Iterates through all files in directory and concatenates to one string
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
	
	public void calcClassAndInterfaceCount(String pathname, ASTParser parser) throws IOException {
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		//System.out.println(cu.getAST().hasResolvedBindings());
		cu.accept(new ASTVisitor() {
			
			public boolean visit(TypeDeclaration node) {
				if (node.isInterface()) {
					interfaceDeclarationCounter++;
				}else {classDeclarationCounter++;}
					
				
				//ITypeBinding binding = node.resolveBinding();
				//System.out.println("Binding: "+binding);
				return true;
			}
		});
		
	}
	
	public void calcEnumDeclarationCount(String pathname, ASTParser parser) throws IOException {
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		cu.accept(new ASTVisitor() {
			
			public boolean visit(EnumConstantDeclaration node) {
				enumDeclarationCounter++;
				return true;
			}
		});
	}
	
	
	public void calcAnnotationDeclarationCount(String pathname, ASTParser parser) throws IOException {
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		cu.accept(new ASTVisitor() {
			
			public boolean visit(MarkerAnnotation node) {
				annotationDeclarationCounter++;
				return false;
			}
		});
	}
	

	
	public void parseType(String pathname, ASTParser parser, String type) throws IOException {
		if (type.equals("Class")) {
			this.calcClassAndInterfaceCount(pathname, parser);
			System.out.println("Class: Declarations-"+classDeclarationCounter+" References-");
		}else if(type.equals("Interface")) {
			this.calcClassAndInterfaceCount(pathname, parser);
			System.out.println("Interface: Declarations-"+interfaceDeclarationCounter+" References-");
		}else if(type.equals("enum")) {
			this.calcEnumDeclarationCount(pathname, parser);
			System.out.println("Enumeration: Declarations-"+enumDeclarationCounter+" References-");
		}else if(type.equals("Annotation")) {
			this.calcAnnotationDeclarationCount(pathname, parser);
			System.out.println("Annotation: Declarations-"+annotationDeclarationCounter+" References-");
		}
	}
	
	public String getDirectoryPathname(Scanner kb) {
		System.out.print("Enter directory pathname: ");
		String tempString = kb.next();
		
		return tempString;
	}
	
	private static ASTParser buildParser(char[] directoryContent, String pathname) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(directoryContent);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		String[] classpathEntries = new String[] {pathname};
	    String[] sourcepathEntries = new String[] {pathname};
		parser.setEnvironment(classpathEntries, sourcepathEntries, null, true);
		
		return parser;
	}
	
	
	public static void main(String[] args) throws IOException {
		
		DirectoryParser dp = new DirectoryParser();
		
		String pathname = args[0];
		String type = args[1];
		
		char[] directoryContent = convertFilesToString(pathname).toCharArray();
		ASTParser parser = buildParser(directoryContent, pathname);
		
		dp.parseType(pathname, parser, type);
	}
}
