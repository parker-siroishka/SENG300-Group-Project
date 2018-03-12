import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.util.Scanner;

import org.eclipse.jdt.core.dom.ASTParser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestDirectoryParser {
	
	// BASE DIRECTORY
	String baseDir = "C:\\Users\\Alyssa\\Documents\\Programs\\";

	@Test
	void testSetUp_Class() {
		InputStream stdin = System.in;
		// redirects console output to test
		OutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	    String directory = baseDir + "InsertionSortDir";   // contains only InsertionSort.java, 1 class
	    
		String[] args = {directory, "Class"}; // no command line args
		DirectoryParser dp = new DirectoryParser();
		try {
			
			DirectoryParser.main(args);
		} catch (IOException ioe) {
			System.out.println("IOException thrown");
		}
		
	    
		String actual = outContent.toString();
		String expected = "Class: Declarations-1 References-";
		assertEquals(actual, expected);
	}

}
