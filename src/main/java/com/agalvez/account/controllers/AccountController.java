package com.agalvez.account.controllers;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agalvez.account.services.IAccountService;
import com.agalvez.account.services.dto.AccountDTO;
import com.agalvez.account.services.dto.TransferDTO;
import com.agalvez.account.services.dto.TransferResponseDTO;

@RestController
@Validated
@RequestMapping("/accounts")
public class AccountController {
	
	Logger logger = LogManager.getLogger();

	@Autowired
	IAccountService accountService;

	@GetMapping("/get/{id}")
	public AccountDTO getAccount(@PathVariable("id") @NotBlank Long id) throws Exception {
		return accountService.getAccount(id);
	}
	
	@GetMapping("/getAll")
	public Page<AccountDTO> getAllAccounts(@RequestParam int page, @RequestParam int pageSize) throws Exception {
		return accountService.getAllAccounts(PageRequest.of(page, pageSize));
	}
	
	@GetMapping("/find/{name}")
	public AccountDTO getAccount(@PathVariable("name") @NotBlank String name) throws Exception {
		return accountService.findAccount(name);
	}
	
	@PostMapping("/create")
	public AccountDTO createAccount(@RequestBody @Valid AccountDTO account) throws Exception {
		logger.info(account);
		return accountService.createAccount(Optional.of(account));		
	}
	
	@PostMapping("/transfer")
	public TransferResponseDTO transfer(@RequestBody @Valid TransferDTO transfer) throws Exception {
		logger.info(transfer);
		return accountService.transfer(transfer);
		
	}
	
}
