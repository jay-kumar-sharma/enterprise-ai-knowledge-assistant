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