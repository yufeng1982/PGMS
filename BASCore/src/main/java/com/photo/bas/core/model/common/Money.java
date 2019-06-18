package com.photo.bas.core.model.common;

import org.jscience.economics.money.Currency;
import org.jscience.physics.amount.Amount;

@SuppressWarnings({ "rawtypes", "unchecked", "unused"})
public class Money implements Comparable<Money> {

	private Double value;

	private Currency currency;

	public Money(Double value, Currency currency) {
		super();

		if (value == null) throw new IllegalArgumentException("Data cannot be null!");
		if (currency == null) throw new IllegalArgumentException("Unit cannot be null!");

		this.value = value;
		this.currency = currency;
	}

	private Money() {
		super();
	}

	private final void setValue(Double value) {
		this.value = value;
	}

	public final Double getValue() {
		return value;
	}

	private final void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public final Currency getCurrency() {
		return currency;
	}

	public final Money convertCurrency(Currency desiredCurrency) {
		// check to see if the two units are equal
		if (getCurrency().equals(desiredCurrency)) {
			return new Money(getValue(), getCurrency());
		} else {
			if (getCurrency().isCompatible(desiredCurrency)) {

				Currency.setReferenceCurrency(desiredCurrency);
				Amount amount = Amount.valueOf(value, currency);
				return new Money(amount.doubleValue(desiredCurrency), desiredCurrency);
			}
			else {
				throw new ClassCastException("Incompatible units for conversion");
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (!(obj instanceof Money))
			return false;

		Money data = (Money) obj;

		try {
			data = data.convertCurrency(this.getCurrency());
			
			Double v1 = getValue();
			Double v2 = data.getValue();

			if (v1 == v2 || (v1 != null && v1.equals(v2))) {
				return true;
			}
		} catch (ClassCastException cce) {
			return false;
		}

		return false;
	}

	@Override
	public String toString() {
		return "[" + getValue().toString() + ", "
				+ getCurrency().toString() + "]";
	}

	public int compareTo(Money data) {
		/*
		 * Be sure that data unit is equal to this object's data unit. This will
		 * throw a ClassCastException if the units are not compatible. This is
		 * consistent with the compareTo method's API documentation which states
		 * that this method throws a ClassCastException if the specified
		 * object's type prevents it from being compared.
		 */
		data = data.convertCurrency(this.getCurrency());

		// now that we have a data object with equal units, we can compare the
		// actual value
		Double v1 = getValue();
		Double v2 = data.getValue();

		return Double.compare(v1, v2);
	}

	public static final Money parseCurrencyData(String uomComponentStr)
			throws IllegalArgumentException {
		uomComponentStr = uomComponentStr.replace('[', ' ');
		uomComponentStr = uomComponentStr.replace(']', ' ');
		uomComponentStr = uomComponentStr.trim();

		String[] split = uomComponentStr.split(",");

		if (split.length != 2)
			throw new IllegalArgumentException("Value data string improperly formatted");

		try {
			return new Money(Double.valueOf(split[0]),
					new Currency(split[1]));
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException(nfe);
		}
	}
}
