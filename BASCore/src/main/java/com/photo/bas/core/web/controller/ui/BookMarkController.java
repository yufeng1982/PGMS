package com.photo.bas.core.web.controller.ui;

import java.io.Writer;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.photo.bas.core.model.common.State;
import com.photo.bas.core.service.common.StateService;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.ThreadLocalUtils;
import com.photo.bas.core.web.controller.entity.AbsController;


/**
 * @author FengYu
 */
@Controller
@RequestMapping("/*/ui/bookMark")
public class BookMarkController extends AbsController<State> {

	@Autowired private StateService stateService;

	@Override
	protected StateService getEntityService() {
		return stateService;
	}
	
	@RequestMapping("add")
	public void add(@RequestParam(value="name") String name, @RequestParam(value="tag") String tag, @RequestParam(value="url") String url, Writer writer) throws Exception {
		
		JSONArray jaBookMark = getEntityService().getCurrentUserState(State.BOOK_MARK_PAGE_NAME, State.BOOK_MARK_SCOPE_OBJECT_TYPE, ThreadLocalUtils.getCurrentUser(), ThreadLocalUtils.getCurrentCorporation());
		if(jaBookMark.length() == 0) {
			JSONObject joBookMark = new JSONObject();
			joBookMark.put("name", State.BOOK_MARK_SCOPE_OBJECT_TYPE);
			joBookMark.put("value", new JSONArray());
			jaBookMark.put(joBookMark);
		}
		for (int i = 0; i < jaBookMark.length(); i++) {
			JSONObject joBookMark = jaBookMark.getJSONObject(i);
			if(State.BOOK_MARK_SCOPE_OBJECT_TYPE.equals(joBookMark.getString("name"))) {
				JSONArray newBookMarks = removeBookMarkByUrl(name, tag, url, joBookMark);
				
				JSONObject newBookMark = new JSONObject();
				newBookMark.put("name", name);
				newBookMark.put("tag", tag.trim());
				newBookMark.put("url", url);
				
				newBookMarks.put(newBookMark);
				
				joBookMark.remove("value");
				joBookMark.put("value", newBookMarks);
			}
		}
		getEntityService().saveUserStates(jaBookMark, State.BOOK_MARK_PAGE_NAME, State.BOOK_MARK_SCOPE_OBJECT_TYPE, ThreadLocalUtils.getCurrentUser(), ThreadLocalUtils.getCurrentCorporation());
		writer.write(resultOK("BookMark_" + Math.abs(url.hashCode())));
	}
	
	@RequestMapping("delete")
	public void delete(@RequestParam(value="url") String url, Writer writer) throws Exception {
		JSONArray jaBookMark = getEntityService().getCurrentUserState(State.BOOK_MARK_PAGE_NAME, State.BOOK_MARK_SCOPE_OBJECT_TYPE, ThreadLocalUtils.getCurrentUser(), ThreadLocalUtils.getCurrentCorporation());
		for (int i = 0; i < jaBookMark.length(); i ++) {
			JSONObject joBookMark = jaBookMark.getJSONObject(i);
			if(State.BOOK_MARK_SCOPE_OBJECT_TYPE.equals(joBookMark.getString("name"))) {
				
				JSONArray newBookMarks = removeBookMarkByUrl("", "", url, joBookMark);
				joBookMark.remove("value");
				joBookMark.putOpt("value", newBookMarks);
			}
		}
		getEntityService().saveUserStates(jaBookMark, State.BOOK_MARK_PAGE_NAME, State.BOOK_MARK_SCOPE_OBJECT_TYPE, ThreadLocalUtils.getCurrentUser(), ThreadLocalUtils.getCurrentCorporation());
		writer.write(resultOK());
	}

