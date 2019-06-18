package com.photo.bas.core.model.common;

import java.util.EnumSet;

import com.photo.bas.core.model.entity.IEnum;
import com.photo.bas.core.utils.ResourceUtils;


	public enum Question implements IEnum {
		
		Pet, Mother, PrimarySchool, Love;

		@Override
		public String getKey() {
			return new StringBuffer().append("Question.").append(name()).toString();
		}

		@Override
		public String getText() {
			return ResourceUtils.getText(getKey());
		}

		@Override
		public String getName() {
			return name();
		}
		public static EnumSet<Question> getQuestions() {
			return EnumSet.allOf(Question.class);
		}
}
