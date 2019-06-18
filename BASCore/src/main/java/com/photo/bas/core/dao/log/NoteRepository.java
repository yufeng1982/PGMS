/**
 * 
 */
package com.photo.bas.core.dao.log;

import java.util.List;

import com.photo.bas.core.dao.entity.AbsCodeNameEntityRepository;
import com.photo.bas.core.model.entity.SourceEntityType;
import com.photo.bas.core.model.log.Note;

/**
 * @author FengYu
 *
 */
public interface NoteRepository extends AbsCodeNameEntityRepository<Note> {
	
	public List<Note> findAllBySourceIdAndSourceTypeAndActiveTrueOrderByModificationDateDesc(String sourceId, SourceEntityType sourceType);
}
