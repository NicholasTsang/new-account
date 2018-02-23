package com.qa.service;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.qa.DBRepository.AccountDBRepository;
import com.qa.domain.Account;
import com.qa.util.JSONUtil;
@ApplicationScoped
@Alternative
public class AccountService implements AccountInterface {

	@PersistenceContext(unitName = "primary")
	private EntityManager manager;
	
	@Inject 
	private JSONUtil util; 

	private Account findAccount(Long id) {
		return manager.find(Account.class, id);
	}
	
	private Map<Integer, Account> accountMap;

	private int count = 0;

	
	public AccountService() {
		accountMap = new HashMap<Integer, Account>();
	}

	public void addAccountFromMap(Account userAccount) {
		accountMap.put(count, userAccount);
		count++;
		
	}

	public void removeAccountFromMap(Integer accountToRemove) {
		boolean countExists = accountMap.containsKey(accountToRemove);
		if (countExists) {
			accountMap.remove(accountToRemove);
		}
	}

	public Map<Integer, Account> getAccountMap() {
		return accountMap;
	}

	public int getNumberOfAccountWithFirstName(String firstNameOfAccount) {
		return (int) accountMap.values().stream()
				.filter(eachAccount -> eachAccount.getFirstName().equals(firstNameOfAccount)).count();
	}

	@Override
	public String getAllAccounts() {
		return util.getJSONForObject(accountMap.values());
	}

	@Override
	public String createAccount(String accout) {
		Account anAccount = util.getObjectForJSON(accout, Account.class);
		accountMap.put(count, anAccount);
		count++;
		
		return "{\"message\": \"account has been sucessfully added\"}";
	}

	@Override
	public String updateAccount(Long id, String accountToUpdate) {
		Account updatedAccount = util.getObjectForJSON(accountToUpdate, Account.class);
		Account accountFromDB = findAccount(id);
		if (accountToUpdate != null) {
		findAccount(id).setFirstName(updatedAccount.getFirstName());
		findAccount(id).setSecondName(updatedAccount.getSecondName());
		findAccount(id).setAccountNumber(updatedAccount.getAccountNumber());
		}
		return "{\"message\": \"account sucessfully updated\"}";
	}

	@Override
	public String deleteAccount(Long id) {
		// TODO Auto-generated method stub
		return null;
	}


}
