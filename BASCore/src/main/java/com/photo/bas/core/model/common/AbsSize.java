/**
 * 
 */
package com.photo.bas.core.model.common;

import javax.measure.unit.Unit;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.photo.bas.core.model.entity.AbsCodeNameEntity;
import com.photo.bas.core.utils.Calc;
import com.photo.bas.core.utils.UnitUserType;

/**
 * @author FengYu
 *
 */
@SuppressWarnings("rawtypes")
@TypeDef(name="unit", typeClass=UnitUserType.class)

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
@Table(name="size", schema = "public")
public class AbsSize  extends AbsCodeNameEntity {
	private static final long serialVersionUID = 8132881720849040300L;

	private Double width;
	private Double depth;
	private Double height;
	private Double perimeter;
	private Double volume;
	private Double weight;
	
	@Type(type="unit")
	private Unit perimeterUom;
	
	@Type(type="unit")
	private Unit widthUom;
	
	@Type(type="unit")
	private Unit weightUom ;
	
	@Type(type="unit")
    private Unit volumeUom;
	
	public AbsSize() {
		this("", "");
	}
	
	public AbsSize(String code, String name){
		super(code, name);
	}

	public String toString() {
		return getName();
	
	}

	public double getWidth() {
		return width == null ? 0f : width;
	}
	
	public void setWidth(Double width) {
		this.width = width;
	}
	
	public double getDepth() {
		return depth == null ? 0f : depth;
	}
	
	public void setDepth(Double depth) {
		this.depth = depth;
	}
	
	public double getHeight() {
		return height == null ? 0f : height;
	}
	
	public void setHeight(Double height) {
		this.height = height;
	}
	
	public double getPerimeter() {
		return perimeter == null ? 0f : perimeter;
	}
	
	public void setPerimeter(Double perimeter) {
		this.perimeter = perimeter;
	}
	
	public double getVolume() {
		return volume == null ? 0f : volume;
	}
	public void reSetVolume() {
		if(getVolume() == 0) {
			setVolume(Calc.mul(getHeight(), getWidth(), getDepth()));
		}
	}
	public void setVolume(Double volume) {
		this.volume = volume;
	}
	
	public double getWeight() {
		return weight == null ? 0f : weight;
	}
	
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	
	public Unit getPerimeterUom() {
		return perimeterUom;
	}
	
	public void setPerimeterUom(Unit perimeterUom) {
		this.perimeterUom = perimeterUom;
	}
	
	public Unit getWidthUom() {
		return widthUom;
	}
	
	public void setWidthUom(Unit widthUom) {
		this.widthUom = widthUom;
	}
	
	public Unit getWeightUom() {
		return weightUom;
	}
	
	public void setWeightUom(Unit weightUom) {
		this.weightUom = weightUom;
	}
	
	public Unit getVolumeUom() {
		return volumeUom;
	}
	
	public void setVolumeUom(Unit volumeUom) {
		this.volumeUom = volumeUom;
	}

	@Override
	public String getSavePermission() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDeletePermission() {
		// TODO Auto-generated method stub
		return null;
	}
}
