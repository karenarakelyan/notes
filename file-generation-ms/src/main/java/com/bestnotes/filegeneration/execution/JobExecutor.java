package com.bestnotes.filegeneration.execution;

import com.bestnotes.filegeneration.client.NoteServiceClient;
import com.bestnotes.filegeneration.domain.entity.Note;
import com.bestnotes.filegeneration.domain.service.NoteService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class JobExecutor {

  private final NoteService noteService;

  private final NoteServiceClient noteServiceClient;

  private final FileWriter fileWriter;

  public JobExecutor(final NoteService noteService, final NoteServiceClient noteServiceClient,
      final FileWriter fileWriter) {
    this.noteService = noteService;
    this.noteServiceClient = noteServiceClient;
    this.fileWriter = fileWriter;
  }

  @Scheduled(fixedRate = 3600000)
  public void execute() {
    Flux<Note> candidatesForProcessing = noteService.getCandidatesForProcessing();
    candidatesForProcessing
        .flatMap(note -> noteServiceClient.get(note.getId(), note.getUserId()))
        .collectList()
        .map(list -> {
          fileWriter.write(list);
          return Mono.empty();
        })
        .subscribe();
  }

}
