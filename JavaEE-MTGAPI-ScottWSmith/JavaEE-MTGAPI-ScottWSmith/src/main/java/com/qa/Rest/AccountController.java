package com.qa.Rest;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.qa.service.AccountService;

@Path("/account")
public class AccountController {

	@Inject
	private AccountService service;

	public void setService(AccountService service) {
		this.service = service;
	}

	@Path("/add")
	@POST
	public String addAccount(String account) {
		return service.createAccount(account);
	}

	@Path("/getAll")
	@GET
	public String getAllAccount() {
		return service.getAllAccounts();
	}

	@Path("/get/{id}")
	@GET
	public String getAccount(@PathParam("id") String id) {
		return service.getAccount(id);
	}

	@POST
	@Path("/update/{id}")
	public String updateAccount(@PathParam("id") Integer accountId, String account) {
		return this.service.updateAccount(accountId, account);
	}

	@DELETE
	@Path("/delete/{id}")
	public String deleteAccount(@PathParam("id") Integer accountId) {
		return this.service.deleteAccount(accountId);
	}

	@POST
	@Path("/login")
	public String login(String account) {
		return this.service.login(account);
	}

	@POST
	@Path("/checkUsername")
	public boolean checkUsername(String account) {
		return this.service.checkUsername(account);
	}
}
