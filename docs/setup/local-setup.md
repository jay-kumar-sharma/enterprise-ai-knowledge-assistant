# Local Development Setup

## Overview

This guide explains how to prepare the local development environment
for the Enterprise AI Knowledge Base & Customer Support Assistant.

The project will contain:

- Spring Boot backend
- PostgreSQL database
- pgvector
- Angular frontend
- AI/LLM integration
- Docker support

---

# Prerequisites

Install the following software before starting development.

## Java

The backend requires a supported Java version.

Verify Java:

```bash
java -version


Verify the Java compiler:
javac -version
Maven
The backend uses Maven for dependency management and builds.
Verify Maven:
mvn -version
Git
Verify Git:
git --version
Node.js
Node.js will be required for the Angular frontend.
Verify:
node -v
Verify npm:
npm -v
PostgreSQL
Install PostgreSQL and verify that the PostgreSQL server is available.
Verify:
psql --version
The default local database configuration is expected to use:
Host: localhost
Port: 5432
Database: ai_knowledge
pgvector
The project uses PostgreSQL with the pgvector extension for vector
similarity search.
The extension must be available in the PostgreSQL environment used by
the application.
Clone the Repository
Clone the repository:
git clone <repository-url>
Move into the project:
cd enterprise-ai-knowledge-assistant
Project Structure
The project follows a structure similar to:
enterprise-ai-knowledge-assistant/
│
├── backend/
├── frontend/
├── docs/
├── scripts/
│
├── .env.example
├── .gitignore
├── docker-compose.yml
├── pom.xml
└── README.md
Some directories will be added during implementation.
Environment Configuration
Do not commit real secrets to Git.
Create a local environment configuration based on:
.env.example
Example variables:
DB_HOST=localhost
DB_PORT=5432
DB_NAME=ai_knowledge
DB_USERNAME=postgres
DB_PASSWORD=change_me

JWT_SECRET=change_me

AI_API_KEY=change_me

SERVER_PORT=8080
Use real local values for development.
Never commit real passwords, API keys, JWT secrets, or other sensitive
credentials.
Database Setup
Create the PostgreSQL database:
CREATE DATABASE ai_knowledge;
The exact database initialization process may change when Docker and
database migrations are implemented.
Backend Setup
Navigate to the backend directory:
cd backend
Build the project:
mvn clean install
Run the Spring Boot application:
mvn spring-boot:run
The backend is expected to start on:
http://localhost:8080
The actual port can be changed through application configuration.
Frontend Setup
Navigate to the frontend directory:
cd frontend
Install dependencies:
npm install
Start the development server:
npm start
The exact Angular development command may depend on the final project
configuration.
Swagger
When the backend is running, Swagger UI is expected to be available at:
http://localhost:8080/swagger-ui/index.html
OpenAPI specification:
http://localhost:8080/v3/api-docs
These endpoints will become available after the corresponding backend
configuration is implemented.
Running the Complete Application
The expected local development flow is:
PostgreSQL
    │
    ▼
Spring Boot Backend
    │
    ▼
Angular Frontend
    │
    ▼
User
Start PostgreSQL first.
Then start the Spring Boot backend.
Finally start the Angular frontend.
Verify the Backend
Once the backend is running, verify that the application responds.
Example:
curl http://localhost:8080/actuator/health
The exact health endpoint depends on whether Spring Boot Actuator is
enabled.
Verify the Frontend
Open the frontend development URL in a browser.
The exact URL and port will be documented once the frontend is
implemented.
Common Problems
PostgreSQL Connection Failed
Check:
PostgreSQL is running
Host is correct
Port is correct
Database exists
Username is correct
Password is correct
Port Already in Use
Check which process is using the port.
macOS/Linux:
lsof -i :8080
Stop the conflicting process or configure the application to use another
port.
Maven Build Failure
Try:
mvn clean
Then:
mvn clean install
Check:
java -version
mvn -version
to ensure the expected Java and Maven versions are being used.
Frontend Dependency Problems
Try:
rm -rf node_modules
npm install
If the project uses a lock file, prefer:
npm ci
when appropriate.
AI API Problems
Check:
AI_API_KEY
and verify that the configured AI provider credentials are valid.
Never place API keys directly in source code.
Development Workflow
Recommended workflow:
Pull latest changes
       ↓
Create feature branch
       ↓
Implement feature
       ↓
Run tests
       ↓
Update documentation
       ↓
Review changes
       ↓
Commit changes
       ↓
Push branch
       ↓
Create Pull Request
Example:
git checkout main
git pull
git checkout -b feature/document-upload
Build Verification
Before creating a Pull Request, run:
mvn clean test
For the frontend:
npm test
The exact commands may evolve as the testing infrastructure is
implemented.
Security Checklist
Before committing code:
- No passwords in source code
- No API keys in source code
- No JWT secrets in source code
- .env is ignored by Git
- .env.example contains only placeholder values
- No sensitive logs are committed
- Tests do not contain production credentials