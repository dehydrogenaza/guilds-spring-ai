package com.softwaremind.guildsai.assistant;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {
    private static final String SYSTEM_MSG = """
            You are an expert Armored Division mechanic.
            Answer questions about proper tank operating procedures and maintenance according to Army regulations.
            Your answers should be detailed and accurate.
            """;

    @Value("${SPRING_AI_OPENAI_API_KEY}")
    private String apiKey;

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
                .build();
    }

    @Bean
    public OpenAiApi openAiApi() {
        return new OpenAiApi(apiKey);
    }

    @Bean
    public EmbeddingModel embeddingModel(OpenAiApi openAiApi) {
        return new OpenAiEmbeddingModel(openAiApi);
    }

    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {
        return new SimpleVectorStore(embeddingModel);
    }
}
