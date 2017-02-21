/*
  File:	MainFrame.java
  Author: Professor Mehlhase, Fixed by Haocheng Zhang
  Date:	2/20/17
  
  Description: The class is to handle GUI and loading files for the program.
*/
package banking.gui;

import banking.primitive.core.Account;
import banking.primitive.core.AccountServer;
import banking.primitive.core.AccountServerFactory;

import java.io.*;
import java.util.*;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.event.*;
import javax.swing.*;
/**
Class:	MainFrame

Description: The class loads account informations and construct GUI.
*/
@SuppressWarnings("serial")
class MainFrame extends JFrame {
	AccountServer	myServer;
	Properties		props;
	JLabel			typeLabel;
	JLabel			nameLabel;
	JLabel			balanceLabel;
	JComboBox		typeOptions;
	JTextField		nameField;
	JTextField		balanceField;
	JButton 		depositButton;
	JButton 		withdrawButton;
	JButton			newAccountButton;
	JButton			displayAccountsButton;
	JButton			displayODAccountsButton;

	/**
	  Method: MainFrame(String propertyFile)
	  Inputs: propertyFile URL
	  Returns: 
	
	  Description: The method loads information from file and construct GUI.
	*/
	public MainFrame(String propertyFile) throws IOException {

		//** initialize myServer
		myServer = AccountServerFactory.getMe().lookup();

		props = new Properties();

		FileInputStream fis = null; 
		try {
			fis =  new FileInputStream(propertyFile);
			props.load(fis);
			fis.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw ioe;
		}
		_constructForm();
	}
	
	/**
	Class:	DisplayHandler

	Description: The class implements action listener for display action.
	*/
	class DisplayHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			List<Account> accounts = null;
			if (e.getSource() == displayAccountsButton) {
				accounts = myServer.getActiveAccounts();
			} else {
				accounts = myServer.getAllAccounts();
			}
			StringBuffer sb = new StringBuffer();
			Account thisAcct = null;
			for (Iterator<Account> li = accounts.iterator(); li.hasNext();) {
				thisAcct = (Account)li.next();
				sb.append(thisAcct.toString()+"\n");
			}

			JOptionPane.showMessageDialog(null, sb.toString());
		}
	}

	
	/**
	Class:	NewAccountHandler

	Description: Complete a handler for new account button
	*/
	class NewAccountHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String type = typeOptions.getSelectedItem().toString();
			String name = nameField.getText();
			String balance = balanceField.getText();

			if (myServer.newAccount(type, name, Float.parseFloat(balance))) {
				JOptionPane.showMessageDialog(null, "Account created successfully");
			} else {
				JOptionPane.showMessageDialog(null, "Account not created!");
			}
		}
	}
	
	/**
	Class:	SaveAccountsHandler

	Description: Complete a handler for save account button
	*/
	class SaveAccountsHandler implements ActionListener {
		/**
		  Method: actionPerformed
		  Inputs: ActionEvent e
		  Returns: showMessageDialog message

		  Description: Complete a handler for save account button
		*/
		public void actionPerformed(ActionEvent e) {
			try {
				myServer.saveAccounts();
				JOptionPane.showMessageDialog(null, "Accounts saved");
			} catch (IOException exc) {
				JOptionPane.showMessageDialog(null, "Error saving accounts");
			}
		}
	}

	/**
	Class:	DepositHandler

	Description: Complete a handler for deposit account button
	*/
	class DepositHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String name = nameField.getText();
			String balance = balanceField.getText();
			Account acc = myServer.getAccount(name);
			if (acc != null && acc.deposit(Float.parseFloat(balance))) {
				JOptionPane.showMessageDialog(null, "Deposit successful");
			} else {
				JOptionPane.showMessageDialog(null, "Deposit unsuccessful");
			}		
		}
	}
	

	/**
	Class:	WithdrawHandler

	Description: Complete a handler for withdraw account button
	*/
	class WithdrawHandler implements ActionListener {
		/**
		  Method: actionPerformed
		  Inputs: ActionEvent e
		  Returns: showMessageDialog messages

		  Description: Complete a handler for withdraw button
		*/
		public void actionPerformed(ActionEvent e) {
			String name = nameField.getText();
			String balance = balanceField.getText();
			Account acc = myServer.getAccount(name);
			if (acc != null && acc.withdraw(Float.parseFloat(balance))) {
				JOptionPane.showMessageDialog(null, "Withdrawal successful");
			} else {
				JOptionPane.showMessageDialog(null, "Withdrawal unsuccessful");
			}		
		}
	}
	
	/**
	Class:	FrameHandler

	Description: Complete a handler for exit window button
	*/
	static class FrameHandler extends WindowAdapter {
		public void windowClosing(WindowEvent e) {

			System.exit(0);
		}
	}
	
	/**
	  Method: _constructForm
	  Inputs: 
	  Returns: 

	  Description: Construct GUI and loads information
	*/
	private void _constructForm() {
		//*** Make these read from properties
		typeLabel		= new JLabel(props.getProperty("TypeLabel"));
		nameLabel		= new JLabel(props.getProperty("NameLabel"));
		balanceLabel	= new JLabel(props.getProperty("BalanceLabel"));

		Object[] accountTypes = {"Savings", "Checking"};
		typeOptions = new JComboBox(accountTypes);
		nameField = new JTextField(20);
		balanceField = new JTextField(20);

		newAccountButton = new JButton("New Account");
		JButton depositButton = new JButton("Deposit");
		JButton withdrawButton = new JButton("Withdraw");
		JButton saveButton = new JButton("Save Accounts");
		displayAccountsButton = new JButton("List Accounts");
		JButton displayAllAccountsButton = new JButton("All Accounts");

		this.addWindowListener(new FrameHandler());
		newAccountButton.addActionListener(new NewAccountHandler());
		displayAccountsButton.addActionListener(new DisplayHandler());
		displayAllAccountsButton.addActionListener(new DisplayHandler());
		depositButton.addActionListener(new DepositHandler());
		withdrawButton.addActionListener(new WithdrawHandler());
		saveButton.addActionListener(new SaveAccountsHandler());		
		
		Container pane = getContentPane();
		pane.setLayout(new FlowLayout());
		
		JPanel panel1 = new JPanel();
		panel1.add(typeLabel);
		panel1.add(typeOptions);
		
		JPanel panel2 = new JPanel();
		panel2.add(displayAccountsButton);
		panel2.add(displayAllAccountsButton);
		panel2.add(saveButton);
		
		JPanel panel3 = new JPanel();
		panel3.add(nameLabel);
		panel3.add(nameField);
		
		JPanel panel4 = new JPanel();
		panel4.add(balanceLabel);
		panel4.add(balanceField);
		
		JPanel panel5 = new JPanel();
		panel5.add(newAccountButton);
		panel5.add(depositButton);
		panel5.add(withdrawButton);

		pane.add(panel1);
		pane.add(panel2);
		pane.add(panel3);
		pane.add(panel4);
		pane.add(panel5);
		
		setSize(400, 250);
	}
}