	private JSONArray removeBookMarkByUrl(String name, String tag, String url, JSONObject joBookMark) throws ParseException {
		JSONArray oldBookMarks = new JSONArray(joBookMark.getString("value"));
		JSONArray newBookMarks = new JSONArray();
		for (int j = 0; j < oldBookMarks.length(); j ++) {
			JSONObject bookMark = oldBookMarks.getJSONObject(j);
			String tempName = bookMark.getString("name");
			String tempUrl = bookMark.getString("url");
			String tempTag = bookMark.getString("tag");
			if(tempTag == null) tempTag = "";
			if(tempName == null) tempName = "";
			if(!tempUrl.equals(url) && !(tempName.equals(name) && tempTag.equals(tag))) {
				newBookMarks.put(bookMark);
			}
		}
		return newBookMarks;
	}
	
	@RequestMapping(value = "tags")
	public void getBookMarkTags(@RequestParam("query") String queryStr, Writer writer) throws Exception {
		String tagCode = Strings.isEmpty(queryStr) ? "" : queryStr.trim();
		Set<String> firstLevelFolders = new TreeSet<String>();
		JSONArray jaBookMark = getEntityService().getCurrentUserState(State.BOOK_MARK_PAGE_NAME, State.BOOK_MARK_SCOPE_OBJECT_TYPE, ThreadLocalUtils.getCurrentUser(), ThreadLocalUtils.getCurrentCorporation());
		for (int i = 0; i < jaBookMark.length(); i ++) {
			JSONObject joBookMark = jaBookMark.getJSONObject(i);
			if(State.BOOK_MARK_SCOPE_OBJECT_TYPE.equals(joBookMark.getString("name"))) {
				JSONArray bookMarks = new JSONArray(joBookMark.getString("value"));
				for (int j = 0; j < bookMarks.length(); j ++) {
					JSONObject bookMark = bookMarks.getJSONObject(j);
					String tagName = bookMark.getString("tag");
					if(!Strings.isEmpty(tagName) && (Strings.isEmpty(tagCode) || tagName.indexOf(tagCode) >= 0)) {
						firstLevelFolders.add(tagName);
					}
				}
			}
		}
		JSONObject tags = new JSONObject();
		JSONArray jaTags = new JSONArray();
		
		Iterator<String> tagIterator = firstLevelFolders.iterator();
		while (tagIterator.hasNext()) {
			String type = tagIterator.next();
			JSONObject tag = new JSONObject();
			tag.put("id", type);
			tag.put("code", type);
			jaTags.put(tag);
		}
		tags.put("tags", jaTags);
		writer.write(tags.toString());
	}
	
	@RequestMapping(value = "isExist")
	public void isTagExist(@RequestParam("url") String url, Writer writer) throws Exception {
		boolean isExist = false;
		JSONArray jaBookMark = getEntityService().getCurrentUserState(State.BOOK_MARK_PAGE_NAME, State.BOOK_MARK_SCOPE_OBJECT_TYPE, ThreadLocalUtils.getCurrentUser(), ThreadLocalUtils.getCurrentCorporation());
		for (int i = 0; i < jaBookMark.length(); i ++) {
			JSONObject joBookMark = jaBookMark.getJSONObject(i);
			if(State.BOOK_MARK_SCOPE_OBJECT_TYPE.equals(joBookMark.getString("name"))) {
				JSONArray bookMarks = new JSONArray(joBookMark.getString("value"));
				for (int j = 0; j < bookMarks.length(); j ++) {
					JSONObject bookMark = bookMarks.getJSONObject(j);
					if(bookMark.getString("url").equals(url)) {
						isExist = true;
						break;
					}
				}
			}
		}
		writer.write(isExist ? resultOK() : resultNo());
	}
	
	private String resultNo() {
		JSONObject r = new JSONObject();
		r.put("value", "no");
		return r.toString();
	}
	private String resultOK(String id) {
		JSONObject r = new JSONObject();
		if(!Strings.isEmpty(id)) r.put("id", id);
		r.put("value", "ok");
		return r.toString();
	}
	private String resultOK() {
		return resultOK("");
	}
}
