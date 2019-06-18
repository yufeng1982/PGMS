/**
 * 
 */
package com.photo.pgm.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.security.FunctionNode;
import com.photo.bas.core.model.security.User;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.web.controller.entity.AbsCodeEntityController;
import com.photo.pgm.core.model.Asset;
import com.photo.pgm.core.model.UserProject;
import com.photo.pgm.core.service.AssetService;
import com.photo.pgm.core.service.UserProjectService;

/**
 * @author FengYu
 *
 */
@Controller
@RequestMapping("/pgm/project/asset/form")
public class AssetController extends AbsCodeEntityController<Asset> {

	private final static String PAGE_FORM_PATH = "project/asset/asset";
	private final static String PATH = "project/asset/form/";
	
	@Autowired private AssetService assetService;
	@Autowired private UserProjectService userProjectService;
	
	@RequestMapping(value = "{id}/show")
	@RequiresPermissions("asset:show")
	public String show(@ModelAttribute("entity") Asset asset, ModelMap modelMap) {
		User user = ThreadLocalUtils.getCurrentUser();
		if (asset.isNewEntity()) {
			asset.setDepartment(user.getDepartment());
		}
		List<UserProject> upList = userProjectService.findByUsersAndCorporation(user, ThreadLocalUtils.getCurrentCorporation());
		StringBuffer ids = new StringBuffer();
		for (UserProject up : upList) {
			if (ids.length() == 0) {
				ids.append(up.getPetrolStation().getId());
			} else {
				ids.append(",").append(up.getPetrolStation().getId());
			}
		}
		modelMap.addAttribute("ids", ids.toString());
		return PAGE_FORM_PATH;
	}
	
	@RequestMapping("{id}/apply")
	@RequiresPermissions("asset:apply")
	public ModelAndView save(HttpServletRequest request, @ModelAttribute("entity") Asset asset, ModelMap modelMap) {
		saveAsset(asset);
		return redirectTo(PATH + asset.getId() + "/show", modelMap);
	}

	@RequestMapping("{id}/ok")
	@RequiresPermissions("asset:ok")
	public ModelAndView ok(HttpServletRequest request, @ModelAttribute("entity") Asset asset, ModelMap modelMap) {
		if(saveAsset(asset)){
			return closePage(modelMap);
		}
		return redirectTo(PATH + (Strings.isEmpty(asset.getId()) ? NEW_ENTITY_ID : asset.getId()) + "/show", modelMap);
	}
	
	private boolean saveAsset(Asset asset) {
		boolean isSucceed = !hasErrorMessages();
		if (isSucceed) {
			if (asset.isNewEntity()) {
				// 给合同添加序号
				String seqNo = assetService.getSequeneNumber("project.asset_sequence", asset.getPetrolStation().getProject().getId());
				asset.setSeq(assetService.findMaxSeq(asset.getCorporation().getId()) + 1);
				asset.setCode(asset.getAssetsCategory().getCode() + "-" + seqNo);
			}
			if (asset.getContract() == null) {
				asset.setDepartment(ThreadLocalUtils.getCurrentUser().getDepartment());
			}
			asset.setName(asset.getAssetsCategory().getName());
			getEntityService().save(asset);
		}
		return isSucceed;
	}
	
	@Override
	protected AssetService getEntityService() {
		return assetService;
	}

	@Override
	protected Asset newInstance(WebRequest request) {
		return new Asset();
	}

	@Override
	protected FunctionNode getBelongToFunctionNode() {
		return functionNodeManager.getFunctionNode("ZC1");
	}
	
}
