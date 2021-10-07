package com.bestnotes.notes.domain.repository;

import com.bestnotes.notes.domain.entity.Note;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Note, UUID> {

  Optional<Note> findByIdAndUserId(UUID id, UUID userId);

  List<Note> findByUserId(UUID userId);

  void deleteByIdAndUserId(UUID id, UUID userId);

}
