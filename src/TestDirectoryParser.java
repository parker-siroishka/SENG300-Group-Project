import static org.junit.Assert.*;
import java.io.*;
import java.util.Scanner;

import org.eclipse.jdt.core.dom.ASTParser;
import org.junit.Test;

public class TestDirectoryParser {
	
	// BASE DIRECTORY
	String BASEDIR = "C:\\Users\\Alyssa\\Documents\\Programs\\SENG_300_Group1\\";

	@Test
	public void test_1_Class() {
		// redirects console output to test
		OutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	    String directory = BASEDIR + "InsertionSortDir";   // contains only InsertionSort.java, 1 class
	    
		String[] args = {directory, "Class"};
		try {
			
			DirectoryParser.main(args);
		} catch (IOException ioe) {
			System.out.println("IOException thrown");
		}
		
	    
		String actual = outContent.toString();
		String expected = "Class: Declarations-1 References-0\r\n";
		assertEquals(actual, expected);
	}
	
	@Test
	public void test_0_Interface() {
		// redirects console output to test
		OutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	    String directory = BASEDIR + "InsertionSortDir";   // contains only InsertionSort.java
	    
		String[] args = {directory, "Interface"};
		try {
			DirectoryParser.main(args);
		} catch (IOException ioe) {
			System.out.println("IOException thrown");
		}
		
	    
		String actual = outContent.toString();
		String expected = "Interface: Declarations-0 References-0\r\n";
		assertEquals(actual, expected);
	}
	
	@Test
	public void test_0_Enum() {
		// redirects console output to test
		OutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	    String directory = BASEDIR + "InsertionSortDir";   // contains only InsertionSort.java
	    
		String[] args = {directory, "Enum"};
		try {
			DirectoryParser.main(args);
		} catch (IOException ioe) {
			System.out.println("IOException thrown");
		}
		
	    
		String actual = outContent.toString();
		String expected = "Enumeration: Declarations-0 References-\r\n";
		assertEquals(actual, expected);
	}
	
	@Test
	public void test_0_Annotation() {
		// redirects console output to test
		OutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	    String directory = BASEDIR + "InsertionSortDir";   // contains only InsertionSort.java
	    
		String[] args = {directory, "Annotation"};
		try {
			DirectoryParser.main(args);
		} catch (IOException ioe) {
			System.out.println("IOException thrown");
		}
		
	    
		String actual = outContent.toString();
		String expected = "Annotation: Declarations-0 References-\r\n";
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_txt_file_Class() {
		// redirects console output to test
		OutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	    String directory = BASEDIR + "notJavaFile";   // contains only blah.txt
	    
		String[] args = {directory, "Class"};
		try {
			DirectoryParser.main(args);
		} catch (IOException ioe) {
			System.out.println("IOException thrown");
		}
		
	    
		String actual = outContent.toString();
		String expected = "Class: Declarations-0 References-0\r\n";
		assertEquals(actual, expected);
	}
	
	@Test
	public void test_invalid_java_file_Class() {
		// redirects console output to test
		OutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	    String directory = BASEDIR + "invalidFile";   // contains only blah.java
	    
		String[] args = {directory, "Class"};
		try {
			DirectoryParser.main(args);
		} catch (IOException ioe) {
			System.out.println("IOException thrown");
		}
		
	    
		String actual = outContent.toString();
		String expected = "Class: Declarations-0 References-0\r\n";
		assertEquals(actual, expected);
	}
	
	@Test
	public void test_txt_file_with_Class() {
		// redirects console output to test
		OutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	    String directory = BASEDIR + "notJavaFileWithClassDec";   // contains only blah.java
	    
		String[] args = {directory, "Class"};
		try {
			DirectoryParser.main(args);
		} catch (IOException ioe) {
			System.out.println("IOException thrown");
		}
		
	    
		String actual = outContent.toString();
		String expected = "Class: Declarations-0 References-0\r\n";
		assertEquals(actual, expected);
	}
	
}
