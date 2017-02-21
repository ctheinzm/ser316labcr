/*
  File:	AccountServerFactory.java
  Author:	N/A
  Date:	2/20/17
  
  Description: For the account server
*/
package banking.primitive.core;

/**
Class:	AccountServerFactory

Description: For the account server
*/
public class AccountServerFactory {

	protected static AccountServerFactory singleton = null;

	protected AccountServerFactory() {

	}

	public static AccountServerFactory getMe() {
		if (singleton == null) {
			singleton = new AccountServerFactory();
		}

		return singleton;
	}

	public AccountServer lookup() {
		return new ServerSolution();
	}
}
