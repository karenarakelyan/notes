package com.bestnotes.notes.messaging.publisher;

import com.bestnotes.notes.domain.entity.Note;
import com.bestnotes.notes.messaging.message.NoteMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class NoteKafkaMessagePublisher implements NoteMessagePublisher {

  private static final String CREATION_TOPIC = "note_created";

  private static final String UPDATE_TOPIC = "note_updated";

  private final KafkaTemplate<String, NoteMessage> kafkaTemplate;

  public NoteKafkaMessagePublisher(
      final KafkaTemplate<String, NoteMessage> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void publishNoteCreatedMessage(final Note note) {
    final NoteMessage noteMessage = createNoteMessage(note);
    kafkaTemplate.send(CREATION_TOPIC, noteMessage);
  }

  @Override
  public void publishNoteUpdatedMessage(final Note note) {
    final NoteMessage noteMessage = createNoteMessage(note);
    kafkaTemplate.send(UPDATE_TOPIC, noteMessage);
  }

  private NoteMessage createNoteMessage(final Note note) {
    final NoteMessage noteMessage = new NoteMessage();
    noteMessage.setId(note.getId());
    noteMessage.setUserId(note.getUserId());
    noteMessage.setTitle(note.getTitle());
    noteMessage.setContent(note.getContent());
    noteMessage.setCreatedOn(note.getCreatedOn());
    noteMessage.setUpdatedOn(note.getUpdatedOn());
    return noteMessage;
  }

}
