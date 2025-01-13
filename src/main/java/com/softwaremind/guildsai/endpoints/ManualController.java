package com.softwaremind.guildsai.endpoints;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.softwaremind.guildsai.manual.ManualUploader;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ManualController {
  private final ManualUploader uploader;

  @PostMapping("/api/manual")
  public String assistant(@RequestBody ManualUploadRequest request) {
    System.out.println(request);
    return uploader.upload(request);
  }
}
