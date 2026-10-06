# Development Guide

## Overview

This guide defines the recommended development workflow for the
Enterprise AI Knowledge Base & Customer Support Assistant.

The goal is to keep development organized, maintainable, testable, and
easy for other contributors to understand.

---

# Development Principles

The project follows this general development cycle:

```text
Understand
    ↓
Design
    ↓
Implement
    ↓
Test
    ↓
Document
    ↓
Review
    ↓
Commit
    ↓
Push
    ↓
Pull Request
    ↓
Review
    ↓
Merge

Before Starting Development
Always make sure the local repository is up to date.
git checkout main
git pull origin main
Then create a feature branch.
Example:
git checkout -b feature/user-authentication
Branch Naming
Branches should clearly describe their purpose.
Recommended prefixes:
feature/
bugfix/
hotfix/
refactor/
test/
docs/
chore/
Examples:
feature/user-authentication
feature/document-upload
feature/rag-pipeline
feature/ai-chat
bugfix/jwt-validation
refactor/document-service
test/authentication-tests
docs/update-api-documentation
chore/docker-configuration
Avoid unclear branch names such as:
mybranch
test
changes
new
final
final2
latest
Feature Development Workflow
For a new feature:
GitHub Issue
     ↓
Create Branch
     ↓
Understand Requirements
     ↓
Design Solution
     ↓
Implement
     ↓
Write Tests
     ↓
Update Documentation
     ↓
Review Local Changes
     ↓
Commit
     ↓
Push
     ↓
Pull Request
GitHub Issues
Each significant feature should have a GitHub Issue.
Example:
Title:
Implement JWT Authentication

Description:

Implement secure user authentication using Spring Security and JWT.

Tasks:
- Create User entity
- Create registration API
- Create login API
- Configure PasswordEncoder
- Implement JWT generation
- Implement JWT validation
- Configure SecurityFilterChain
- Add authentication tests
- Update API documentation
Issues should describe what needs to be built rather than only listing
implementation details.
Creating a Feature Branch
Start from the latest main branch:
git checkout main
git pull origin main
Create the feature branch:
git checkout -b feature/user-authentication
Verify:
git branch
The current branch should be marked with *.
Project Structure
The project is organized into major areas:
enterprise-ai-knowledge-assistant/
│
├── backend/
├── frontend/
├── docs/
├── scripts/
│
├── .github/
├── README.md
├── CONTRIBUTING.md
├── SECURITY.md
├── CHANGELOG.md
├── .gitignore
├── .env.example
└── docker-compose.yml
The exact structure may evolve as implementation progresses.
Backend Development
The backend will use Java and Spring Boot.
The backend should follow clear separation of responsibilities.
A typical structure may look like:
backend/
└── src/
    └── main/
        └── java/
            └── .../
                ├── controller/
                ├── service/
                ├── repository/
                ├── entity/
                ├── dto/
                ├── security/
                ├── exception/
                └── config/
Responsibilities:
Controller
Handles HTTP requests and responses.
Service
Contains business logic.
Repository
Handles persistence and database access.
Entity
Represents persistent domain objects.
DTO
Defines API request and response structures.
Security
Contains authentication and authorization logic.
Exception
Contains application-specific exception handling.
Config
Contains application configuration.
Backend Coding Guidelines
Prefer:
- Clear class names
- Small focused methods
- Constructor injection
- DTOs for API boundaries
- Centralized exception handling
- Bean validation
- Meaningful logging
- Secure configuration
- Testable services
Avoid:
- Hardcoded credentials
- Business logic inside controllers
- Large methods
- Duplicate code
- Unnecessary dependencies
- Sensitive information in logs
Frontend Development
The frontend will use Angular.
A typical structure may include:
frontend/
└── src/
    └── app/
        ├── core/
        ├── shared/
        ├── features/
        ├── services/
        └── components/
Frontend responsibilities should be separated into appropriate
components and services.
API Integration
The frontend communicates with the Spring Boot backend through REST
APIs.
Example:
Angular
   │
   │ HTTP
   ▼
Spring Boot REST API
   │
   ▼
Service Layer
   │
   ▼
Database / AI Layer
API URLs and configuration should not be hardcoded throughout frontend
components.
Centralize API configuration where practical.
AI Development
AI functionality should be implemented as a separate application
capability rather than mixing AI logic throughout unrelated services.
The planned flow is:
User
  ↓
Chat API
  ↓
RAG Service
  ↓
Retriever
  ↓
Vector Database
  ↓
Relevant Context
  ↓
LLM
  ↓
Response
AI-related code should remain modular so that the underlying provider
or model can be changed without rewriting the entire application.
Testing Workflow
Every significant feature should include appropriate tests.
The expected testing layers include:
Unit Tests
    ↓
Integration Tests
    ↓
API Tests
    ↓
End-to-End Tests
Testing should cover:
- Normal scenarios
- Validation failures
- Authentication failures
- Authorization failures
- Not-found scenarios
- Database interactions
- AI/RAG behavior where practical
Running Backend Tests
From the backend directory:
mvn test
For a complete build:
mvn clean verify
The exact commands may change as the Maven project is finalized.
API Testing
APIs should be tested using tools such as:
- Swagger UI
- Postman
- Automated integration tests
Example workflow:
Register
   ↓
Login
   ↓
Receive JWT
   ↓
Send JWT
   ↓
Call Protected API
   ↓
Verify Response
Code Review Before Commit
Before committing, inspect the changes:
git status
Review changed files:
git diff
Check staged changes:
git diff --cached
Make sure:
- No secrets are included.
- No unnecessary files are included.
- No debug code remains.
- Tests pass.
- Documentation is updated when required.
Commit Guidelines
Use clear and focused commit messages.
Recommended format:
type: short description
Examples:
feat: add user registration
feat: implement jwt authentication
feat: add document upload API
feat: implement vector similarity search

fix: handle invalid jwt token
fix: resolve document upload validation

test: add authentication integration tests

docs: update rag pipeline documentation

refactor: simplify document processing service

chore: configure docker compose
Avoid messages such as:
update
changes
done
final
working
test
Commit Size
Prefer small logical commits.
Good:
feat: add user entity
feat: add registration API
feat: add jwt authentication
test: add authentication tests
Avoid one huge commit:
added entire project
Small commits make changes easier to review and understand.
Push Changes
After committing:
git push -u origin feature/user-authentication
For later commits on the same branch:
git push
Pull Request
Create a Pull Request after the feature is complete.
A Pull Request should contain:
- What was implemented
- Why it was implemented
- Important design decisions
- Testing performed
- Screenshots where useful
- Related GitHub Issue
Example:
## Summary

Implemented JWT-based authentication.

## Changes

- Added User entity
- Added registration API
- Added login API
- Added JWT generation
- Added JWT validation
- Added Spring Security configuration

## Testing

- Registration API tested
- Login API tested
- Invalid credentials tested
- Protected endpoint tested

## Related Issue

Closes #12
Pull Request Checklist
Before requesting review:
- Code compiles
- Tests pass
- No secrets committed
- API changes documented
- README updated if necessary
- Database changes documented
- Security implications reviewed
- Commit messages are meaningful
- Branch is up to date
- Pull Request description is complete
Keeping Branches Updated
If main has changed while working on a feature:
git checkout main
git pull origin main
Then update the feature branch according to the project's preferred
merge/rebase strategy.
For a rebase workflow:
git checkout feature/user-authentication
git rebase main
Resolve conflicts if necessary and continue the rebase.
Handling Merge Conflicts
When Git reports a conflict:
1. Open the conflicted file.
2. Understand both changes.
3. Keep the correct implementation.
4. Remove conflict markers.
5. Test the application.
6. Stage the resolved file.
Example:
git add <file>
Continue the operation:
git rebase --continue
or complete the merge as appropriate.
Never resolve conflicts blindly.
Documentation Requirements
Documentation should be updated when behavior or architecture changes.
Relevant documentation includes:
README.md
docs/architecture/
docs/api/
docs/database/
docs/setup/
docs/development/
Examples:
- New API → update API documentation.
- Database change → update database design.
- Authentication change → update authentication flow.
- RAG change → update RAG pipeline.
- Setup change → update setup documentation.
Security Requirements
Never commit:
.env
Passwords
API keys
JWT secrets
Private keys
Production credentials
Database credentials
Before committing:
git status
Check the changed files carefully.
Daily Development Workflow
A practical daily workflow:
1. Pull latest main
       ↓
2. Review assigned GitHub Issue
       ↓
3. Create / switch to feature branch
       ↓
4. Implement small change
       ↓
5. Run tests
       ↓
6. Review git diff
       ↓
7. Commit
       ↓
8. Push
       ↓
9. Continue implementation
       ↓
10. Create Pull Request
Definition of Done
A feature is considered complete when:
Implementation
     +
Tests
     +
Documentation
     +
Code Review
     +
Security Review
     +
Successful Build
     +
Pull Request
All applicable requirements should be completed before merging.