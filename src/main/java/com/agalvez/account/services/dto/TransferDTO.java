package com.agalvez.account.services.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.agalvez.account.persistence.enums.CurrencyEnum;

import lombok.Data;
import lombok.ToString;

@Validated
@Data
@ToString
public class TransferDTO {
	
	@NotNull
	private Long originAccountId;
	
	@NotNull
	private Long destinationAccountId;
	
	@NotNull
	private CurrencyEnum currency;
	
	@NotNull
	@Min(0)
	private Long amount;

}
