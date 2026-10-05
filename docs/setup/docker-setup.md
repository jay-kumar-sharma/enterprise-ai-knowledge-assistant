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