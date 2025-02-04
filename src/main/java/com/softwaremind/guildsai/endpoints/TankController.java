package com.softwaremind.guildsai.endpoints;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.softwaremind.guildsai.assistant.TankAssistant;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class TankController {
    private final TankAssistant assistant;

    @PostMapping(
        value = "/api/assistant",
        produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public Flux<String> assistant(@RequestBody TankQuestionRequest request) {
        System.out.println(request);
        return assistant.ask(request);
    }
}
