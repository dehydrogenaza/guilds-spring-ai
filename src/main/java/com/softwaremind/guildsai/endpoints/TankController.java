package com.softwaremind.guildsai.endpoints;

import com.softwaremind.guildsai.assistant.TankAssistant;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TankController {
    private final TankAssistant assistant;

    @PostMapping("/api/assistant")
    public String assistant(@RequestBody TankQuestionRequest request) {
        System.out.println(request);
        return assistant.ask(request);
    }
}

