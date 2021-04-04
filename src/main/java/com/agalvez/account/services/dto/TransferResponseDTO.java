package com.agalvez.account.services.dto;

import com.agalvez.account.persistence.entities.Account;
import com.agalvez.account.persistence.enums.CurrencyEnum;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TransferResponseDTO {

	private Account originAccount;

	private Account destinationAccount;

	private CurrencyEnum currency;

	private Long amount;

}
