package com.pekarsky.llm_sandbox.config;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringAiConfig {

    @Value("${spring.ai.ollama.url}")
    private String ollamaUrl;

    @Bean
    public ChatClient chatClient() {
        OllamaApi ollamaApi = new OllamaApi(ollamaUrl);
        return new OllamaChatClient(ollamaApi)
                .withDefaultOptions(OllamaOptions.create().withModel("mistral"));
    }
} 