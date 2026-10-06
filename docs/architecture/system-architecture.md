# System Architecture

## Overview

The Enterprise AI Knowledge Base & Customer Support Assistant is
designed as a full-stack application that combines a Spring Boot
backend with Generative AI capabilities.

The system allows administrators to manage enterprise knowledge
documents and authenticated users to ask natural-language questions
about the available knowledge base.

## High-Level Architecture

```text
                         ┌──────────────────────┐
                         │        User          │
                         └──────────┬───────────┘
                                    │
                                    ▼
                         ┌──────────────────────┐
                         │   Angular Frontend   │
                         └──────────┬───────────┘
                                    │
                              REST APIs
                                    │
                                    ▼
                         ┌──────────────────────┐
                         │    Spring Boot API   │
                         └──────────┬───────────┘
                                    │
               ┌────────────────────┼────────────────────┐
               │                    │                    │
               ▼                    ▼                    ▼
        ┌─────────────┐      ┌─────────────┐      ┌─────────────┐
        │  Security   │      │  PostgreSQL │      │  Spring AI  │
        │ JWT / RBAC  │      │  + pgvector │      │             │
        └─────────────┘      └─────────────┘      └──────┬──────┘
                                                         │
                                                         ▼
                                                  ┌─────────────┐
                                                  │     LLM     │
                                                  └─────────────┘


