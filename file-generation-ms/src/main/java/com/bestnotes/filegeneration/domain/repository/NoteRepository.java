package com.bestnotes.filegeneration.domain.repository;

import com.bestnotes.filegeneration.domain.entity.Note;
import com.bestnotes.filegeneration.domain.entity.ProcessingType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, UUID> {

  Optional<Note> getNoteByIdAndUserId(UUID id, UUID userId);

  List<Note> getNoteByProcessingTypeIn(List<ProcessingType> processingTypes);

}
