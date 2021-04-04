package com.agalvez.account.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.agalvez.account.persistence.entities.Account;
import com.agalvez.account.services.dto.AccountDTO;
import com.agalvez.account.services.dto.TransferDTO;
import com.agalvez.account.services.dto.TransferResponseDTO;

public interface IAccountService {
	
	/**
	 * Recovers an Account by his ID
	 * @param {@link Long} id
	 * @return {@link AccountDTO}
	 * @throws Exception
	 */
	public AccountDTO getAccount(Long id) throws Exception;
	
	/**
	 * Recovers an Account by his name
	 * @param {@link String} name
	 * @return {@link AccountDTO}
	 * @throws Exception
	 */
	public AccountDTO findAccount(String name) throws Exception;
	
	/**
	 * Recovers all Accounts
	 * @param {@link Pageable} page
	 * @return {@link Page} of {@link AccountDTO}
	 * @throws Exception
	 */
	public Page<AccountDTO> getAllAccounts(Pageable page) throws Exception;
	
	/**
	 * Creates a new Account
	 * @param {@link Optional} of {@link AccountDTO} account
	 * @return {@link AccountDTO}
	 * @throws Exception
	 */
	public AccountDTO createAccount(Optional<AccountDTO> account) throws Exception;
	
	/**
	 * Allows to transfer money from one account to another
	 * @param {@link TransferDTO} transfer
	 * @return {@link TransferResponseDTO}
	 * @throws Exception
	 */
	public TransferResponseDTO transfer(TransferDTO transfer) throws Exception;

}
