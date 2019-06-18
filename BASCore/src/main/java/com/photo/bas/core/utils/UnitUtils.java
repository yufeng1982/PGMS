/**
 * 
 */
package com.photo.bas.core.utils;

import java.util.ArrayList;
import java.util.List;

import javax.measure.quantity.Area;
import javax.measure.quantity.Dimensionless;
import javax.measure.quantity.Duration;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Volume;
import javax.measure.unit.NonSI;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;

import com.photo.bas.core.model.common.CNonSI;
import com.photo.bas.core.model.common.CSI;
import com.photo.bas.core.model.common.UnitType;

/**
 * @author FengYu
 *
 */
@SuppressWarnings("rawtypes")
public class UnitUtils {
	
	public static final Unit DEFAULT_APP_WEIGHT_UOM = Unit.valueOf("lb");
	public static final Unit DEFAULT_APP_VOLUME_UOM = Unit.valueOf("ft^3");
	public static final Unit DEFAULT_APP_LENGTH_UOM = Unit.valueOf("ft");

	public static Double convertUnitValue(Unit originalUnit, Unit targetUnit, Double originalVal) {
		if(originalVal == null || originalVal == 0) return 0.0;
		if(originalUnit == null || targetUnit == null) return originalVal;
		return originalUnit.equals(targetUnit) ? originalVal : new ValueUnit(originalVal, originalUnit).convertDataUnit(targetUnit).getValue();
	}

	public static List getUnitList(UnitType unitType){
		switch(unitType){
		case AREA:
			return getAreaUnitList();
		case LENGTH:
			return getLengthUnitList();
		case WEIGHT:
			return getMassUnitList();
		case EACH:
			return getDimensionlessUnitList();
		case VOLUME:
			return getVolumeUnitList();
		}
		return new ArrayList();
	}
	
	public static List<Unit<Length>> getLengthUnitList(){
		List<Unit<Length>> list = new ArrayList<Unit<Length>>();
		list.add(SI.METER);
		list.add(SI.KILOMETER);
		list.add(SI.CENTIMETER);
		list.add(SI.MILLIMETER);

		list.add(NonSI.INCH);
		list.add(NonSI.FOOT);
		list.add(NonSI.MILE);
		list.add(NonSI.YARD);
		
		return list;
	}
	
	public static List<Unit<Mass>> getMassUnitList(){
		List<Unit<Mass>> list = new ArrayList<Unit<Mass>>();
		list.add(SI.GRAM);
		list.add(SI.KILOGRAM);
		
//		list.add(NonSI.OUNCE);
		list.add(NonSI.POUND);
		list.add(NonSI.METRIC_TON);
		list.add(NonSI.TON_US);
		
		return list;
	}
	
	public static List<Unit<Dimensionless>> getDimensionlessUnitList(){
		List<Unit<Dimensionless>> list = new ArrayList<Unit<Dimensionless>>();
		list.add(Unit.ONE);
		return list;
	}
	
	public static List<Unit<Area>> getAreaUnitList(){
		List<Unit<Area>> list = new ArrayList<Unit<Area>>();
		list.add(SI.SQUARE_METRE);
		list.add(NonSI.ARE);
		list.add(NonSI.HECTARE);
		
		list.add(CSI.SQUARE_MILLIMETER);
		list.add(CSI.SQUARE_CENTIMETER);
		list.add(CSI.SQUARE_KILOMETER);
		
		list.add(CNonSI.SQUARE_FOOT);
		list.add(CNonSI.SQUARE_MILE);
		list.add(CNonSI.SQUARE_YARD);
		
		return list;
	}
	
	public static List<Unit<Volume>> getVolumeUnitList(){
		List<Unit<Volume>> list = new ArrayList<Unit<Volume>>();
		list.add(SI.CUBIC_METRE);
		list.add(NonSI.CUBIC_INCH);
		list.add(NonSI.GALLON_DRY_US);
		list.add(NonSI.GALLON_LIQUID_US);
		list.add(NonSI.LITER);
		
		list.add(CSI.CUBIC_MILLIMETER);
		list.add(CSI.CUBIC_CENTIMETER);
		list.add(CSI.CUBIC_KILOMETER);
		
		list.add(CNonSI.CUBIC_FOOT);
		list.add(CNonSI.CUBIC_MILE);
		list.add(CNonSI.CUBIC_YARD);
		
		return list;
	}
	
	public static List<Unit<Duration>> getTimeUnitList(){
		List<Unit<Duration>> list = new ArrayList<Unit<Duration>>();
		list.add(NonSI.DAY);
		list.add(NonSI.MONTH);
		list.add(NonSI.YEAR);
		return list;
	}
	
	private static boolean isExist(Unit unit, List list){
		for(Object obj : list){
			if(obj.equals(unit)) return true;
		}
		return false;
	}
	
	public static UnitType getUnitType(Unit unit){
		if(isExist(unit, getAreaUnitList())) return UnitType.AREA;
		else if(isExist(unit, getLengthUnitList())) return UnitType.LENGTH;
		else if(isExist(unit, getMassUnitList())) return UnitType.WEIGHT;
		else if(isExist(unit, getVolumeUnitList())) return UnitType.VOLUME;
		else return  UnitType.EACH;
	}
	
	public static Unit getVolumeUnit(Unit unit){
		Unit volumeUnit = null;
		
		if(unit.equals(SI.METER)){
			volumeUnit = SI.CUBIC_METRE;
		}else if(unit.equals(SI.KILOMETER)){
			volumeUnit = CSI.CUBIC_KILOMETER;
		}else if(unit.equals(SI.CENTIMETER)){
			volumeUnit = CSI.CUBIC_CENTIMETER;
		}else if(unit.equals(SI.MILLIMETER)){
			volumeUnit = CSI.CUBIC_MILLIMETER;
		
		}else if(unit.equals(NonSI.INCH)){
			volumeUnit = NonSI.CUBIC_INCH;
		}else if(unit.equals(NonSI.FOOT)){
			volumeUnit = CNonSI.CUBIC_FOOT;
		}else if(unit.equals(NonSI.MILE)){
			volumeUnit =CNonSI.CUBIC_MILE;
		}else if(unit.equals(NonSI.YARD)){
			volumeUnit = CNonSI.CUBIC_YARD;
		}
		
		return volumeUnit;
	}
}
