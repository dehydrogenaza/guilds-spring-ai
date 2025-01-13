package com.softwaremind.guildsai.tank_lessons;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LessonSlotsNearCity implements Function<LessonSlotsNearCity.Request, LessonSlotsNearCity.Response> {
  public record Request(String location) {}
  public record Response(List<Lesson> availableSlots) {}

  private static final List<Lesson> MOCK_LESSONS_NEAR_CRACOW = List.of(
      new Lesson(
          LocalDateTime.of(2025, 1, 15, 6, 0),
          "Dawid",
          "Kraków"
      ),
      new Lesson(
          LocalDateTime.of(2025, 1, 16, 18, 30),
          "Piotr",
          "Dąbrowa Górnicza"
      ),
      new Lesson(
          LocalDateTime.of(2025, 1, 16, 20, 15),
          "Weronika",
          "Kraków"
      )
  );

  private static final List<Lesson> MOCK_LESSONS_NEAR_WARSAW = List.of(
      new Lesson(
          LocalDateTime.of(2025, 1, 17, 10, 45),
          "Bartek",
          "Pruszków"
      )
  );

  @Override
  public Response apply(Request request) {
    log.info("Looking for lessons near {}", request.location);

    return switch (request.location) {
      case "Cracow", "Kraków" -> new Response(MOCK_LESSONS_NEAR_CRACOW);
      case "Warsaw", "Warszawa" -> new Response(MOCK_LESSONS_NEAR_WARSAW);
      default -> new Response(List.of());
    };
  }
}
