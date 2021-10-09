package com.bestnotes.notes.messaging.publisher;

import com.bestnotes.notes.domain.entity.Note;

public interface NoteMessagePublisher {

  void publishNoteCreatedMessage(final Note note);

  void publishNoteUpdatedMessage(final Note note);

}
