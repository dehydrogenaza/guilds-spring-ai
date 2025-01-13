package com.softwaremind.guildsai.assistant;

import com.softwaremind.guildsai.endpoints.TankQuestionRequest;
import com.softwaremind.guildsai.manual.ManualReader;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
public class TankAssistant {
    private static final String QUESTION_TEMPLATE = """
            The question concerns the following model: %s.
            %s
            """;

    private final ChatClient chatClient;
    private final ManualReader manualReader;
    private final VectorStore vectorStore;

    public TankAssistant(ChatClient chatClient, ManualReader manualReader, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.manualReader = manualReader;
        this.vectorStore = vectorStore;
    }

    public String ask(TankQuestionRequest request) {
        var manual = manualReader.getDocsFromPdf();
        vectorStore.accept(manual);
        System.out.println("Added the manual to VS.");

        return chatClient
                .prompt()
                .user(QUESTION_TEMPLATE.formatted(request.model(), request.question()))
                .call()
                .content();
    }
}
