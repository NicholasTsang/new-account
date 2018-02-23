package com.qa.service;

import com.qa.domain.Account;

public interface AccountInterface {

	 String getAllAccounts() ;

	
	String createAccount(String accout);

	
	String updateAccount(Long id, String accountToUpdate) ;

	
	String deleteAccount(Long id) ;

	
}
