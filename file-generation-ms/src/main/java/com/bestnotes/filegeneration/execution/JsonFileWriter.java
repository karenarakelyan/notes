package com.bestnotes.filegeneration.execution;

import com.bestnotes.filegeneration.client.dto.NoteResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JsonFileWriter implements FileWriter {

  private static java.io.FileWriter fileWriter;

  private final ObjectMapper objectMapper;
  @Value("${output.directoryPath}")
  private String outputDirectoryPath;

  public JsonFileWriter(final ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public void write(final List<NoteResponse> noteResponses) {

    List<String> dataAsJson = convertDataToJson(noteResponses);
    dataAsJson.stream().forEach(data -> {
      File path = getPath();
      try {
        fileWriter = new java.io.FileWriter(path);
        fileWriter.write(data);
      } catch (IOException e) {
        throw new RuntimeException(e);
      } finally {
        try {
          fileWriter.flush();
          fileWriter.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });

  }

  private List<String> convertDataToJson(final List<NoteResponse> noteResponses) {
    return noteResponses.stream()
        .map(r -> {
          try {
            return objectMapper.writeValueAsString(r);
          } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
          }
        })
        .collect(Collectors.toList());
  }

  private File getPath() {
    String outputFilePath = outputDirectoryPath + "/" + System.currentTimeMillis() + ".parquet";
    File outputFile = new File(outputFilePath);
    return outputFile;
  }
}
