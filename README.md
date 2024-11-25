# Spring Boot LangChain4j with HuggingFace Integration

This project demonstrates the implementation of a chatbot and document ingestion system using Spring Boot and LangChain4j, integrated with HuggingFace models and PostgreSQL vector storage.

## Features

- Chat interface using HuggingFace's Falcon-7b-instruct model
- Document ingestion and embedding using sentence-transformers
- Vector storage using PostgreSQL with pgvector extension
- RESTful API endpoints for chat and document ingestion

## Prerequisites

- Java 17 or higher
- PostgreSQL with pgvector extension installed
- HuggingFace API key
- Maven

## Configuration

### Database Setup

1. Install PostgreSQL and pgvector extension
2. Create a database named `vectordb`
3. Create a table named `test` with vector support (dimension: 384)

```sql
CREATE EXTENSION IF NOT EXISTS vector;
CREATE TABLE test (
    id SERIAL PRIMARY KEY,
    embedding vector(384),
    text TEXT
);
```

### Application Properties

Create `application.properties` file with the following configurations:

```properties
# HuggingFace API Key
HF_API_KEY=your_huggingface_api_key

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/vectordb
spring.datasource.username=postgres
spring.datasource.password=postgres
```

## Project Structure

- `HuggingFaceConfig.java`: Configuration class for HuggingFace models and vector storage
- `HelloController.java`: REST controller with endpoints for chat and document ingestion
- `Assistant.java`: Interface defining the chat behavior
- `IngestionService.java`: Service for document ingestion and embedding

## API Endpoints

### 1. Health Check
```
GET /hello
Response: "Hello World !!"
```

### 2. Chat Interface
```
GET /chat?message=your_message
Response: AI-generated response based on the input message
```

### 3. Document Ingestion
```
GET /ingest?url=document_url
Response: "Indexing Done !!"
```

## Components

### Language Models
- Chat Model: tiiuae/falcon-7b-instruct
- Embedding Model: sentence-transformers/all-MiniLM-L6-v2

### Vector Storage
- PostgreSQL with pgvector
- Dimension: 384
- Configurable search parameters (max results: 3, min score: 1.0)

### Chat Memory
- Message window with 2 message history

## Usage

1. Start the application
2. Ingest documents using the `/ingest` endpoint
3. Start chatting using the `/chat` endpoint

## Dependencies

- Spring Boot
- LangChain4j
- PostgreSQL
- pgvector
- HuggingFace Java Client

## Notes

- The chat model has a timeout of 15 seconds
- The embedding model has a timeout of 60 seconds
- Document splitting is configured with chunk size 2000 and overlap 200
- Vector search is limited to top 3 results with minimum score of 1.0

## Security Considerations

- Ensure proper security measures for the HuggingFace API key
- Configure appropriate database security
- Implement proper authentication and authorization for the API endpoints in production

