package com.softwaremind.guildsai.assistant;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.softwaremind.guildsai.endpoints.TankQuestionRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TankAssistant {
  private static final String QUESTION_TEMPLATE = """
      The question concerns the following model: %s.
      %s
      """;

  private final ChatClient chatClient;

  public String ask(TankQuestionRequest request) {
    return chatClient
        .prompt()
        .user(QUESTION_TEMPLATE.formatted(request.model(), request.question()))
        .call()
        .content();
  }
}
