package com.qa.service;

import javax.inject.Inject;

import com.qa.persistence.repository.AccountRepository;

public class AccountServiceImpl implements AccountService {

	@Inject
	private AccountRepository repo;

	public String getAllAccounts() {
		return this.repo.getAllAccounts();
	}

	public String createAccount(String account) {
		return this.repo.createAccount(account);
	}

	public String deleteAccount(int accountId) {
		return this.repo.deleteAccount(accountId);
	}

	public String updateAccount(int accountId, String account) {
		return this.repo.updateAccount(accountId, account);
	}

	public String getAccount(String id) {
		return this.repo.getAccount(id);
	}

	public String login(String account) {
		return this.repo.login(account);
	}

	public boolean checkUsername(String account) {
		return this.repo.checkUsername(account);
	}
}
