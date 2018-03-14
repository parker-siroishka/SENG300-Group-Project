package astApp;
/* https://www.programcreek.com/2014/01/how-to-resolve-bindings-when-using-eclipse-jdt-astparser/
https://help.eclipse.org/mars/index.jsp?topic=%2Forg.eclipse.jdt.doc.isv%2Freference%2Fapi%2Forg%2Feclipse%2Fjdt%2Fcore%2Fdom%2Fpackage-summary.html
*/
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.io.File;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;


public class DirectoryParser {
	
	String sourceDirectory;
			
	public DirectoryParser(String sourceDir) {
		
		this.sourceDirectory = sourceDir;
		
	}
	
	
	/**
	 * Iterates through all files in directory and concatenates to one string
	 * 
	 * @param fileToConvert - the .java file that is to be converted into a string
	 * @return String - String of java code in directory
	 * @throws IOException - If invalid directoryPath
	 */
	public String convertFileToString(File fileToConvert)throws IOException, FileNotFoundException {
		
		StringBuilder sb = new StringBuilder();
			
		if(fileToConvert.isFile() && fileToConvert.getName().toLowerCase().endsWith(".java")) {
			BufferedReader inputStream = null;
			
			try {
				inputStream = new BufferedReader(new FileReader(fileToConvert));
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
	 * Sets the configuration settings for an instance of ASTParser in order for the environment to be set properly
	 * This ensures that the AST provides binding information for each node.
	 * 
	 * @param parser - an instance of the ASTParser class to be configured
	 * @param fileContents - String which contains the converted contents of the current file to parse
	 * @param directoryPath - the path in the file system which represents the environment of the package to be parsed
	 * @param filename - the name of the .java file to be parsed currently
	 * @return parser - returns the parameter parser after the settings have been configured
	 */
	
	public ASTParser configParser(ASTParser parser, String fileContents, String fileName){
			Map optionMap = JavaCore.getOptions();
			JavaCore.setComplianceOptions(JavaCore.VERSION_1_8, optionMap);
			parser.setCompilerOptions(optionMap);
		
			parser.setResolveBindings(true);
			parser.setBindingsRecovery(true);
			parser.setIgnoreMethodBodies(false);

			parser.setUnitName(fileName);
			String[] classpathEntries = new String[] {this.sourceDirectory};
		    String[] sourcepathEntries = new String[] {this.sourceDirectory};
		    
			parser.setEnvironment(classpathEntries, sourcepathEntries, new String[]{"UTF-8"}, true);
			parser.setSource(fileContents.toCharArray());
			return parser;
	}
	
	public void setSourceDirectory(String newSourceDir) {
		
		this.sourceDirectory = newSourceDir; 
	}
		
	
	public String getSourceDirectory() {
		
		return this.sourceDirectory;
	}
}
	
	
	
	