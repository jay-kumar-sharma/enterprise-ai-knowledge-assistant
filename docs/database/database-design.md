# Database Design

## Overview

The Enterprise AI Knowledge Base & Customer Support Assistant will use
PostgreSQL as the primary relational database.

The database will store:

- User accounts
- Roles
- Documents
- Document chunks
- Embeddings
- Knowledge-base metadata
- Chat conversations
- Chat messages
- Application metadata

Vector data will be stored using PostgreSQL with the `pgvector`
extension.

---

# Database Architecture

```text
                    PostgreSQL
                        │
        ┌───────────────┼────────────────┐
        │               │                │
        ▼               ▼                ▼
      Users         Documents       Conversations
        │               │                │
        │               ▼                ▼
        │        Document Chunks      Messages
        │               │
        │               ▼
        │          Embeddings
        │               │
        └───────────────┴────────────────┐
                                         ▼
                                      pgvector