# Security Policy

## Overview

Security is an important part of the Enterprise AI Knowledge Base
& Customer Support Assistant.

The project follows secure development practices for authentication,
authorization, API access, database configuration, and AI provider
credentials.

## Supported Versions

The latest version on the `main` branch is the actively maintained
version.

| Version | Supported |
|---|---|
| Latest | Yes |
| Older versions | No |

## Reporting a Security Vulnerability

If you discover a security vulnerability, please report it
privately to the project maintainer.

Do not publicly disclose sensitive security issues before they
have been investigated.

## Sensitive Information

Never commit sensitive information to the repository, including:

- API keys
- Database passwords
- JWT secrets
- Access tokens
- Private credentials
- `.env` files
- Cloud credentials

Use environment variables for sensitive configuration.

## Environment Configuration

The repository contains:

```text
.env.example