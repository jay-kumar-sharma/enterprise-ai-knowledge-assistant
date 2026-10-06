# Environment Variables

## Overview

The application uses environment variables for configuration values
that should not be hardcoded into the source code.

This includes:

- Database credentials
- JWT secrets
- AI provider API keys
- Application configuration

---

# Why Environment Variables?

Sensitive configuration should not be committed to Git.

Examples of information that should remain outside source code:

```text
Database passwords
API keys
JWT secrets
Production credentials


Configuration File Structure
The project uses:
.env
.env.example
.env.example
Contains placeholder values and can safely be committed.
Example:
DB_HOST=localhost
DB_PORT=5432
DB_NAME=ai_knowledge
DB_USERNAME=postgres
DB_PASSWORD=change_me

JWT_SECRET=change_me

AI_API_KEY=change_me

SERVER_PORT=8080
.env
Contains actual local development values.
Example:
DB_HOST=localhost
DB_PORT=5432
DB_NAME=ai_knowledge
DB_USERNAME=postgres
DB_PASSWORD=<local-password>

JWT_SECRET=<local-secret>

AI_API_KEY=<provider-api-key>

SERVER_PORT=8080
The actual .env file must never be committed to Git.
Database Variables
DB_HOST
PostgreSQL server hostname.
Local development:
localhost
DB_PORT
PostgreSQL server port.
Default:
5432
DB_NAME
Application database name.
Example:
ai_knowledge
DB_USERNAME
Database username.
Example:
postgres
DB_PASSWORD
Password for the configured PostgreSQL user.
This value must not be committed to Git.
JWT Configuration
JWT_SECRET
Secret used for signing and validating JWT tokens.
Example:
JWT_SECRET=<strong-secret>
Production environments must use a securely generated secret.
Do not:
- Commit the secret to Git.
- Put the secret directly in Java source code.
- Share the secret publicly.
- Use a weak predictable secret.
AI Configuration
AI_API_KEY
API key used to communicate with the configured AI provider.
Example:
AI_API_KEY=<provider-api-key>
The exact provider will be configured during AI integration.
The API key must never be committed to Git.
Application Configuration
SERVER_PORT
Port used by the Spring Boot application.
Example:
SERVER_PORT=8080
The value may be changed if port 8080 is already in use.
Environment Separation
The application should support separate configuration for different
environments.
Example:
Development
    │
    ├── Local PostgreSQL
    ├── Development AI credentials
    └── Local configuration


Testing
    │
    ├── Test database
    ├── Test configuration
    └── Test credentials


Production
    │
    ├── Production database
    ├── Production AI configuration
    └── Production secrets
Production credentials must never be reused in local development.
Git Security
The root .gitignore contains rules to prevent .env files from being
committed.
Verify that .env is ignored:
git check-ignore -v .env
Git should report the .gitignore rule responsible for ignoring the
file.
Verify .env.example:
git check-ignore -v .env.example
.env.example should normally not be ignored because it is intended to
be committed as a configuration template.
Secret Rotation
If a secret is accidentally exposed:
1. Revoke the exposed secret immediately.
2. Generate a new secret.
3. Update the environment configuration.
4. Check Git history for the exposed value.
5. Remove the secret from repository history if necessary.
6. Review logs and affected systems.
Simply deleting a secret from the latest commit does not necessarily
remove it from Git history.
Production Secrets
Production secrets should preferably be managed using a dedicated
secret-management solution rather than committed configuration files.
Possible solutions include:
- Cloud secret managers
- CI/CD secret storage
- Container orchestration secrets
- Vault-based secret management
The exact solution will be selected when deployment architecture is
implemented.
Security Checklist
Before committing changes:
- .env is ignored.
- No API keys are present in source code.
- No database passwords are present in source code.
- No JWT secrets are present in source code.
- .env.example contains placeholders only.
- Logs do not expose sensitive values.
- Production credentials are not used locally.
- Test credentials are not production credentials.