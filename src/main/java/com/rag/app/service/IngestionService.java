package com.rag.app.service;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentTransformer;
import dev.langchain4j.data.document.loader.UrlDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.document.transformer.jsoup.HtmlToTextDocumentTransformer;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngestionService {

    @Autowired
    EmbeddingStore embeddingStore;
    @Autowired
    EmbeddingModel embeddingModel;

    public void execute(String url) {
        Document document = UrlDocumentLoader.load(url, new TextDocumentParser());
        DocumentTransformer transformer = new HtmlToTextDocumentTransformer();
        EmbeddingStoreIngestor.builder()
                .documentTransformer(transformer)
                .documentSplitter(DocumentSplitters.recursive(2000, 200))
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build()
                .ingest(document);
    }

}
