/**
 * 
 */
package com.photo.bas.core.utils;

import javax.measure.unit.Unit;

import org.jscience.physics.amount.Amount;

/**
 * @author FengYu
 *
 */
@SuppressWarnings({"rawtypes", "unused", "unchecked"})
public class ValueUnit implements Comparable<ValueUnit> {

	private Double value;
	private Unit unit;

	public ValueUnit(Double value, Unit unit) {
		super();
		if (value == null) throw new IllegalArgumentException("Data cannot be null!");
		if (unit == null) throw new IllegalArgumentException("Unit cannot be null!");
		this.value = value;
		this.unit = unit;
	}
	private ValueUnit() {
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

	public final Unit getUnit() {
		return unit;
	}

	public final ValueUnit convertDataUnit(Unit desiredUnit) {
		if (getUnit().equals(desiredUnit)) {
			return new ValueUnit(getValue(), getUnit());
		} else {
			if (getUnit().isCompatible(desiredUnit)) {
				return new ValueUnit(getUnit().getConverterTo(desiredUnit).convert(getValue()), desiredUnit);
			}
			else {
				throw new ClassCastException("Incompatible units for conversion");
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof ValueUnit)) return false;

		ValueUnit data = (ValueUnit) obj;
		try {
			data = data.convertDataUnit(this.getUnit());
			Double v1 = getValue();
			Double v2 = data.getValue();

			if (v1 == v2 || (v1 != null && v1.equals(v2))) return true;

		} catch (ClassCastException e) {
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

	public int compareTo(ValueUnit data) {
		data = data.convertDataUnit(this.getUnit());

		Double v1 = getValue();
		Double v2 = data.getValue();

		return Double.compare(v1, v2);
	}

	public static final ValueUnit parseUomData(String uomComponentStr)
			throws IllegalArgumentException {
		uomComponentStr = uomComponentStr.replace('[', ' ');
		uomComponentStr = uomComponentStr.replace(']', ' ');
		uomComponentStr = uomComponentStr.trim();

		String[] split = uomComponentStr.split(",");

		if (split.length != 2)
			throw new IllegalArgumentException("Value data string improperly formatted");

		try {
			return new ValueUnit(Double.valueOf(split[0]), Unit.valueOf(split[1]));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
	}
}