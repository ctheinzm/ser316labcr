/*
  File:	Savings.java
  Author: Professor Mehlhase, Fixed by Haocheng Zhang
  Date:	2/20/17
  
  Description: The class is to handle Savings account.
*/
package banking.primitive.core;

/**
Class:	Savings

Description: The class is a special account savings account and have multiple methods to access savings account.
*/
public class Savings extends Account {

	/**
	  Method: Savings(String name) 
	  Inputs: name
	  Returns: 
	
	  Description: The method construct a new account with name.
	*/
	public Savings(String name) {
		super(name);
	}

	/**
	  Method: Savings(String name, float balance)
	  Inputs: name and balance
	  Returns: 
	
	  Description: The method construct a new account with name and balance.
	*/
	public Savings(String name, float balance) throws IllegalArgumentException {
		super(name, balance);
	}

	/**
	  Method: getType() 
	  Inputs: 
	  Returns: String "Savings"
	
	  Description: The method return a string "Savings".
	*/
	public String getType() { return "Savings"; }

	
	
	/**
	  Method: deposit(float amount) 
	  Inputs: amount
	  Returns: boolean
	
	  Description: The method deposit money into account by specific amount.
	  			   A deposit may be made unless the Savings account is closed
	  			   A deposit comes with a fee of 50 cents per deposit
	*/
	public boolean deposit(float amount) {
		if (getState() != State.CLOSED && amount > 0.0f) {
			balance = balance + amount - 0.50F;
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
	  			   After 3 withdrawals a fee of $1 is added to each withdrawal.
	  			   An account whose balance dips below 0 is in an OVERDRAWN state
	*/
	public boolean withdraw(float amount) {
		if (getState() == State.OPEN && amount > 0.0f) {
			balance = balance - amount;
			_numWithdraws++;
			if (_numWithdraws > 3)
				balance = balance - 1.0f;
			// KG BVA: should be < 0
			if (balance < 0.0f) {
				setState(State.OVERDRAWN);
			}
			return true;
		}
		return false;
	}

	/**
	  Method: toString() 
	  Inputs: 
	  Returns: String "Savings" + name + balance
	
	  Description: The method print the savings account infos.
	*/
	public String toString() {
		return "Savings: " + getName() + ": " + getBalance();
	}
	
	private static final long serialVersionUID = 111L;
	private int _numWithdraws = 0;
}
