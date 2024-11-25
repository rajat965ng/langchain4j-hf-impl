package com.rag.app.controller;

import com.rag.app.service.Assistant;
import com.rag.app.service.IngestionService;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloController {

    @Autowired
    ChatLanguageModel chatLanguageModel;
    @Autowired
    IngestionService ingestionService;

    @Autowired
    ContentRetriever contentRetriever;

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello World !!";
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String message){
        Assistant assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(chatLanguageModel)
                .contentRetriever(contentRetriever)
                .build();
        return assistant.chat(message);
    }

    @GetMapping("/ingest")
    public String ingest(@RequestParam String url){
        ingestionService.execute(url);
        return "Indexing Done !!";
    }
}
