package com.agalvez.account.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.agalvez.account.persistence.entities.Account;
import com.agalvez.account.persistence.enums.CurrencyEnum;
import com.agalvez.account.persistence.repositories.AccountRepository;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class AccountRepositoryTest {

	@Autowired
	private AccountRepository accountRepository;

	Account account;

	@Before
	public void init() {
		account = new Account();
		account.setBalance(11L);
		account.setName("test");
		account.setTreasury(true);
		account.setCurrency(CurrencyEnum.EUR);

		accountRepository.save(account);

	}

	@Test
	public void findByName_whenEntityIsFound_thenEntityListIsReturned() {
		assertEquals(account.getName(), accountRepository.findByName(account.getName()).get(0).getName());
	}
	
	@Test
	public void findByName_whenEntityIsNotFound_thenEmptyListIsReturned() {
		assertTrue(accountRepository.findByName(account.getName()+"1").isEmpty());
	}

}