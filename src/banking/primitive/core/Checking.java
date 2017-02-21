/*
  File:	Checking.java
  Author: Professor Mehlhase, Fixed by Haocheng Zhang
  Date:	2/20/17
  
  Description: The class is to handle checking account.
*/

package banking.primitive.core;

/**
Class:	Checking

Description: The class is a special account checking account and have multiple methods to access checking account.
*/
public class Checking extends Account {

	private static final long serialVersionUID = 11L;
	private int _numWithdraws = 0;

	/**
	  Method: Checking(String name) 
	  Inputs: name
	  Returns: 
	
	  Description: The method construct a new account with name.
	*/
	private Checking(String name) {
		super(name);
	}

	/**
	  Method: Checking(String name) 
	  Inputs: name and balance
	  Returns: 
	
	  Description: The method construct a new account with name and balance.
	*/
	public Checking(String name, float balance) {
		super(name, balance);
	}

	
	/**
	  Method: createChecking(String name) 
	  Inputs: name
	  Returns: The method return a new checking account with name.
	
	  Description: The method create a new checking account with name.
	*/
  public static Checking createChecking(String name) {
      return new Checking(name);
  }
  
  
	/**
	  Method: deposit(float amount) 
	  Inputs: amount
	  Returns: boolean
	
	  Description: The method deposit money into account by specific amount.
	  			   A deposit may be made unless the Checking account is closed
	*/
	public boolean deposit(float amount) {
		if (getState() != State.CLOSED && amount > 0.0f) {
			balance = balance + amount;
			if (balance >= 0.0f) {
				setState(State.OPEN);
			}
			return true;
		}
		return false;
	}
	
	
	/**
	  Method: withdraw(float amount) 
	  Inputs: amount
	  Returns: boolean
	
	  Description: The method withdraw money from account by specific amount.
	  			   After 10 withdrawals a fee of $2 is charged per transaction You may
	  			   continue to withdraw an overdrawn account until the balance is below -$100
	*/
	public boolean withdraw(float amount) {
		if (amount > 0.0f) {		
			// KG: incorrect, last balance check should be >=
			if (getState() == State.OPEN || (getState() == State.OVERDRAWN && balance > -100.0f)) {
				balance = balance - amount;
				_numWithdraws++;
				if (_numWithdraws > 10)
					balance = balance - 2.0f;
				if (balance < 0.0f) {
					setState(State.OVERDRAWN);
				}
				return true;
			}
		}
		return false;
	}

	/**
	  Method: getType() 
	  Inputs: 
	  Returns: String "checking"
	
	  Description: The method return a string "Checking".
	*/
	public String getType() { return "Checking"; }
	
	
	/**
	  Method: toString() 
	  Inputs: 
	  Returns: String "checking" + name + balance
	
	  Description: The method print the checking account infos.
	*/
	public String toString() {
		return "Checking: " + getName() + ": " + getBalance();
	}
}
