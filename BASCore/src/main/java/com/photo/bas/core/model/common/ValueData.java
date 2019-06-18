package com.photo.bas.core.model.common;

import javax.measure.unit.Unit;

import org.jscience.physics.amount.Amount;

@SuppressWarnings({ "rawtypes", "unchecked", "unused"})
public class ValueData implements Comparable<ValueData> {

	private Double value;
	private Unit unit;

	public ValueData(Double value, Unit unit) {
		super();

		if (value == null) throw new IllegalArgumentException("Data cannot be null!");
		if (unit == null) throw new IllegalArgumentException("Unit cannot be null!");

		this.value = value;
		this.unit = unit;
	}

	private ValueData() {
		super();
	}
	private final void setValue(Double value) {
		this.value = value;
	}
	public final Double getValue() {
		return value;
	}
	private final void setUnit(Unit unit) {
		this.unit = unit;
	}

	/**
	 * @return the unit
	 */
	public final Unit getUnit() {
		return unit;
	}
	public final ValueData convertDataUnit(Unit desiredUnit) {
		if (getUnit().equals(desiredUnit)) {
			return new ValueData(getValue(), getUnit());
		} else {
			if (getUnit().isCompatible(desiredUnit)) {
				return new ValueData(getUnit().getConverterTo(desiredUnit)
						.convert(getValue()), desiredUnit);
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

		if (!(obj instanceof ValueData))
			return false;

		ValueData data = (ValueData) obj;

		try {
			data = data.convertDataUnit(this.getUnit());
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
		return "[" + getValue().toString() + ", " + getUnit().toString() + "]";
	}
	
	public Amount toAmount(){
		return Amount.valueOf(value, unit);
	}

	public int compareTo(ValueData data) {
		data = data.convertDataUnit(this.getUnit());

		Double v1 = getValue();
		Double v2 = data.getValue();

		return Double.compare(v1, v2);
	}

	public static final ValueData parseUomData(String uomComponentStr)
			throws IllegalArgumentException {
		uomComponentStr = uomComponentStr.replace('[', ' ');
		uomComponentStr = uomComponentStr.replace(']', ' ');
		uomComponentStr = uomComponentStr.trim();

		String[] split = uomComponentStr.split(",");

		if (split.length != 2)
			throw new IllegalArgumentException("Value data string improperly formatted");

		try {
			return new ValueData(Double.valueOf(split[0]), Unit.valueOf(split[1]));
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException(nfe);
		}
	}
}
