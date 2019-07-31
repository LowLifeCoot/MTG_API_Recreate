package com.qa.persistence.repository;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.qa.persistence.domain.Account;
import com.qa.util.JSONUtil;

@Default
@Transactional(value = TxType.SUPPORTS)
public class AccountDBRepositry implements AccountRepository {

	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	@Inject
	private JSONUtil json;

	@Transactional(value = TxType.REQUIRED)
	public String getAllAccounts() {
		TypedQuery<Account> query = em.createQuery("SELECT a FROM Account a", Account.class);
		return json.getJSONForObject(query.getResultList());
	}

	@Transactional(value = TxType.REQUIRED)
	public String getAccount(String id) {
		TypedQuery<Account> query = em.createQuery("SELECT a FROM Account a WHERE ID = '" + id + "'", Account.class);
		return json.getJSONForObject(query.getResultList());
	}

	@Transactional(value = TxType.REQUIRED)
	public String createAccount(String account) {
		Account toCreate = this.json.getObjectForJSON(account, Account.class);
		this.em.persist(toCreate);
		return this.json.getJSONForObject(toCreate.getId());
	}

	@Transactional(value = TxType.REQUIRED)
	public String deleteAccount(int accountNumber) {
		Account accountTemp = em.find(Account.class, accountNumber);
		em.remove(accountTemp);
		return "Removed account: " + accountTemp.getName();
	}

	@Transactional(value = TxType.REQUIRED)
	public String updateAccount(int accountNumber, String account) {
		// if id is same then replace data at database

		Account current = this.em.find(Account.class, accountNumber);
		Account toChange = this.json.getObjectForJSON(account, Account.class);
		current.setName(toChange.getName());
		current.setPassword(toChange.getPassword());

		this.em.persist(current);
		return SUCCESS + this.json.getJSONForObject(current);
	}

	@Transactional(value = TxType.REQUIRED)
	public String login(String account) {
		Account newAccount = this.json.getObjectForJSON(account, Account.class);
		String username = newAccount.getName();
		String password = newAccount.getPassword();

		TypedQuery<Account> query = this.em.createQuery(
				"SELECT a FROM Account a WHERE name = '" + username + "' AND password = '" + password + "' ",
				Account.class);
		Account logAcc = null;
		try {
			logAcc = (Account) query.getSingleResult();
		} catch (NoResultException nre) {
			createAccount(account);
			logAcc = (Account) query.getSingleResult();
		}
		return this.json.getJSONForObject(logAcc.getId());
	}

	public boolean checkUsername(String account) {
		Account user = this.json.getObjectForJSON(account, Account.class);
		String username = user.getName();
		TypedQuery<Account> query = this.em.createQuery("SELECT a FROM Account a WHERE name='" + username + "'",
				Account.class);
		if (query.getResultList().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}
