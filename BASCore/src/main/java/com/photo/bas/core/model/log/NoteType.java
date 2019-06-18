package com.photo.bas.core.model.log;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.photo.bas.core.model.entity.IEnum;
import com.photo.bas.core.utils.ResourceUtils;

/**
 * 
 * @author FengYu
 */
public enum NoteType implements IEnum {
	General, PurchaseNote, LocationDefault,
	PackagingInstructions, PackagedFor, CADRemark, CrossRef, CornerPadRef, WQPrt, PricingRemark;
	
	public static EnumSet<NoteType> getNoteTypes() {
		return EnumSet.allOf(NoteType.class);
	}
	
	private static final List<NoteType> ITEMTYPES = new ArrayList<NoteType> ();
	static {
		ITEMTYPES.add(LocationDefault);
		ITEMTYPES.add(PackagingInstructions);
		ITEMTYPES.add(PackagedFor);
		ITEMTYPES.add(CADRemark);
		ITEMTYPES.add(CrossRef);
		ITEMTYPES.add(CornerPadRef);
		ITEMTYPES.add(WQPrt);
		ITEMTYPES.add(PricingRemark);
	}
	
	public static List<NoteType> getSalesNoteTypeList(){
		
		List<NoteType> list = new ArrayList<NoteType> ();
		list.add(General);
		
		return list;
	}
	public static List<NoteType> getItemNoteTypeList(){
		return ITEMTYPES;
	}

	NoteType() {}
	
	public String getKey() {
		return new StringBuffer().append("NoteType.").append(name()).toString();
	}

	public String toString() {
		return getKey();
	}
	
	public String getText() {
		return ResourceUtils.getText(getKey());
	}

	@Override
	public String getName() {
		return name();
	}
}
