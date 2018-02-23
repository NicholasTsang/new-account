package com.qa.DBRepository;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

import java.util.Collection;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;

import com.qa.domain.Account;
import com.qa.service.AccountInterface;
import com.qa.util.JSONUtil;


@Transactional(SUPPORTS)
@Default
public class AccountDBRepository implements AccountInterface {
	
	private static final Logger LOGGER = Logger.getLogger(AccountDBRepository.class);

	@PersistenceContext(unitName = "primary")
	private EntityManager manager;

	@Inject
	private JSONUtil util;

	public String getAllAccounts() {
		Query query = manager.createQuery("Select a FROM Account a");
		LOGGER.info("THIS IS THE QUERY" + query);
		Collection<Account> accounts = (Collection<Account>) query.getResultList();
		return util.getJSONForObject(accounts);
	}

	@Transactional(REQUIRED)
	public String createAccount(String accout) {
		Account anAccount = util.getObjectForJSON(accout, Account.class);
		manager.persist(anAccount);
		return "{\"message\": \"account has been sucessfully added\"}";
	}

	@Transactional(REQUIRED)
	public String updateAccount(Long id, String accountToUpdate) {
		Account updatedAccount = util.getObjectForJSON(accountToUpdate, Account.class);
		//Account accountFromDB = findAccount(id);
		if (accountToUpdate != null) {
		findAccount(id).setFirstName(updatedAccount.getFirstName());
		findAccount(id).setSecondName(updatedAccount.getSecondName());
		findAccount(id).setAccountNumber(updatedAccount.getAccountNumber());
			//accountFromDB = updatedAccount;
			//manager.merge(accountFromDB);
		}
		return "{\"message\": \"account sucessfully updated\"}";
	}

	@Transactional(REQUIRED)
	public String deleteAccount(Long id) {
		Account accountInDB = findAccount(id);
		if (accountInDB != null) {
			manager.remove(accountInDB);
		}
		return "{\"message\": \"account sucessfully deleted\"}";
	}

	private Account findAccount(Long id) {
		return manager.find(Account.class, id);
	}
	
	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

	public void setUtil(JSONUtil util) {
		this.util = util;
	}

}
