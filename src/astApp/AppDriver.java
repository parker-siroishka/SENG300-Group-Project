package astApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class AppDriver {

	public static void main(String args[]) {
		
		String pathname = args[0];
		String type = args[1];
		
		DirectoryParser dp = new DirectoryParser(pathname);
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		ASTCounter counter = new ASTCounter(null, type);
		
		File directory = new File(dp.getSourceDirectory());
		File[] filesInDir = directory.listFiles();
		
		for(File currFile: filesInDir) {
			
			String sDirectoryContent = null;
			try {
				
				sDirectoryContent = dp.convertFileToString(currFile);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			parser = dp.configParser(parser,sDirectoryContent, currFile.getName());
			CompilationUnit cu = (CompilationUnit)parser.createAST(null);
			counter.setRootNode(cu);
			cu.accept(counter);
		}
		
		System.out.println(counter.getQualTypeToFind() + ". Declarations found: " + counter.getDeclarationCount() + "; References found: " + counter.getReferenceCount());
	}

}
