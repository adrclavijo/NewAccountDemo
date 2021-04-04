package com.agalvez.account.services.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.springframework.validation.annotation.Validated;

import com.agalvez.account.persistence.entities.Account;
import com.agalvez.account.persistence.enums.CurrencyEnum;

import lombok.Data;

@Validated
@Data
public class AccountDTO {
	
	@Null
	private Long accountId;

	@NotNull
	private String name;

	@NotNull
	@Enumerated(EnumType.STRING)
	private CurrencyEnum currency;

	private Long balance;

	private boolean treasury;
	
	public AccountDTO() {
		
	}
	
	public AccountDTO(Account entity) {
		this.accountId = entity.getAccountId();
		this.name = entity.getName();
		this.currency = entity.getCurrency();
		this.balance = entity.getBalance();
		this.treasury = entity.isTreasury();
	}

}
