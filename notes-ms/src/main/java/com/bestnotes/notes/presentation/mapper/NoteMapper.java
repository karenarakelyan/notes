package com.bestnotes.notes.presentation.mapper;

import com.bestnotes.notes.domain.entity.Note;
import com.bestnotes.notes.presentation.dto.response.NoteResponse;

public class NoteMapper {

  public static NoteResponse mapToResponse(final Note note) {
    return NoteResponse.NoteResponseBuilder.aNoteResponse()
        .withId(note.getId())
        .withUserId(note.getUserId())
        .withTitle(note.getTitle())
        .withNote(note.getContent())
        .withCreatedOn(note.getCreatedOn())
        .withUpdatedOn(note.getUpdatedOn())
        .build();
  }

}
