# RAG Pipeline

## Overview

RAG stands for Retrieval-Augmented Generation.

The Enterprise AI Knowledge Base & Customer Support Assistant will use
RAG to allow the AI system to answer questions using information stored
in the application's knowledge base.

Instead of relying only on the knowledge contained in the language
model, the application retrieves relevant information from the
knowledge base and provides that information as context to the LLM.

---

# RAG Architecture

```text
                    User Question
                          │
                          ▼
                 ┌─────────────────┐
                 │  Spring Boot    │
                 │   REST API      │
                 └────────┬────────┘
                          │
                          ▼
                 ┌─────────────────┐
                 │ Query Processing│
                 └────────┬────────┘
                          │
                          ▼
                 ┌─────────────────┐
                 │    Embedding    │
                 │     Model       │
                 └────────┬────────┘
                          │
                    Query Vector
                          │
                          ▼
                 ┌─────────────────┐
                 │ Vector Database │
                 │    pgvector     │
                 └────────┬────────┘
                          │
                  Relevant Documents
                          │
                          ▼
                 ┌─────────────────┐
                 │ Context Builder │
                 └────────┬────────┘
                          │
                          ▼
                 ┌─────────────────┐
                 │      LLM        │
                 └────────┬────────┘
                          │
                          ▼
                    AI Response


                Document Processing Pipeline


                        Document Upload
                              │
                              ▼
                        Document Validation
                              │
                              ▼
                        Text Extraction
                              │
                              ▼
                        Text Cleaning
                              │
                              ▼
                        Document Chunking
                              │
                              ▼
                        Generate Embeddings
                              │
                              ▼
                        Store Vectors
                              │
                              ▼
                        Knowledge Base


Step 1: Document Upload
An authorized user or administrator uploads a document through the
application.
Possible document types may include:
- PDF
- Text documents
- Markdown documents
- Other supported knowledge-base formats
The backend validates the uploaded file before processing it.
Validation may include:
- File type
- File size
- File name
- File content
Step 2: Text Extraction
The application extracts readable text from the uploaded document.
For example:
PDF
 │
 ▼
Text Extraction
 │
 ▼
Raw Document Text
The extracted text becomes the input for the next processing stage.
Step 3: Text Cleaning
Extracted text may contain unnecessary formatting or characters.
The processing pipeline can normalize the content before chunking.
Examples:
- Remove unnecessary whitespace
- Normalize line breaks
- Remove unsupported characters
- Preserve meaningful headings and structure
Step 4: Document Chunking
Large documents should not be sent to the LLM as one large block.
The document is divided into smaller chunks.
Example:
Original Document
       │
       ▼
┌──────────────────┐
│     Chunk 1      │
├──────────────────┤
│     Chunk 2      │
├──────────────────┤
│     Chunk 3      │
├──────────────────┤
│     Chunk 4      │
└──────────────────┘
Chunk size and overlap will be configurable based on the application's
requirements.
Chunk metadata should be preserved where useful.
Example metadata:
documentId
documentName
pageNumber
chunkNumber
createdAt
Step 5: Generate Embeddings
Each document chunk is converted into an embedding vector.
An embedding represents the semantic meaning of the text as numerical
data.
Conceptually:
Text Chunk
    │
    ▼
Embedding Model
    │
    ▼
[0.021, -0.183, 0.442, ...]
The generated vector is stored together with the original chunk and
its metadata.
Step 6: Store Embeddings
The project plans to use PostgreSQL with pgvector for vector storage.
Conceptually:
PostgreSQL
│
├── documents
│
├── document_chunks
│
└── embeddings
The vector database allows the application to perform similarity
search against stored embeddings.
Query Processing Pipeline
When a user asks a question, the application performs the following
steps:
User Question
      │
      ▼
Generate Query Embedding
      │
      ▼
Vector Similarity Search
      │
      ▼
Retrieve Relevant Chunks
      │
      ▼
Build Context
      │
      ▼
Create Prompt
      │
      ▼
Send to LLM
      │
      ▼
Generate Response
Vector Similarity Search
The user's question is converted into an embedding.
The resulting vector is compared with vectors stored in the vector
database.
Conceptually:
User Query Vector
       │
       ▼
Vector Database
       │
       ├── Chunk A → similarity: high
       ├── Chunk B → similarity: high
       ├── Chunk C → similarity: medium
       └── Chunk D → similarity: low
       │
       ▼
Relevant Chunks
The application retrieves the most relevant chunks according to the
configured similarity-search strategy.
Context Construction
The retrieved chunks are combined to create context for the LLM.
Example:
User Question
      +
Retrieved Knowledge
      │
      ▼
Context
      │
      ▼
Prompt
The context should contain information relevant to answering the user's
question.
LLM Generation
The application sends the user's question together with the retrieved
context to the configured language model.
Conceptually:
System Instructions
        +
Retrieved Context
        +
User Question
        │
        ▼
       LLM
        │
        ▼
Generated Answer
The LLM uses the retrieved context to generate the final response.
Complete RAG Flow
The complete pipeline can be represented as:
                 DOCUMENT INGESTION
                         │
                         ▼
                  Document Upload
                         │
                         ▼
                  Text Extraction
                         │
                         ▼
                     Chunking
                         │
                         ▼
                    Embeddings
                         │
                         ▼
                    pgvector
                         │
                         │
                         │
                    USER QUERY
                         │
                         ▼
                    User Question
                         │
                         ▼
                  Query Embedding
                         │
                         ▼
                  Vector Search
                         │
                         ▼
                Relevant Chunks
                         │
                         ▼
                 Context Builder
                         │
                         ▼
                    LLM Prompt
                         │
                         ▼
                       LLM
                         │
                         ▼
                  AI Response
RAG Components
The implementation is expected to contain components similar to:
rag/
├── controller/
│   └── ChatController
│
├── service/
│   ├── RagService
│   ├── DocumentService
│   ├── EmbeddingService
│   └── VectorSearchService
│
├── model/
│   ├── Document
│   └── DocumentChunk
│
└── repository/
    ├── DocumentRepository
    └── DocumentChunkRepository
The exact package structure may evolve during implementation.
RAG Goals
The RAG implementation should aim to provide:
- Knowledge-base-aware answers
- Relevant context retrieval
- Reduced dependence on model-only knowledge
- Document-grounded responses
- Source metadata where applicable
- Secure access to private knowledge
- Scalable document processing
Future Improvements
Potential future improvements include:
- Hybrid search
- Metadata filtering
- Re-ranking
- Query expansion
- Conversation memory
- Source citations
- Streaming responses
- Multiple knowledge bases
- Document versioning
- Advanced retrieval strategies
- Evaluation of retrieval quality
- RAG observability and monitoring
