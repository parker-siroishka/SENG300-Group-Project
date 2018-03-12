import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;

public class DirectoryParser{
	
	int classDeclarationCounter = 0;
	int classReferenceCounter = 0;
	SimpleName className;
	int annotationDeclarationCounter = 0;
	int interfaceDeclarationCounter = 0;
	int interfaceReferenceCounter = 0;
	SimpleName interfaceName;
	int enumDeclarationCounter = 0;
	
	/**
	 * Iterates through all files in directory and concatenates all java code to one string
	 * 
	 * @param directoryPath - location to directory for parsing
	 * @return String - String of java code in directory
	 * @throws IOException - If invalid directoryPath
	 */
	public static String convertFilesToString(File f)throws IOException, FileNotFoundException {
		StringBuilder sb = new StringBuilder();
		
		

			
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
		return sb.toString();
	}
	
	/**
	 * For every TypeDeclaration node visited, increment either classDeclarationCounter
	 * or interfaceDeclarationCounter depending on what the node is
	 * 
	 * @param pathname - command line argument for directory
	 * @param parser - built parser
	 * @throws IOException - If invalid pathname
	 */
	public void calcClassAndInterfaceCount(String pathname, ASTParser parser) throws IOException {
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		cu.accept(new ASTVisitor() {
			
			public boolean visit(TypeDeclaration node) {
				if (node.isInterface()) {
					interfaceName = node.getName();
					interfaceDeclarationCounter++;
				}else {
					className = node.getName();
					classDeclarationCounter++;
				}
					
				
				return true;
			}
		});
		
	}
	
	public void calcEnumDeclarationCount(String pathname, ASTParser parser) throws IOException {
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		cu.accept(new ASTVisitor() {
			
			public boolean visit(EnumDeclaration node) {
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
	

	/**
	 * Depending on the command line string passed, that Java type will be parsed and analyzed
	 * 
	 * @param pathname - path to directory
	 * @param parser - ASTParser
	 * @param type - command line string indicating what type to parse
	 * @throws IOException - If invalid pathname
	 */
	public void parseType(String pathname, ASTParser parser, String type) throws IOException {
		if (type.equals("Class")) {
			this.calcClassAndInterfaceCount(pathname, parser);
			System.out.println("Class: Declarations-"+classDeclarationCounter+" References-"+classReferenceCounter);
		}else if(type.equals("Interface")) {
			this.calcClassAndInterfaceCount(pathname, parser);
			System.out.println("Interface: Declarations-"+interfaceDeclarationCounter+" References-"+interfaceReferenceCounter);
		}else if(type.equals("Enum")) {
			this.calcEnumDeclarationCount(pathname, parser);
			System.out.println("Enumeration: Declarations-"+enumDeclarationCounter+" References-");
		}else if(type.equals("Annotation")) {
			this.calcAnnotationDeclarationCount(pathname, parser);
			System.out.println("Annotation: Declarations-"+annotationDeclarationCounter+" References-");
		}
	}
	
	/**
	 * Sets all parser properties
	 * 
	 * @param directoryContent - char[] of java files content from directory
	 * @param pathname - path to directory
	 * @return parser - ASTParser
	 */
	private static ASTParser configureParser(String fileContent, String pathname, ASTParser parser, String fileName) {
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		parser.setUnitName(fileName);
		parser.setSource(fileContent.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		String[] sourcepathEntries = new String[] {pathname};
	    String [] encodings = new String[] {"UTF-8"};
		parser.setEnvironment(null, sourcepathEntries, encodings, true);
		
		return parser;
	}
	
	
	public static void main(String[] args) throws IOException {
		
		DirectoryParser dp = new DirectoryParser();
		
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		String pathname = args[0];
		String type = args[1];
		File dir = new File(pathname);
		File[] files = dir.listFiles();
		String[] fileStrings = new String[files.length];
		int counter = 0;
	
		for(File f: files) {
	
			String content = convertFilesToString(f);
			fileStrings[counter] = content;
			parser = configureParser(fileStrings[counter], pathname, parser, f.getName());
			dp.parseType(pathname, parser, type);
			counter++;
		}
		
		
		
		
	}
}
