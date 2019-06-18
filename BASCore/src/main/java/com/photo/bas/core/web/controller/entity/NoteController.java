package com.photo.bas.core.web.controller.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.photo.bas.core.model.entity.SourceEntityType;
import com.photo.bas.core.model.log.Note;
import com.photo.bas.core.model.log.NoteType;
import com.photo.bas.core.service.log.NoteService;
import com.photo.bas.core.utils.Strings;

@Controller
@RequestMapping("/*/notes")
public class NoteController extends AbsListController<Note> {
	
	@Autowired private NoteService noteService;

	@Override
	protected NoteService getEntityService() {
		return noteService;
	}
	
	@RequestMapping("json")
	public ModelAndView json(@RequestParam(value = "entityId") String sourceEntityId,
			@RequestParam(value = "entityType") SourceEntityType entityType,
			@RequestParam(value = "isLastNote",required = false) boolean isLastNote) {
		JSONObject jso = new JSONObject();
		JSONArray array = new JSONArray();
		if(!Strings.isEmpty(sourceEntityId)){
			List<Note> notes = getEntityService().findEntityNotes(sourceEntityId, entityType);
			if (CollectionUtils.isNotEmpty(notes)) {
				List<Note> newNotes = new ArrayList<Note>();
				if(isLastNote){
					newNotes.add(notes.get(0));
				}else{
					newNotes = notes;
				}
				for (Note note : newNotes) {
					array.put(note.toJSONObject());
				}
			}
		}
		jso.put("data", array);
		return toJSONView(jso);
	}
	
	public Note buildNote(String sourceEntityId, String tempOwnerShipSourceEntityId, SourceEntityType entityType, NoteType noteType, String data) {
		Note note = new Note(sourceEntityId, entityType ,noteType, data);
		if(!Strings.isEmpty(tempOwnerShipSourceEntityId)) {
			note.setSourceId(tempOwnerShipSourceEntityId);
		}
		return note;
	}

	@RequestMapping("create")
	public ModelAndView create(@RequestParam(value = "entityId", required = false) String sourceEntityId,
			@RequestParam(value = "entityType", required = false) SourceEntityType entityType,
			@RequestParam(value = "noteType", required = false) NoteType noteType,
			@RequestParam(value = "data", required = false) String data,
			@RequestParam(value = "noteId", required = false) String noteId,
			@RequestParam(value = "tempOwnerShipSourceEntityId", required = false) String tempOwnerShipSourceEntityId) {
		Note note = null ;
		if(!Strings.isEmpty(noteId)){
			note = getEntityService().get(noteId);
			note.setType(noteType);
			note.setDescription(data);
		} else {
			note = buildNote(sourceEntityId, tempOwnerShipSourceEntityId, entityType, noteType, data);
		}
		
		Assert.notNull(note, "Provide note information");
		
		getEntityService().save(note);
		JSONObject returnValue = new JSONObject();
		returnValue.put("success", true);
		if(Strings.isEmpty(noteId)){
			returnValue.put("message", "Created new Note: " + note.getCode());
			returnValue.put("created", true);
			
		}else {
			returnValue.put("message", "Modified Note: " + note.getCode());
			returnValue.put("modified", true);
		}
		returnValue.put("data", note.toJSONObject());
		return toJSONView(returnValue);
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
	public ModelAndView delete(@PathVariable("id") String id) {
		Assert.notNull(id, "Identifier must be provided.");
		Note note = getEntityService().get(id);
		note.setActive(false);
		getEntityService().save(note);
		
		JSONObject returnValue = new JSONObject();
		returnValue.put("success", true);
		returnValue.put("id", id);
		return toJSONView(returnValue);
	}
}
