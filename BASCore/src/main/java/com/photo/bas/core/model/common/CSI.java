package com.photo.bas.core.model.common;

import static javax.measure.unit.SI.CENTIMETER;
import static javax.measure.unit.SI.KILOMETER;
import static javax.measure.unit.SI.MILLIMETER;

import javax.measure.quantity.Area;
import javax.measure.quantity.Volume;
import javax.measure.unit.ProductUnit;
import javax.measure.unit.Unit;
public class CSI {

	public static final Unit<Area> SQUARE_KILOMETER = new ProductUnit<Area>(KILOMETER.times(KILOMETER));
	
	public static final Unit<Area> SQUARE_CENTIMETER = new ProductUnit<Area>(CENTIMETER.times(CENTIMETER));
	
	public static final Unit<Area> SQUARE_MILLIMETER = new ProductUnit<Area>(MILLIMETER.times(MILLIMETER));
	
	
	public static final Unit<Volume> CUBIC_KILOMETER =  new ProductUnit<Volume>(SQUARE_KILOMETER.times(KILOMETER));
	
	public static final Unit<Volume> CUBIC_CENTIMETER =  new ProductUnit<Volume>(SQUARE_CENTIMETER.times(CENTIMETER));
	
	public static final Unit<Volume> CUBIC_MILLIMETER =  new ProductUnit<Volume>(SQUARE_MILLIMETER.times(MILLIMETER));

}
