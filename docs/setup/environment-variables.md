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