/*
  File:	ServerSolution.java
  Author:	N/A, edited by Connor Heinzmann
  Date:	2/20/17
  
  Description: Reads user input to create accounts.
*/

package banking.primitive.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.*;

import banking.primitive.core.Account.State;

/**
Class:	ServerSolution

Description: Reads user input to create accounts, as well as save the list of accounts.
*/
class ServerSolution implements AccountServer {

	static String fileName = "accounts.ser";

	Map<String,Account> accountMap = null;
	/**
	  Method: ServerSolution
	  Inputs: file to retrieve account list
	  Returns: N/A

	  Description: Reads list from previously saved file use to list accounts
	*/
	public ServerSolution() {
		accountMap = new HashMap<String,Account>();
		File file = new File(fileName);
		ObjectInputStream in = null;
		try {
			if (file.exists()) {
				System.out.println("Reading from file " + fileName + "...");
				in = new ObjectInputStream(new FileInputStream(file));

				Integer sizeI = (Integer) in.readObject();
				int size = sizeI.intValue();
				for (int i=0; i < size; i++) {
					Account acc = (Account) in.readObject();
					if (acc != null)
						accountMap.put(acc.getName(), acc);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}
	}
	/**
	  Method: newAccountFactory
	  Inputs: type, name, balance
	  Returns: Bool

	  Description: Checks new account names versus current ones, will throw false if identical names.
	*/
	private boolean newAccountFactory(String type, String name, float balance)
		throws IllegalArgumentException {
		
		if (accountMap.get(name) != null) return false;
		
		Account acc;
		if ("Checking".equals(type)) {
			acc = new Checking(name, balance);

		} else if ("Savings".equals(type)) {
			acc = new Savings(name, balance);

		} else {
			throw new IllegalArgumentException("Bad account type:" + type);
		}
		try {
			accountMap.put(acc.getName(), acc);
		} catch (Exception exc) {
			return false;
		}
		return true;
	}
	
	/**
	  Method: newAccount
	  Inputs: type, name, balance
	  Returns: Bool

	  Description: Throws if account starts with negative balance
	*/
	public boolean newAccount(String type, String name, float balance) 
		throws IllegalArgumentException {
		
		if (balance < 0.0f) throw new IllegalArgumentException("New account may not be started with a negative balance");
		
		return newAccountFactory(type, name, balance);
	}
	
	/**
	  Method: closeAccount
	  Inputs: name
	  Returns: Bool

	  Description: Closes the inputed account
	*/
	public boolean closeAccount(String name) {
		Account acc = accountMap.get(name);
		if (acc == null) {
			return false;
		}
		acc.setState(State.CLOSED);
		return true;
	}
	
	/**
	  Method: getAccount
	  Inputs: name
	  Returns: name

	  Description: returns account
	*/
	public Account getAccount(String name) {
		return accountMap.get(name);
	}

	/**
	  Method: getAllAccounts
	  Inputs: -
	  Returns: all accounts

	  Description: lists all accounts
	*/
	public List<Account> getAllAccounts() {
		return new ArrayList<Account>(accountMap.values());
	}

	/**
	  Method: getActiveAccounts
	  Inputs: - 
	  Returns: account array

	  Description: lists all accounts that are not closed (open / overdrawn)
	*/
	public List<Account> getActiveAccounts() {
		List<Account> result = new ArrayList<Account>();

		for (Account acc : accountMap.values()) {
			if (acc.getState() != State.CLOSED) {
				result.add(acc);
			}
		}
		return result;
	}
	
	/**
	  Method: saveAccounts
	  Inputs: account array
	  Returns: - 

	  Description: saves account list to a local file.
	*/
	public void saveAccounts() throws IOException {
		ObjectOutputStream out = null; 
		try {
			out = new ObjectOutputStream(new FileOutputStream(fileName));

			out.writeObject(Integer.valueOf(accountMap.size()));
			for (int i=0; i < accountMap.size(); i++) {
				out.writeObject(accountMap.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException("Could not write file:" + fileName);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}
	}

}
