# Troubleshooting Guide

## Overview

This guide provides solutions for common development problems in the
Enterprise AI Knowledge Base & Customer Support Assistant.

When troubleshooting an issue, follow this general process:

```text
Identify Problem
      ↓
Read Error Message
      ↓
Check Logs
      ↓
Verify Configuration
      ↓
Reproduce Problem
      ↓
Apply Fix
      ↓
Run Tests
      ↓
Document Important Fix


1. Java Version Problems
Check the installed Java version:
java -version
Check the Java compiler:
javac -version
Check the Java version Maven is using:
mvn -version
The Java version reported by Maven should match the version expected by
the project.
Common Symptoms
Unsupported class file version
Source option not supported
Target option not supported
Java version mismatch
Resolution
Verify:
JAVA_HOME
PATH
Maven configuration
IDE Java SDK
Project compiler settings
2. Maven Problems
Check Maven:
mvn -version
Try cleaning the project:
mvn clean
Then build:
mvn clean install
Run tests:
mvn test
3. Dependency Resolution Problems
If Maven cannot download or resolve a dependency:
Could not resolve dependencies
Could not find artifact
Dependency convergence error
Try:
mvn clean install
Check:
- Dependency version
- Repository configuration
- Internet connectivity
- pom.xml
- Maven version
- Java version
Avoid changing dependency versions randomly.
Verify compatibility before changing Spring Boot, Spring Cloud, or other
framework versions.
4. Spring Boot Application Does Not Start
First inspect the complete application log.
Look for the first meaningful exception rather than only the final
error message.
Common causes:
Database connection failure
Port already in use
Missing environment variable
Invalid application configuration
Bean creation failure
Dependency injection failure
Invalid entity mapping
Run the application with:
mvn spring-boot:run
5. Port Already in Use
If the application reports that port 8080 is already in use:
lsof -i :8080
Identify the process using the port.
Alternatively, configure the application to use another port.
Example:
server.port=8081
Do not change the port without also updating clients that depend on the
backend URL.
6. PostgreSQL Connection Problems
Check whether PostgreSQL is running.
Verify:
psql --version
Check the configured values:
DB_HOST
DB_PORT
DB_NAME
DB_USERNAME
DB_PASSWORD
Expected local configuration:
Host: localhost
Port: 5432
Database: ai_knowledge
Common errors:
Connection refused
Password authentication failed
Database does not exist
Could not connect to server
Verify that the database exists.
7. Docker PostgreSQL Connection Problems
When the Spring Boot backend runs inside Docker, do not normally use:
DB_HOST=localhost
for connecting to the PostgreSQL container.
Instead, use the PostgreSQL Docker Compose service name.
Example:
DB_HOST=postgres
The exact value depends on the service name defined in
docker-compose.yml.
8. Database Does Not Exist
If PostgreSQL reports:
database "ai_knowledge" does not exist
Create the database:
CREATE DATABASE ai_knowledge;
If Docker is being used, verify the PostgreSQL initialization
configuration.
9. Database Authentication Failure
If PostgreSQL reports:
password authentication failed
check:
DB_USERNAME
DB_PASSWORD
Also verify that the credentials configured in the application match
the PostgreSQL user.
Do not put database passwords directly into Java source code.
10. pgvector Problems
If the application reports that the vector type or extension is
missing, verify that pgvector is available in the PostgreSQL
environment.
Check the extension:
SELECT * FROM pg_extension;
The expected extension is:
vector
If it is not available, verify the PostgreSQL image or installation
being used.
The exact setup depends on the selected PostgreSQL/pgvector
configuration.
11. Environment Variable Problems
If the application cannot find configuration values, verify the
environment.
Check:
DB_HOST
DB_PORT
DB_NAME
DB_USERNAME
DB_PASSWORD
JWT_SECRET
AI_API_KEY
SERVER_PORT
Never commit actual secrets.
Compare the local configuration with:
.env.example
Remember:
.env.example → template
.env         → local secret configuration
12. JWT Authentication Problems
Common symptoms:
401 Unauthorized
Invalid JWT
JWT token expired
Authentication failed
Check:
Authorization header
JWT format
JWT secret
Token expiration
Username extraction
JWT filter
Security configuration
The request should normally contain:
Authorization: Bearer <JWT_TOKEN>
13. 401 Unauthorized
A 401 response generally indicates that authentication has not
succeeded.
Check:
Is the token present?
Is the token valid?
Is the token expired?
Is the Authorization header correct?
Is the JWT filter executing?
Compare the request with:
Authorization: Bearer <JWT_TOKEN>
14. 403 Forbidden
A 403 response generally indicates that the request reached security
checks but the authenticated user does not have the required access.
Check:
User role
Required role
Spring Security authorization rules
Granted authorities
Example:
ROLE_USER
     ↓
Admin-only endpoint
     ↓
403 Forbidden
Authentication and authorization are different concepts.
Authentication
    ↓
Who are you?

Authorization
    ↓
What are you allowed to access?
15. CORS Problems
A frontend may report a CORS error when communicating with the backend.
Symptoms can include:
CORS policy blocked request
Preflight request failed
Network Error
Check:
Frontend origin
Backend CORS configuration
HTTP method
Allowed headers
Authorization header
OPTIONS requests
Do not solve CORS problems by blindly allowing every origin in a
production configuration.
16. Swagger Problems
If Swagger UI does not load, check:
Springdoc dependency
Spring Boot compatibility
OpenAPI configuration
Application startup logs
Expected development endpoints may include:
/swagger-ui/index.html
/v3/api-docs
If /v3/api-docs returns an internal server error, inspect the backend
stack trace and verify that the Springdoc version is compatible with the
Spring Boot version.
17. Entity Mapping Problems
Common Hibernate/JPA errors include:
Unable to build Hibernate SessionFactory
Could not determine recommended JdbcType
Repeated column
Unknown entity
TransientPropertyValueException
Check:
@Entity
@Id
@GeneratedValue
@OneToMany
@ManyToOne
@OneToOne
@JoinColumn
Verify that entity relationships match the actual database design.
18. Lazy Loading Problems
A common Hibernate issue is:
LazyInitializationException
This can happen when a lazy relationship is accessed after the
persistence context is closed.
Review:
Transaction boundaries
Fetch strategy
DTO mapping
Repository queries
Avoid solving lazy-loading problems by changing every relationship to
EAGER.
Choose the loading strategy based on the actual use case.
19. Validation Problems
If invalid requests are being accepted, verify that:
DTO validation annotations
@Valid
Validation dependency
Exception handling
are configured correctly.
Typical validation annotations include:
@NotNull
@NotBlank
@Email
@Size
20. API Returns 500
A 500 Internal Server Error means the server encountered an
unexpected problem.
Check the backend logs.
Look for the root exception:
Caused by:
Do not diagnose the problem only from the HTTP status code.
Common causes include:
NullPointerException
Database failure
Invalid entity mapping
External API failure
Configuration error
Unexpected business logic error
21. Frontend Cannot Reach Backend
Verify that the backend is running:
http://localhost:8080
Then verify the frontend API base URL.
Check:
Backend URL
Backend port
CORS configuration
Browser developer console
Network tab
Authentication token
22. npm Problems
Check:
node -v
npm -v
Install dependencies:
npm install
If dependencies become inconsistent, remove node_modules and reinstall:
rm -rf node_modules
npm install
If a lock file is present and dependency versions should remain
unchanged, prefer:
npm ci
23. Angular Build Problems
Try:
npm install
Then:
npm run build
Check:
Node.js version
Angular CLI version
package.json
package-lock.json
TypeScript version
Do not upgrade Angular dependencies individually without checking
version compatibility.
24. AI API Problems
If AI requests fail, check:
AI_API_KEY
AI provider configuration
Model configuration
Network connectivity
Request format
API limits
Provider availability
Never log the complete API key.
25. AI Response Is Incorrect
If the AI response is not useful, determine which stage is failing.
User Question
      ↓
Query Embedding
      ↓
Vector Search
      ↓
Retrieved Context
      ↓
Prompt
      ↓
LLM
      ↓
Response
Inspect each stage separately.
Possible causes:
Poor chunking
Incorrect embeddings
Poor retrieval
Wrong similarity threshold
Insufficient context
Incorrect prompt
Model limitations
Do not immediately assume that the LLM itself is the problem.
26. RAG Retrieval Problems
If the system retrieves irrelevant documents:
Check:
Chunk size
Chunk overlap
Embedding model
Vector dimensions
Similarity metric
Top-K value
Metadata filters
Query preprocessing
A useful debugging approach is to temporarily inspect:
User Query
Query Embedding
Retrieved Chunks
Similarity Scores
Final Prompt
This makes it easier to determine where retrieval quality is being
lost.
27. Hallucination Problems
If the model generates information that is not supported by the
knowledge base, inspect:
Retrieved context
Prompt instructions
Source filtering
Retrieval quality
Model behavior
The system should have a defined behavior for cases where the
knowledge base does not contain enough information.
28. Docker Problems
Check Docker:
docker --version
docker compose version
Check running containers:
docker compose ps
View logs:
docker compose logs
View a specific service:
docker compose logs <service-name>
Restart services:
docker compose restart
Rebuild:
docker compose up -d --build
29. Docker Container Keeps Restarting
Check:
docker compose ps
Then:
docker compose logs <service-name>
Look for:
Configuration error
Missing environment variable
Port conflict
Database connection failure
Application startup failure
Fix the underlying error rather than repeatedly restarting the
container.
30. Git Problems
Check repository status:
git status
Check branches:
git branch -a
Check remote:
git remote -v
Review changes:
git diff
31. Accidentally Staged a File
Check staged files:
git diff --cached
Unstage a file:
git restore --staged <file>
The file remains in the working directory but is removed from the
staging area.
32. Accidentally Committed a Secret
If a password, API key, JWT secret, or other credential has been
committed:
1. Revoke or rotate the exposed credential immediately.
2. Replace it with a new credential.
3. Remove the secret from the source code.
4. Check whether the secret exists in Git history.
5. Clean Git history when necessary.
6. Verify that the new secret is stored securely.
Removing the file from the latest commit does not necessarily remove the
secret from Git history.
33. Git Merge Conflicts
Check:
git status
Open each conflicted file.
Look for:
<<<<<<<
=======
>>>>>>>
Resolve the conflict.
Then:
git add <file>
Run tests before completing the merge or rebase.
Never commit unresolved conflict markers.
34. Slow Application
If the application becomes slow, identify which layer is responsible.
Frontend
   ↓
Network
   ↓
Controller
   ↓
Service
   ↓
Database
   ↓
Vector Search
   ↓
LLM
Measure the relevant component before optimizing.
Potential causes include:
Slow database query
Missing database index
Large document processing
Slow vector search
Large prompt
LLM latency
Network latency
Frontend rendering
Avoid optimizing without measurements.
35. Logging
Logs should provide enough information to diagnose problems without
exposing secrets.
Good logging:
Document processing started
Document processing completed
User authentication failed
RAG retrieval completed
Avoid logging:
Passwords
JWT secrets
API keys
Authorization tokens
Database passwords
Sensitive personal information
36. General Debugging Checklist
When encountering a problem:
[ ] Read the complete error message
[ ] Find the root exception
[ ] Check application logs
[ ] Check environment variables
[ ] Verify dependency versions
[ ] Verify database connectivity
[ ] Verify API request
[ ] Reproduce the problem
[ ] Make the smallest appropriate change
[ ] Run tests
[ ] Verify the fix
[ ] Document the solution if useful
37. Getting Help
When reporting a technical issue, provide:
Problem
Expected behavior
Actual behavior
Steps to reproduce
Error message
Relevant logs
Environment
Java version
Maven version
Node.js version
Database version
Docker version
Avoid posting secrets or credentials.