# API Documentation

## Overview

The Enterprise AI Knowledge Base & Customer Support Assistant exposes
REST APIs for authentication, users, documents, knowledge-base
management, and AI-powered conversations.

The API follows RESTful design principles and uses JSON for request and
response payloads.

---

# Base URL

For local development:

```text
http://localhost:8080

API versioning:
/api/v1
Therefore, an example API endpoint is:
http://localhost:8080/api/v1/auth/login
Authentication
Protected endpoints require a valid JWT token.
The token is sent using the HTTP Authorization header:
Authorization: Bearer <JWT_TOKEN>
Authentication-related endpoints do not require a JWT unless
specifically configured otherwise.
Authentication APIs
Register User
Creates a new user account.
Endpoint
POST /api/v1/auth/register
Request
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password"
}
Response
Example:
{
  "message": "User registered successfully"
}
The exact response structure may evolve during implementation.
Login
Authenticates an existing user.
Endpoint
POST /api/v1/auth/login
Request
{
  "email": "john@example.com",
  "password": "password"
}
Response
Example:
{
  "token": "<JWT_TOKEN>",
  "tokenType": "Bearer"
}
The exact response structure may evolve during implementation.
User APIs
User-related endpoints will be protected using Spring Security.
Planned operations include:
GET     /api/v1/users/me
GET     /api/v1/users
GET     /api/v1/users/{id}
PUT     /api/v1/users/{id}
DELETE  /api/v1/users/{id}
Administrative endpoints will require appropriate authorization.
Document APIs
The document APIs manage files that become part of the AI knowledge
base.
Upload Document
Uploads a document for processing.
Endpoint
POST /api/v1/documents
Authentication
Required.
Authorization
Administrative or knowledge-base management permission may be required.
Request
multipart/form-data
Example:
file=<document>
Processing
After upload, the document may go through:
Upload
  ↓
Validation
  ↓
Text Extraction
  ↓
Chunking
  ↓
Embedding Generation
  ↓
Vector Storage
List Documents
Returns documents available to the authenticated user or administrator.
Endpoint
GET /api/v1/documents
Authentication
Required.
Get Document
Returns information about a specific document.
Endpoint
GET /api/v1/documents/{id}
Authentication
Required.
Delete Document
Deletes a document and its associated knowledge-base data.
Endpoint
DELETE /api/v1/documents/{id}
Authentication
Required.
Authorization
Appropriate administrative permission is required.
AI Chat APIs
The AI chat API allows authenticated users to ask questions about the
knowledge base.
Ask Question
Endpoint
POST /api/v1/chat
Authentication
Required.
Request
{
  "question": "What is the company's leave policy?"
}
Processing Flow
User Question
      ↓
Query Embedding
      ↓
Vector Search
      ↓
Retrieve Relevant Chunks
      ↓
Build Context
      ↓
LLM
      ↓
AI Response
Example Response
{
  "answer": "The leave policy states that...",
  "sources": [
    {
      "documentId": 1,
      "documentName": "employee-handbook.pdf"
    }
  ]
}
The response structure will be finalized during implementation.
Knowledge Base APIs
The knowledge-base APIs manage documents and searchable knowledge.
Planned operations include:
GET     /api/v1/knowledge-base
POST    /api/v1/knowledge-base
GET     /api/v1/knowledge-base/{id}
PUT     /api/v1/knowledge-base/{id}
DELETE  /api/v1/knowledge-base/{id}
The final API surface will depend on the implemented domain model.
HTTP Status Codes
The API will use standard HTTP status codes.
Status Code	Meaning
200	Request completed successfully
201	Resource created successfully
204	Request successful with no response body
400	Invalid request
401	Authentication required or failed
403	Access denied
404	Resource not found
409	Resource conflict
422	Validation failure
500	Internal server error


Error Response
API errors should use a consistent response structure.
Example:
{
  "timestamp": "2026-01-01T10:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid request",
  "path": "/api/v1/documents"
}
The final error response structure will be defined during backend
implementation.
Validation
API requests should be validated before business logic is executed.
Validation may include:
- Required fields
- Email format
- Password requirements
- File type
- File size
- String length
- Request structure
Invalid requests should return an appropriate HTTP status code and
descriptive error response.
API Documentation with Swagger
The backend will expose OpenAPI documentation.
Planned Swagger UI endpoint:
http://localhost:8080/swagger-ui/index.html
OpenAPI specification:
http://localhost:8080/v3/api-docs
The exact paths may change depending on the Springdoc configuration.
API Security
The API must follow these security principles:
- Protected endpoints require authentication.
- Administrative endpoints require appropriate roles or permissions.
- Passwords must never be returned in API responses.
- JWT secrets must not be hardcoded.
- Sensitive configuration must use environment variables.
- Input validation must be applied to incoming requests.
- Error responses must not expose sensitive implementation details.
API Versioning
The project uses URL-based API versioning:
/api/v1
Example:
/api/v1/auth/login
/api/v1/documents
/api/v1/chat
Future breaking changes can be introduced through a new API version.
Example:
/api/v2
API Development Status
The API documentation describes the planned REST API surface.
Endpoints will be updated as the backend implementation progresses.
Implemented endpoints should be documented with:
- HTTP method
- URL
- Authentication requirement
- Authorization requirement
- Request parameters
- Request body
- Response body
- HTTP status codes
- Error scenarios
- Example requests
- Example responses