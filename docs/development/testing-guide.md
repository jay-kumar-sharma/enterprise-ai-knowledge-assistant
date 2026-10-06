# Testing Guide

## Overview

Testing is an important part of the development lifecycle of the
Enterprise AI Knowledge Base & Customer Support Assistant.

The goal is to verify:

- Business logic
- REST APIs
- Authentication
- Authorization
- Database interactions
- Document processing
- RAG functionality
- AI integration
- Frontend behavior
- Application security

---

# Testing Strategy

The project follows a layered testing strategy:

```text
                    Testing
                       │
        ┌──────────────┼──────────────┐
        │              │              │
        ▼              ▼              ▼
   Unit Tests    Integration Tests   API Tests
        │              │              │
        └──────────────┼──────────────┘
                       │
                       ▼
                End-to-End Tests


Testing Pyramid
The project should generally follow the testing pyramid:
                 ┌───────────────┐
                 │  E2E Tests    │
                 │    Fewer      │
                 └───────┬───────┘
                         │
                 ┌───────▼───────┐
                 │ Integration   │
                 │    Tests      │
                 └───────┬───────┘
                         │
              ┌──────────▼──────────┐
              │     Unit Tests      │
              │       More         │
              └─────────────────────┘
Unit tests should form the largest portion of the automated test suite.
Backend Testing
The Spring Boot backend should be tested at multiple levels.
Expected technologies may include:
- JUnit
- Mockito
- Spring Boot Test
- MockMvc
- Testcontainers
- REST API testing tools
The exact testing stack will be finalized during implementation.
Unit Tests
Unit tests verify individual classes or methods in isolation.
Example:
TaskService
     │
     ▼
Unit Test
     │
     ├── Valid input
     ├── Invalid input
     ├── Expected exception
     └── Business rule
Unit tests should avoid unnecessary dependencies on external systems.
Service Layer Testing
Business logic should be tested thoroughly.
Example scenarios for an authentication service:
Valid registration
Duplicate email
Invalid password
Valid login
Invalid credentials
User not found
Example scenarios for a document service:
Valid document
Unsupported file
File too large
Document not found
Unauthorized access
Processing failure
Controller Testing
Controller tests verify:
- HTTP method
- URL
- Request validation
- HTTP status
- Response body
- Authentication
- Authorization
Example:
POST /api/v1/auth/login
        │
        ▼
Controller Test
        │
        ├── Valid request → 200
        ├── Invalid request → 400
        └── Invalid credentials → 401
Integration Tests
Integration tests verify that multiple application components work
together.
Example:
Controller
    │
    ▼
Service
    │
    ▼
Repository
    │
    ▼
Database
Integration tests may verify:
- Spring context
- Database persistence
- Repository queries
- Security configuration
- REST API behavior
- Transaction behavior
Database Testing
Database-related tests should verify:
- Entity persistence
- Relationships
- Constraints
- Queries
- Transactions
- Unique fields
- Foreign keys
Example:
Create User
    ↓
Save to Database
    ↓
Retrieve User
    ↓
Verify Data
Test Database
Tests should avoid depending on a developer's personal database.
Possible approaches include:
- Dedicated test database
- H2 for suitable tests
- Testcontainers
- PostgreSQL test instance
For database behavior that depends specifically on PostgreSQL or
pgvector, a PostgreSQL-based test environment is preferable.
The final approach will be selected during implementation.
Authentication Testing
Authentication is a security-critical area.
Tests should verify:
Registration
    │
    ├── Valid registration
    ├── Duplicate email
    ├── Invalid input
    └── Password handling

Login
    │
    ├── Valid credentials
    ├── Invalid password
    ├── Unknown user
    └── JWT generation

Protected APIs
    │
    ├── Valid JWT
    ├── Missing JWT
    ├── Invalid JWT
    └── Expired JWT
Authorization Testing
Role-based access must be tested separately from authentication.
Example:
ROLE_USER
    │
    ├── Allowed endpoint → Success
    └── Admin endpoint → 403


ROLE_ADMIN
    │
    ├── User endpoint → Success
    └── Admin endpoint → Success
The exact authorization rules depend on the implemented requirements.
JWT Testing
JWT-related tests should verify:
- Token generation
- Token validation
- Token expiration
- Invalid signature
- Missing token
- Malformed token
- Username extraction
- Role/authority handling
Example:
Request
   │
   ▼
Authorization Header
   │
   ▼
JWT Filter
   │
   ├── Valid → Continue
   │
   └── Invalid → Reject
API Testing
REST APIs can be tested using:
- Swagger UI
- Postman
- Automated MockMvc tests
- Integration tests
Example API flow:
Register
   ↓
Login
   ↓
Receive JWT
   ↓
Send JWT
   ↓
Call Protected Endpoint
   ↓
Verify Response
API Test Cases
Every API should consider at least:
Success Cases
- Valid request
- Correct authentication
- Correct authorization
- Expected response
Validation Cases
- Missing required field
- Invalid data type
- Invalid format
- Boundary values
Security Cases
- Missing token
- Invalid token
- Insufficient permissions
Resource Cases
- Resource exists
- Resource does not exist
- Duplicate resource
Server Cases
- Dependency failure
- Database failure
- Unexpected exception
Document Processing Testing
Document processing should be tested independently.
Example:
Upload Document
      ↓
Validate File
      ↓
Extract Text
      ↓
Clean Text
      ↓
Split Into Chunks
      ↓
Generate Embeddings
      ↓
Store Vectors
Test cases should include:
- Valid document
- Empty document
- Unsupported format
- Corrupted document
- Large document
- Extraction failure
- Chunking behavior
- Embedding failure
RAG Testing
RAG testing should cover both retrieval and generation.
The RAG pipeline is:
Question
   ↓
Query Embedding
   ↓
Vector Search
   ↓
Relevant Chunks
   ↓
Context
   ↓
LLM
   ↓
Answer
Testing should verify that relevant knowledge is retrieved.
Example:
Question:
"What is the leave policy?"

Expected:
Relevant leave-policy document chunks
Retrieval Testing
Retrieval quality can be evaluated using:
- Relevant document retrieval
- Top-K results
- Similarity scores
- Metadata filtering
- Query variations
Example:
Query
  │
  ▼
Vector Search
  │
  ├── Relevant Chunk A
  ├── Relevant Chunk B
  └── Irrelevant Chunk C
The retrieval strategy may be improved using techniques such as:
- Metadata filtering
- Hybrid search
- Re-ranking
- Query expansion
LLM Testing
LLM output is not always deterministic.
Therefore, AI testing should focus on measurable behavior rather than
expecting one exact response in every case.
Potential evaluation criteria include:
- Relevance
- Groundedness
- Completeness
- Citation/source correctness
- Safety
- Response latency
The final evaluation strategy will be defined as the AI functionality
matures.
Hallucination Testing
The system should be tested for unsupported answers.
Example:
Knowledge Base:
Contains information about company leave policy.

Question:
"What is the company's policy for Mars vacations?"

Expected behavior:
The system should avoid inventing information when the knowledge base
does not contain sufficient information.
The exact response behavior will be defined during AI implementation.
Conversation Testing
If conversation history is supported, test:
User Question 1
       ↓
Assistant Answer 1
       ↓
User Follow-up Question
       ↓
Assistant Answer 2
Tests should verify that:
- Conversation ownership is respected.
- Messages are stored correctly.
- Conversation history is retrieved correctly.
- Users cannot access another user's conversation.
Frontend Testing
The Angular frontend should eventually include tests for:
- Components
- Services
- Forms
- Routing
- Authentication state
- API integration
- Error handling
Example:
Login Component
      │
      ▼
Submit Form
      │
      ▼
Authentication Service
      │
      ▼
Backend API
      │
      ▼
Store Authentication State
End-to-End Testing
End-to-end tests verify complete user workflows.
Example:
Open Application
      ↓
Register
      ↓
Login
      ↓
Upload Document
      ↓
Wait for Processing
      ↓
Ask Question
      ↓
Retrieve AI Answer
      ↓
Verify Source
E2E tests should be used for critical user journeys rather than every
possible internal behavior.
Test Naming
Tests should clearly describe the behavior being verified.
Example:
shouldRegisterUserWhenValidRequestIsProvided()

shouldRejectRegistrationWhenEmailAlreadyExists()

shouldRejectRequestWhenJwtIsMissing()

shouldAllowAdminToUploadDocument()

shouldDenyUserAccessToAdminEndpoint()
Avoid vague names such as:
test1()
testLogin()
checkSomething()
works()
Arrange-Act-Assert
Unit tests should preferably follow the Arrange-Act-Assert structure.
Arrange
   ↓
Prepare test data
   ↓
Act
   ↓
Execute behavior
   ↓
Assert
   ↓
Verify result
Example:
// Arrange
Create valid user request.

// Act
Call registration service.

// Assert
Verify user was created.
Test Data
Test data should be:
- Predictable
- Isolated
- Easy to understand
- Independent between tests
Avoid relying on production data.
Sensitive information should never be used in tests.
Mocking
Mockito or another mocking framework may be used when isolation is
appropriate.
Example:
Service
  │
  ├── Mock Repository
  └── Mock External AI Service
Do not mock everything automatically.
Use real integrations where the behavior of the integration itself is
what needs to be tested.
External AI Testing
Tests should avoid unnecessary calls to paid external AI APIs.
Possible approaches include:
- Mock AI responses
- Fake embedding service
- Local test model
- Recorded test fixtures
- Dedicated integration tests
This helps make tests:
- Faster
- Cheaper
- More deterministic
- Suitable for CI
Test Coverage
Code coverage can help identify untested areas.
However, coverage percentage alone should not be treated as proof of
software quality.
Important areas should have meaningful behavioral tests.
Priority should be given to:
- Security
- Authentication
- Authorization
- Business logic
- Data integrity
- Critical APIs
- RAG retrieval
- Important user workflows
Running Tests
Backend:
mvn test
Full Maven verification:
mvn clean verify
Frontend testing commands will depend on the final Angular testing
configuration.
Continuous Integration
Tests should eventually run automatically in GitHub Actions.
Expected flow:
Push / Pull Request
        ↓
GitHub Actions
        ↓
Build
        ↓
Unit Tests
        ↓
Integration Tests
        ↓
Quality Checks
        ↓
Build Result
A Pull Request should not be merged when required CI checks fail.
Testing Checklist
Before creating a Pull Request:
- Unit tests pass
- Integration tests pass
- API tests pass
- Security tests pass
- Relevant edge cases tested
- No sensitive test data committed
- RAG behavior tested where applicable
- Frontend tests pass where applicable
- Full build succeeds
- Documentation updated
Definition of Testing Complete
Testing for a feature is considered complete when:
Implementation
      +
Unit Tests
      +
Integration Tests
      +
API Tests
      +
Security Tests
      +
Documentation
      +
CI Verification
The exact testing requirements depend on the feature being developed.