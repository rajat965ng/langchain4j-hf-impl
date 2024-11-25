package com.rag.app.configuration;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.huggingface.HuggingFaceChatModel;
import dev.langchain4j.model.huggingface.HuggingFaceEmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class HuggingFaceConfig {
    @Value("${HF_API_KEY}")
    String apiKey;

    @Bean
    public ChatLanguageModel chatLanguageModel() {
        HuggingFaceChatModel model = HuggingFaceChatModel.builder()
                .accessToken(apiKey)
                .modelId("tiiuae/falcon-7b-instruct")
                .timeout(Duration.ofSeconds(15))
                .temperature(0.7)
                .maxNewTokens(120)
                .waitForModel(true)
                .build();
        return model;
    }

    @Bean
    public EmbeddingModel embeddingModel(){
        EmbeddingModel embeddingModel = HuggingFaceEmbeddingModel.builder()
                .accessToken(apiKey)
                .modelId("sentence-transformers/all-MiniLM-L6-v2")
                .waitForModel(true)
                .timeout(Duration.ofSeconds(60))
                .build();
        return embeddingModel;
    }

    @Bean
    public EmbeddingStore embeddingStore(){
        EmbeddingStore<TextSegment> embeddingStore = PgVectorEmbeddingStore.builder()
                .host("localhost")
                .port(5432)
                .database("vectordb")
                .user("postgres")
                .password("postgres")
                .table("test")
                .dimension(384)
                .build();
        return embeddingStore;
    }

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.withMaxMessages(2);
    }


    @Bean
    public ContentRetriever contentRetriever() {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingModel(embeddingModel())
                .embeddingStore(embeddingStore())
                .maxResults(3)
                .minScore(1.0)
                .build();
    }

}
