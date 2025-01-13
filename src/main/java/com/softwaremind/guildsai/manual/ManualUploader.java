package com.softwaremind.guildsai.manual;

import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import com.softwaremind.guildsai.endpoints.ManualUploadRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManualUploader {
  private final ManualReader manualReader;
  private final VectorStore vectorStore;
  private final TokenTextSplitter tokenTextSplitter;

  public String upload(ManualUploadRequest request) {
    var manual = manualReader.getDocsFromPdf(request.pdf());
    var splitTokens = tokenTextSplitter.split(manual);
    vectorStore.accept(splitTokens);
    log.info("Added the manual for {} to VS.", request.model());

    return "Uploaded manual: " + request;
  }
}
