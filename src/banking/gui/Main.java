/*
  File:	Main.java
  Author: Professor Mehlhase, Fixed by Haocheng Zhang
  Date:	2/20/17
  
  Description: The class is the main class in the program.
*/
package banking.gui;

import javax.swing.JFrame;

/**
Class:	Main

Description: main method for running the program.
*/
final class Main {

	/**
	  Method: Main() 
	  Inputs: 
	  Returns: 
	
	  Description: The main constructor.
	*/
	private Main() {
	}
	
	/**
	  Method: main(final String[] args) 
	  Inputs: args command-line arguments
	  Returns: 
	
	  Description: main method for running the program.
	*/
	public static void main(final String[] args) throws Exception {

		if (args.length != 1) {
			System.out.println("Usage: java FormMain <property file>");
			System.exit(1);
		}

		String propertyFile = args[0];
		JFrame frame = new MainFrame(propertyFile);
		frame.setVisible(true);

	}
	
}
