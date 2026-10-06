# Docker Setup

## Overview

Docker will be used to provide a consistent development and deployment
environment for the application.

The project is expected to use containers for services such as:

- PostgreSQL
- pgvector
- Spring Boot backend
- Angular frontend

The exact container architecture will be finalized during
implementation.

---

# Why Docker?

Docker provides isolated and reproducible environments.

Without Docker:

```text
Developer Machine
      │
      ├── Java
      ├── Maven
      ├── PostgreSQL
      ├── Node.js
      └── Other Dependencies


With Docker:
Developer Machine
      │
      ▼
Docker
      │
      ├── PostgreSQL + pgvector
      ├── Spring Boot
      └── Angular
This helps reduce environment-related differences between developers.
Prerequisites
Install Docker Desktop.
Verify the installation:
docker --version
Verify Docker Compose:
docker compose version
Docker Desktop must be running before starting the containers.
Docker Compose
The project will use:
docker-compose.yml
This file will define the application's containerized services.
A planned architecture is:
                 Docker Compose
                      │
        ┌─────────────┼─────────────┐
        │             │             │
        ▼             ▼             ▼
   PostgreSQL     Backend       Frontend
   + pgvector   Spring Boot     Angular
PostgreSQL Container
The PostgreSQL container will provide the relational database used by
the application.
The database will also provide vector storage through the pgvector
extension.
Expected configuration:
Host: localhost
Port: 5432
Database: ai_knowledge
The exact image and version will be defined in docker-compose.yml.
Backend Container
The Spring Boot backend will eventually run inside a Docker container.
Conceptually:
Spring Boot Application
          │
          ▼
      Docker Image
          │
          ▼
      Backend Container
The backend container will communicate with the PostgreSQL container
through the Docker Compose network.
Frontend Container
The Angular frontend may be packaged into a Docker image.
A production-oriented architecture may use:
Angular Build
     │
     ▼
Static Files
     │
     ▼
Web Server
     │
     ▼
Frontend Container
The final frontend container architecture will be defined during
implementation.
Docker Network
Docker Compose automatically provides networking between services.
Conceptually:
┌──────────────────────────────────────────┐
│             Docker Network               │
│                                          │
│  ┌─────────────┐     ┌───────────────┐  │
│  │   Backend   │────▶│  PostgreSQL   │  │
│  │ Spring Boot │     │   + pgvector  │  │
│  └─────────────┘     └───────────────┘  │
│                                          │
│  ┌─────────────┐                         │
│  │  Frontend   │────▶ Backend            │
│  │   Angular   │                         │
│  └─────────────┘                         │
│                                          │
└──────────────────────────────────────────┘
Inside Docker Compose, services should communicate using service names
rather than relying on localhost.
Starting the Application
Once docker-compose.yml has been implemented:
docker compose up -d
Check running containers:
docker compose ps
View logs:
docker compose logs
View backend logs:
docker compose logs backend
Follow backend logs:
docker compose logs -f backend
Stopping the Application
Stop containers:
docker compose stop
Stop and remove containers:
docker compose down
To remove containers and associated volumes:
docker compose down -v
Warning: removing database volumes can delete local database data.
Use docker compose down -v only when you intentionally want to reset
the local database environment.
Rebuilding Images
After changing Docker-related configuration or application code when
required:
docker compose build
Start the rebuilt services:
docker compose up -d
Or:
docker compose up -d --build
Checking Container Status
Run:
docker compose ps
Example:
NAME                         STATUS
------------------------------------------------
ai-knowledge-postgres       running
ai-knowledge-backend        running
ai-knowledge-frontend       running
Actual container names depend on the Docker Compose configuration.
Checking PostgreSQL
Once PostgreSQL is running, verify the container:
docker compose ps
Then inspect its logs:
docker compose logs postgres
The service name may differ depending on the final Compose file.
Environment Variables
Docker services should receive configuration through environment
variables.
Example:
DB_HOST=postgres
DB_PORT=5432
DB_NAME=ai_knowledge
DB_USERNAME=postgres
DB_PASSWORD=<password>

JWT_SECRET=<secret>

AI_API_KEY=<provider-api-key>

SERVER_PORT=8080
Notice that the database host inside Docker may be:
postgres
rather than:
localhost
because Docker Compose services communicate using service names.
Persistent Database Storage
PostgreSQL data should use a Docker volume.
Conceptually:
PostgreSQL Container
        │
        ▼
Docker Volume
        │
        ▼
Persistent Database Data
This prevents normal container recreation from automatically deleting
the database contents.
The exact volume configuration will be defined in
docker-compose.yml.
Development vs Production
Docker configuration should distinguish between development and
production requirements.
Development
Focus on:
- Fast development
- Local database
- Debugging
- Easy service startup
- Developer-friendly configuration
Production
Focus on:
- Security
- Minimal images
- Resource limits
- Secret management
- Health checks
- Logging
- Monitoring
- Reliable deployments
Development configuration should not automatically be treated as
production configuration.
Docker Security
The following practices should be followed:
- Do not hardcode secrets in Dockerfiles.
- Do not commit production credentials.
- Do not expose unnecessary ports.
- Use trusted base images.
- Keep images updated.
- Run containers with appropriate permissions.
- Avoid running applications as root when practical.
- Do not place API keys inside source code.
- Use environment-based configuration.
- Review exposed services before production deployment.
Dockerfile Guidelines
Each application container should have a dedicated Dockerfile where
appropriate.
A production Java container should ideally separate:
Build Stage
    │
    ▼
Application Artifact
    │
    ▼
Runtime Stage
This multi-stage approach can reduce the size of the final runtime
image.
The exact Dockerfile will be implemented after the Spring Boot
application structure is established.
Health Checks
Containerized services should eventually provide health checks.
For example:
PostgreSQL
    │
    ▼
Database Health

Backend
    │
    ▼
Application Health

Frontend
    │
    ▼
Web Server Health
Spring Boot Actuator may be used for backend health information.
Troubleshooting
Container Not Starting
Check:
docker compose ps
Then inspect logs:
docker compose logs <service-name>
Port Already in Use
Check the port:
lsof -i :8080
For PostgreSQL:
lsof -i :5432
Stop the conflicting service or change the configured port.
Backend Cannot Connect to Database
Verify:
PostgreSQL container is running
Database name is correct
Database credentials are correct
Backend uses the Docker service name
Docker network is available
Inside Docker Compose, avoid using:
DB_HOST=localhost
for communication from the backend container to PostgreSQL.
Use the PostgreSQL service name instead.
Container Logs
Use:
docker compose logs -f
or for a specific service:
docker compose logs -f backend
Recommended Development Workflow
Write Code
    │
    ▼
Run Tests
    │
    ▼
Build Application
    │
    ▼
Build Docker Image
    │
    ▼
Start Docker Compose
    │
    ▼
Verify Services
    │
    ▼
Test APIs