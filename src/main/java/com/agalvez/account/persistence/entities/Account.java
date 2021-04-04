package com.agalvez.account.persistence.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.agalvez.account.persistence.enums.CurrencyEnum;
import com.agalvez.account.services.dto.AccountDTO;

import lombok.Data;
import lombok.ToString;

@Entity(name = "Account")
@Table(name = "ACCOUNT")
@Data
@ToString
public class Account implements Serializable {

	private static final long serialVersionUID = 3363261947234147285L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long accountId;

	@Column(length = 100)
	@NotNull
	private String name;

	@Column(length = 3)
	@NotNull
	@Enumerated(EnumType.STRING)
	private CurrencyEnum currency;

	@Column
	private Long balance;

	@Column
	private boolean treasury;
	
	public Account() {
		
	}
	
	public Account(AccountDTO dto) {
		this.name = dto.getName();
		this.currency = dto.getCurrency();
		this.balance = dto.getBalance();
		this.treasury = dto.isTreasury();
	}

}
