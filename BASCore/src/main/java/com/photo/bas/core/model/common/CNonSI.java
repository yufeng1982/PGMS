package com.photo.bas.core.model.common;

import static javax.measure.unit.NonSI.FOOT;
import static javax.measure.unit.NonSI.MILE;
import static javax.measure.unit.NonSI.YARD;

import javax.measure.quantity.Area;
import javax.measure.quantity.Volume;
import javax.measure.unit.ProductUnit;
import javax.measure.unit.Unit;

public class CNonSI {
	
	
	public static final Unit<Area> SQUARE_FOOT = new ProductUnit<Area>(FOOT.times(FOOT));
	
	public static final Unit<Area> SQUARE_MILE = new ProductUnit<Area>(MILE.times(MILE));
	
	public static final Unit<Area> SQUARE_YARD = new ProductUnit<Area>(YARD.times(YARD));
	
	
	public static final Unit<Volume> CUBIC_FOOT = new ProductUnit<Volume>(SQUARE_FOOT.times(FOOT));
	
	public static final Unit<Volume> CUBIC_MILE = new ProductUnit<Volume>(SQUARE_MILE.times(MILE));

	public static final Unit<Volume> CUBIC_YARD = new ProductUnit<Volume>(SQUARE_YARD.times(YARD));
	
}
