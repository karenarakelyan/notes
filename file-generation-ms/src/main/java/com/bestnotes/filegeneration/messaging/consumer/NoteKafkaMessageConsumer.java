package com.bestnotes.filegeneration.messaging.consumer;

import com.bestnotes.filegeneration.domain.service.NoteService;
import com.bestnotes.filegeneration.messaging.message.NoteMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NoteKafkaMessageConsumer {

  private static final String CREATION_TOPIC = "note_created";

  private static final String UPDATE_TOPIC = "note_updated";

  private final NoteService noteService;

  public NoteKafkaMessageConsumer(final NoteService noteService) {
    this.noteService = noteService;
  }

  @KafkaListener(topics = CREATION_TOPIC)
  public void consumeCreate(NoteMessage message) {
    noteService.create(message.getId(), message.getUserId());
  }

  @KafkaListener(topics = UPDATE_TOPIC)
  public void consumeUpdate(NoteMessage message) {
    noteService.update(message.getId(), message.getUserId());
  }

}
