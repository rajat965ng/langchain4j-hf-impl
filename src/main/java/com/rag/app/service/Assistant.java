package com.rag.app.service;

import dev.langchain4j.service.SystemMessage;


public interface Assistant {
    @SystemMessage("You are polite assistant")
    public String chat(String message);
}
