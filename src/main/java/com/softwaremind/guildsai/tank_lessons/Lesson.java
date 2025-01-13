package com.softwaremind.guildsai.tank_lessons;

import java.time.LocalDateTime;

public record Lesson(
    LocalDateTime startDateTime,
    String instructor,
    String location
) {}