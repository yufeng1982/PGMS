package com.photo.bas.core.validator.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.photo.bas.core.model.security.User;
import com.photo.bas.core.security.service.UserService;
import com.photo.bas.core.utils.EncodeUtils;
import com.photo.bas.core.utils.ResourceUtils;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.ThreadLocalUtils;

/**
 * @author FengYu
 */
@Component
public class UserValidator {

	@Autowired UserService userService;
	
	public void validateResetPassword(User user) {
		String oldPassword = user.getPlainPassword();
		if(!Strings.isEmpty(oldPassword)) {
			
			String inputPassword = userService.entryptPassword(oldPassword,EncodeUtils.decodeHex(user.getSalt()));
			if(inputPassword != null &&!inputPassword.equals(user.getPassword())) {
				ThreadLocalUtils.addErrorMsg(ResourceUtils.getText("User.Validat.Password.Different"));
			}
		}
		if(!userService.isPropertyUnique("loginName", user.getLoginName(), user.getId())) {
			ThreadLocalUtils.addErrorMsg(ResourceUtils.getText("User.Validat.Name.Unique"));
		}
	}
	public void validate(User user) {
		if(!Strings.isEmpty(user.getPlainPassword())) {
			if(!user.getPassword().equals(user.getPlainPassword())) {
				ThreadLocalUtils.addErrorMsg(ResourceUtils.getText("User.Validat.Password.Different"));
			}
		}
		if(!userService.isPropertyUnique("loginName", user.getLoginName(), user.getId())) {
			ThreadLocalUtils.addErrorMsg(ResourceUtils.getText("User.Validat.Name.Unique"));
		}
	}
}
