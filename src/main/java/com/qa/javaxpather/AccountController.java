package com.qa.javaxpather;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.qa.DBRepository.AccountDBRepository;
import com.qa.service.AccountInterface;
import com.qa.service.AccountService;


@Path("/Account")
@Produces (MediaType.APPLICATION_JSON)
@Consumes (MediaType.APPLICATION_JSON)
public class AccountController {
	
	@Inject
	private AccountInterface DBService;

	@Path("/json")
	@GET
	@Produces({ "application/json" })
	public String getAllAccounts() {
		return DBService.getAllAccounts();
	}

	@Path("/json")
	@POST
	@Produces({ "application/json" })
	public String addAccount(String account) {
		return DBService.createAccount(account);
	}

	@Path("/json/{id}")
	@PUT
	@Produces({ "application/json" })
	public String updateAccount(@PathParam("id") Long id, String account) {
		return DBService.updateAccount(id, account);
	}

	@Path("/json/{id}")
	@DELETE
	@Produces({ "application/json" })
	public String deleteAccount(@PathParam("id") Long id) {
		return DBService.deleteAccount(id);

	}

	public void setDBService(AccountDBRepository  DBService) {
		this.DBService = DBService;
	}
	
}
