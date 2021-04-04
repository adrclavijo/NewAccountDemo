package com.agalvez.account.persistence.enums;

import java.util.Optional;

public enum CurrencyEnum {
	
	EUR,
	USD,
	GBP,
	JPY;
	
	public static Optional<CurrencyEnum> getByName(String name) {
		return Optional.ofNullable(name).map(v -> {
			for (CurrencyEnum currency : CurrencyEnum.values()) {
				if (v.equals(currency.name()))
					return currency;
			}
			return null;
		});
	}
	

}
