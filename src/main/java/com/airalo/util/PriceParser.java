package com.airalo.util;


import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;

public class PriceParser {
	public static MonetaryAmount parsePrice(String price, CurrencyUnit... cUnit) {
		CurrencyUnit unit = getCurrencyUnit(cUnit);

		if (price.isEmpty()) {
			return Monetary.getDefaultAmountFactory().setCurrency(unit).setNumber(0.0).create();
		}

		return Monetary.getDefaultAmountFactory().setCurrency(unit)
				.setNumber(RegexUtil.parsePrice(price)).create();
	}

	private static CurrencyUnit getCurrencyUnit(CurrencyUnit... cUnit) {
		if (cUnit.length == 0)
			return Monetary.getCurrency("USD");
		return cUnit[0];
	}
}
