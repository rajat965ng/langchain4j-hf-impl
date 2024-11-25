package com.rag.app;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.huggingface.HuggingFaceChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App {
    public static void main( String[] args ) {
        SpringApplication.run(App.class,args);
    }
}
