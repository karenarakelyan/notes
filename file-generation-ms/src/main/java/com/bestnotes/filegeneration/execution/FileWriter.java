package com.bestnotes.filegeneration.execution;

import com.bestnotes.filegeneration.client.dto.NoteResponse;
import java.util.List;

public interface FileWriter {

  void write(List<NoteResponse> noteResponses);

}
