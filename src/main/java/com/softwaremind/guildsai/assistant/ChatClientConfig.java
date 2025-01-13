package com.softwaremind.guildsai.assistant;

import java.time.LocalDateTime;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.softwaremind.guildsai.tank_lessons.LessonSlotsNearCity;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ChatClientConfig {
  private static final String SYSTEM_MSG = """
      You are an expert Armored Division mechanic who works in a Tank Driving school.
      Answer questions about proper tank operating procedures and maintenance according to Army regulations, and help train new tank drivers.
      Help the trainees find tank driving lessons that suit their needs.
      Your answers should be detailed and accurate.
      """;

  @Bean
  ChatClient chatClient(ChatClient.Builder builder, VectorStore vectorStore) {
    var defaultConfig = ChatOptionsBuilder.builder()
        .withTemperature(0.5)
        .build();
    var defaultAdvisor = new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults());
    return builder
        .defaultSystem(SYSTEM_MSG)
        .defaultOptions(defaultConfig)
        .defaultAdvisors(defaultAdvisor)
        .defaultFunctions("LessonSlotsNearCity", "CurrentDateTime", "LessonDiscount")
        .build();
  }

  @Bean
  public VectorStore vectorStore(EmbeddingModel embeddingModel) {
    return new SimpleVectorStore(embeddingModel);
  }

  @Bean
  public TokenTextSplitter tokenTextSplitter() {
    return TokenTextSplitter.builder()
        .build();
  }

  @Bean
  public FunctionCallback lessonSlotsNearCity() {
    return FunctionCallback.builder()
        .description("Get the list of all available tank driving lesson slots near a given city.")
        .function("LessonSlotsNearCity", new LessonSlotsNearCity())
        .inputType(LessonSlotsNearCity.Request.class)
        .build();
  }

  @Bean
  public FunctionCallback currentDateTime() {
    return FunctionCallback.builder()
        .description("Get the current date and time.")
        .function("CurrentDateTime", () -> {
          log.info("Returning current date and time");
          return LocalDateTime.now();
        })
        .inputType(Void.class)
        .build();
  }
}
