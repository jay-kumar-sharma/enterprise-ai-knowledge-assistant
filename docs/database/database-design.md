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


Core Entities
The initial database model is expected to contain the following
entities:
User
Role
Document
DocumentChunk
Conversation
Message
The model may evolve as implementation requirements become clearer.
User
Stores application user information.
Possible fields:
Field	Type	Description
id	BIGINT	Primary key
name	VARCHAR	User name
email	VARCHAR	Unique email
password	VARCHAR	Hashed password
role	VARCHAR	User role
created_at	TIMESTAMP	Account creation time
updated_at	TIMESTAMP	Last update time


Passwords must be stored only as secure password hashes.
Role
Roles define authorization boundaries within the application.
Initial roles:
ROLE_USER
ROLE_ADMIN
The implementation may use either:
- An enum stored in the user table
- A separate role table
- A role/permission model
The final approach will be determined during implementation.
Document
Stores metadata about uploaded documents.
Possible fields:
Field	Type	Description
id	BIGINT	Primary key
name	VARCHAR	Original document name
type	VARCHAR	Document type
size	BIGINT	File size
status	VARCHAR	Processing status
uploaded_by	BIGINT	User who uploaded it
created_at	TIMESTAMP	Upload timestamp
updated_at	TIMESTAMP	Last update timestamp


Example processing statuses:
UPLOADED
PROCESSING
COMPLETED
FAILED
Document Chunk
A document is divided into smaller chunks before embedding.
Possible fields:
Field	Type	Description
id	BIGINT	Primary key
document_id	BIGINT	Parent document
content	TEXT	Chunk text
chunk_number	INTEGER	Chunk sequence
page_number	INTEGER	Source page
created_at	TIMESTAMP	Creation timestamp


Relationship:
Document
   │
   │ 1
   │
   │
   │ N
   ▼
DocumentChunk
One document can contain many document chunks.
Embedding
Embeddings represent document chunks as numerical vectors.
Conceptually:
Document Chunk
      │
      ▼
Embedding Model
      │
      ▼
Vector
      │
      ▼
pgvector
A vector column may conceptually look like:
embedding vector(...)
The exact vector dimensions will depend on the embedding model selected
for the project.
Vector Search
The pgvector extension will be used for similarity search.
Conceptually:
Query
  │
  ▼
Query Embedding
  │
  ▼
pgvector
  │
  ▼
Similarity Search
  │
  ▼
Relevant Chunks
The implementation will select an appropriate distance/similarity
strategy based on the chosen embedding model.
Conversation
Stores an AI chat session.
Possible fields:
Field	Type	Description
id	BIGINT	Primary key
user_id	BIGINT	User who owns conversation
title	VARCHAR	Conversation title
created_at	TIMESTAMP	Creation time
updated_at	TIMESTAMP	Last update time


Relationship:
User
 │
 │ 1
 │
 │ N
 ▼
Conversation
Message
Stores individual messages inside a conversation.
Possible fields:
Field	Type	Description
id	BIGINT	Primary key
conversation_id	BIGINT	Parent conversation
role	VARCHAR	USER / ASSISTANT / SYSTEM
content	TEXT	Message content
created_at	TIMESTAMP	Creation time


Relationship:
Conversation
      │
      │ 1
      │
      │ N
      ▼
   Message
Entity Relationship Overview
┌──────────────┐
│     User     │
└──────┬───────┘
       │
       │ 1:N
       ▼
┌──────────────┐
│ Conversation │
└──────┬───────┘
       │
       │ 1:N
       ▼
┌──────────────┐
│    Message   │
└──────────────┘


┌──────────────┐
│     User     │
└──────┬───────┘
       │
       │ 1:N
       ▼
┌──────────────┐
│   Document   │
└──────┬───────┘
       │
       │ 1:N
       ▼
┌──────────────────┐
│  DocumentChunk   │
└────────┬─────────┘
         │
         │ 1:1 / associated vector
         ▼
┌──────────────────┐
│     Embedding    │
│    pgvector      │
└──────────────────┘
Database Relationships
User → Documents
A user can upload multiple documents.
User 1 ─────────── N Document
Document → Document Chunks
A document can contain multiple chunks.
Document 1 ─────── N DocumentChunk
User → Conversations
A user can have multiple conversations.
User 1 ─────────── N Conversation
Conversation → Messages
A conversation can contain multiple messages.
Conversation 1 ─── N Message
Indexing Strategy
Indexes will be added where they improve query performance.
Potential indexes include:
users.email
documents.uploaded_by
documents.status
document_chunks.document_id
conversations.user_id
messages.conversation_id
The exact indexes will be determined after implementing and evaluating
the application's query patterns.
Data Integrity
The database should enforce appropriate constraints.
Examples:
- User email should be unique.
- Required fields should not accept NULL values.
- Foreign keys should maintain relationships.
- Document chunks should reference valid documents.
- Messages should reference valid conversations.
- Timestamps should be maintained consistently.
Database Migrations
Database schema changes should be managed through a migration strategy
rather than manually modifying production databases.
A migration tool such as Flyway may be introduced during backend
implementation.
Example:
db/
└── migration/
    ├── V1__create_users.sql
    ├── V2__create_documents.sql
    ├── V3__create_document_chunks.sql
    └── V4__create_conversations.sql
The actual migration structure will be defined when database
implementation begins.
Development Database
Local development is expected to use PostgreSQL.
Example configuration:
Host: localhost
Port: 5432
Database: ai_knowledge
Credentials must be provided through environment configuration and must
not be committed to Git.
Refer to:
.env.example
for the expected configuration variables.
Vector Database Configuration
The project plans to use PostgreSQL with the pgvector extension.
Conceptually:
PostgreSQL
    │
    ├── Relational Data
    │
    └── Vector Data
          │
          └── pgvector
This allows application data and vector search infrastructure to remain
within the same database system during the initial implementation.
Security Considerations
Database security requirements include:
- Never commit database passwords.
- Never store plaintext user passwords.
- Use environment variables for credentials.
- Restrict database access.
- Use separate credentials for different environments where appropriate.
- Apply least-privilege database permissions.
- Validate and sanitize external input.
- Use parameterized queries through the application's persistence
  framework.
- Back up important production data.