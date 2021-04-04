package com.agalvez.account.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.agalvez.account.exceptions.AccountBadRequestException;
import com.agalvez.account.exceptions.AccountForbiddenException;
import com.agalvez.account.exceptions.AccountNotFoundException;
import com.agalvez.account.persistence.entities.Account;
import com.agalvez.account.persistence.repositories.AccountRepository;
import com.agalvez.account.services.IAccountService;
import com.agalvez.account.services.dto.AccountDTO;
import com.agalvez.account.services.dto.TransferDTO;
import com.agalvez.account.services.dto.TransferResponseDTO;

@Service
public class AccountServiceImpl implements IAccountService {

	@Autowired
	AccountRepository accountRepository;

	Logger logger = LogManager.getLogger();

	@Override
	public AccountDTO createAccount(Optional<AccountDTO> account) throws Exception {
		try {
			Account entity = new Account(
					account.orElseThrow(() -> new AccountBadRequestException("Invalid account data")));
			return new AccountDTO(accountRepository.save(entity));
		} catch (Exception e) {
			logger.error("Database error: {}", e.getMessage());
			throw new AccountBadRequestException("An error has occurred");
		}

	}

	@Transactional
	@Override
	public AccountDTO getAccount(Long id) throws Exception {
		return new AccountDTO(recoverAccount(id));
	}

	/**
	 * Private method just for internal use. As the code remains the same, the getAccount
	 * method will also call this one
	 * 
	 * @param {@link Long} id
	 * @return {@link Account}
	 * @throws Exception
	 */
	private Account recoverAccount(Long id) throws Exception {
		try {
			return accountRepository.findById(id)
					.orElseThrow(() -> new AccountNotFoundException("Account with ID " + id + " cannot be found"));
		} catch (Exception e) {
			logger.error("Database error: {}", e.getMessage());
			throw new AccountBadRequestException("An error has occurred");
		}
	}

	@Override
	public Page<AccountDTO> getAllAccounts(Pageable page) throws Exception {
		try {
			List<AccountDTO> accountList = new ArrayList<>();
			Page<Account> originalResults = accountRepository.findAll(page);
			originalResults.forEach(a -> accountList.add(new AccountDTO(a)));
			return new PageImpl<>(accountList, page, originalResults.getTotalElements());
		} catch (Exception e) {
			logger.error("Database error: {}", e.getMessage());
			throw new AccountBadRequestException("An error has occurred");
		}
	}

	@Override
	public AccountDTO findAccount(String name) throws Exception {
		try {
			List<Account> accountList = accountRepository.findByName(name);
			if (accountList.isEmpty()) {
				throw new AccountNotFoundException("Account with name " + name + " cannot be found");
			} else {
				return new AccountDTO(accountList.get(0));
			}
		} catch (Exception e) {
			logger.error("Database error: {}", e.getMessage());
			throw new AccountBadRequestException("An error has occurred");
		}

	}

	@Transactional
	@Override
	public TransferResponseDTO transfer(TransferDTO transfer) throws Exception {
		if (null == transfer) {
			throw new AccountBadRequestException("Invalid transfer data values");
		} else if (transfer.getOriginAccountId() != null
				&& transfer.getOriginAccountId().equals(transfer.getDestinationAccountId())) {
			throw new AccountBadRequestException("Both accounts are the same one");
		} else {
			Account origin = recoverAccount(transfer.getOriginAccountId());
			Account destination = recoverAccount(transfer.getDestinationAccountId());
			if (origin.getBalance() < transfer.getAmount() && !origin.isTreasury()) {
				throw new AccountForbiddenException("The origin account has not enough balance for this transfer");
			}
			// different currency check
			if (!origin.getCurrency().equals(destination.getCurrency())) {
				throw new AccountForbiddenException("The currency for both accounts is not the same");
			}

			origin.setBalance(origin.getBalance() - transfer.getAmount());
			destination.setBalance(destination.getBalance() + transfer.getAmount());

			TransferResponseDTO response = new TransferResponseDTO();
			response.setAmount(transfer.getAmount());
			response.setCurrency(transfer.getCurrency());
			response.setOriginAccount(accountRepository.save(origin));
			response.setDestinationAccount(accountRepository.save(destination));

			return response;
		}
	}

}
