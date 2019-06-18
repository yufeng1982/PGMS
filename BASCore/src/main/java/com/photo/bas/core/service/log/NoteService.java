/**
 * 
 */
package com.photo.bas.core.service.log;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.photo.bas.core.dao.log.NoteRepository;
import com.photo.bas.core.model.entity.SourceEntityType;
import com.photo.bas.core.model.log.Note;
import com.photo.bas.core.service.entity.AbsCodeNameEntityService;
import com.photo.bas.core.utils.PageInfo;

/**
 * @author FengYu
 *
 */
@Component
public class NoteService extends AbsCodeNameEntityService<Note, PageInfo<Note>> {

	@Autowired private NoteRepository noteRepository;

	@Override
	protected NoteRepository getRepository() {
		return noteRepository;
	}

	@Override
	public boolean isCommonAccess() {
		return false;
	}

	public List<Note> findEntityNotes(String sourceId, SourceEntityType sourceType) {
		return getRepository().findAllBySourceIdAndSourceTypeAndActiveTrueOrderByModificationDateDesc(sourceId, sourceType);
	}

}
