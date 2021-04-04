package com.agalvez.account.services.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.agalvez.account.exceptions.AccountBadRequestException;
import com.agalvez.account.exceptions.AccountForbiddenException;
import com.agalvez.account.exceptions.AccountNotFoundException;
import com.agalvez.account.persistence.entities.Account;
import com.agalvez.account.persistence.enums.CurrencyEnum;
import com.agalvez.account.persistence.repositories.AccountRepository;
import com.agalvez.account.services.dto.AccountDTO;
import com.agalvez.account.services.dto.TransferDTO;

@RunWith(SpringRunner.class)
public class AccountServiceImplTest {

	@Mock
	private AccountRepository mockAccountRepository;

	@InjectMocks
	AccountServiceImpl accountService;

	@Test
	public void createAccount_whenValidDataIsGiven_thenEntityIsCreated() throws Exception {
		when(mockAccountRepository.save(Mockito.any())).thenReturn(new Account());

		assertNotNull(accountService.createAccount(Optional.of(new AccountDTO())));
	}

	@Test(expected = AccountBadRequestException.class)
	public void createAccount_whenInValidDataIsGiven_thenExceptionIsThrown() throws Exception {
		accountService.createAccount(null);
	}

	@Test
	public void getAccount_whenValidIdIsGiven_thenEntityIsReturned() throws Exception {
		when(mockAccountRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Account()));

		assertNotNull(accountService.getAccount(1L));
	}

	@Test(expected = AccountNotFoundException.class)
	public void getAccount_whenInvalidIdIsGiven_thenExceptionIsThrown() throws Exception {
		when(mockAccountRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));

		accountService.getAccount(1L);
	}

	@Test(expected = AccountNotFoundException.class)
	public void getAccount_whenNullIdIsGiven_thenExceptionIsThrown() throws Exception {

		accountService.getAccount(null);
	}

	@Test
	public void findAccount_whenValidNameIsGiven_thenEntityIsReturned() throws Exception {

		List<Account> accountList = new ArrayList<>();
		accountList.add(new Account());
		when(mockAccountRepository.findByName(Mockito.anyString())).thenReturn(accountList);

		assertNotNull(accountService.findAccount("name"));
	}

	@Test(expected = AccountNotFoundException.class)
	public void findAccount_whenValidNameIsGiven_thenExceptionIsThrown() throws Exception {
		when(mockAccountRepository.findByName(Mockito.anyString())).thenReturn(new ArrayList<>());

		accountService.findAccount("");
	}

	@Test(expected = AccountNotFoundException.class)
	public void findAccount_whenInvalidNameIsGiven_thenExceptionIsThrown() throws Exception {

		accountService.findAccount(null);
	}
	
	@Test(expected = AccountBadRequestException.class)
	public void transfer_whenTransferDataIsNull_thenExceptionIsThrown() throws Exception {
		accountService.transfer(null);
	}
	
	@Test(expected = AccountBadRequestException.class)
	public void transfer_whenBothAccountsAreTheSame_thenExceptionIsThrown() throws Exception {
		TransferDTO transfer = new TransferDTO();
		transfer.setOriginAccountId(1L);
		transfer.setDestinationAccountId(1L);
		
		accountService.transfer(transfer);
	}
	
	@Test(expected = AccountForbiddenException.class)
	public void transfer_whenOriginAccountHasNotEnoughBalanceAndItIsNotTreasury_thenExceptionIsThrown() throws Exception {
		Account accountO = new Account();
		accountO.setAccountId(1L);
		accountO.setBalance(100L);
		accountO.setTreasury(false);
		
		Account accountD = new Account();
		accountO.setAccountId(2L);
		accountD.setBalance(200L);
		accountD.setTreasury(false);
		
		TransferDTO transfer = new TransferDTO();
		transfer.setOriginAccountId(1L);
		transfer.setDestinationAccountId(2L);
		transfer.setAmount(300L);	
		
		
		
		when(mockAccountRepository.findById(1L)).thenReturn(Optional.of(accountO));
		when(mockAccountRepository.findById(2L)).thenReturn(Optional.of(accountD));

		accountService.transfer(transfer);
	}
	
	@Test(expected = AccountForbiddenException.class)
	public void transfer_whenOriginAccountHasNotEnoughBalanceAndItIsTreasuryButCurrencyIsNotTheSame_thenExceptionIsThrown() throws Exception {
		Account accountO = new Account();
		accountO.setAccountId(1L);
		accountO.setBalance(100L);
		accountO.setTreasury(true);
		accountO.setCurrency(CurrencyEnum.EUR);
		
		Account accountD = new Account();
		accountO.setAccountId(2L);
		accountD.setBalance(200L);
		accountD.setTreasury(false);
		accountD.setCurrency(CurrencyEnum.USD);

		
		TransferDTO transfer = new TransferDTO();
		transfer.setOriginAccountId(1L);
		transfer.setDestinationAccountId(2L);
		transfer.setAmount(300L);	
		
		
		
		when(mockAccountRepository.findById(1L)).thenReturn(Optional.of(accountO));
		when(mockAccountRepository.findById(2L)).thenReturn(Optional.of(accountD));

		assertNotNull(accountService.transfer(transfer));
	}
	
	
	@Test
	public void transfer_whenOriginAccountHasNotEnoughBalanceAndItIsTreasury_thenOperationIsCompleted() throws Exception {
		Account accountO = new Account();
		accountO.setAccountId(1L);
		accountO.setBalance(100L);
		accountO.setTreasury(true);
		accountO.setCurrency(CurrencyEnum.EUR);
		
		Account accountD = new Account();
		accountO.setAccountId(2L);
		accountD.setBalance(200L);
		accountD.setTreasury(false);
		accountD.setCurrency(CurrencyEnum.EUR);

		
		TransferDTO transfer = new TransferDTO();
		transfer.setOriginAccountId(1L);
		transfer.setDestinationAccountId(2L);
		transfer.setAmount(300L);	
		
		
		
		when(mockAccountRepository.findById(1L)).thenReturn(Optional.of(accountO));
		when(mockAccountRepository.findById(2L)).thenReturn(Optional.of(accountD));

		assertNotNull(accountService.transfer(transfer));
	}
		

}
