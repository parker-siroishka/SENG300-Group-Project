package astApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class AppDriver {
	
	public static DirectoryParser dirParser;
	public static ASTParser parser;
	public static ASTCounter counter;
	
	public static void main(String args[]) {
		
		String pathname = args[0];
		String type = args[1];
		
		dirParser = new DirectoryParser(pathname);
		parser = ASTParser.newParser(AST.JLS8);
		counter = new ASTCounter(null, type);
		
		File directory = new File(dirParser.getSourceDirectory());
		File[] filesInDir = directory.listFiles();
		
		for(File currFile: filesInDir) {
			
			String sDirectoryContent = null;
			try {
				
				sDirectoryContent = dirParser.convertFileToString(currFile);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			parser = dirParser.configParser(parser,sDirectoryContent, currFile.getName());
			CompilationUnit cu = (CompilationUnit)parser.createAST(null);
			counter.setRootNode(cu);
			cu.accept(counter);
		}
		
		System.out.println(counter.getQualTypeToFind() + ". Declarations found: " + counter.getDeclarationCount() + "; References found: " + counter.getReferenceCount());
	}

}
