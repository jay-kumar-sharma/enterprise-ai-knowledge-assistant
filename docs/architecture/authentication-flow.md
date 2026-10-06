# Authentication Flow

## Overview

The Enterprise AI Knowledge Base & Customer Support Assistant
uses JWT-based authentication to secure REST APIs.

Authentication is responsible for:

- User registration
- User login
- Password hashing
- JWT token generation
- JWT token validation
- User identity extraction
- Role-based authorization

---

# Authentication Architecture

```text
                ┌─────────────────────┐
                │      Client         │
                │ Angular Frontend    │
                └──────────┬──────────┘
                           │
                           │ Login Request
                           ▼
                ┌─────────────────────┐
                │   Auth Controller   │
                └──────────┬──────────┘
                           │
                           ▼
                ┌─────────────────────┐
                │    Auth Service     │
                └──────────┬──────────┘
                           │
                  Validate Credentials
                           │
                           ▼
                ┌─────────────────────┐
                │     User Database   │
                └──────────┬──────────┘
                           │
                     User Valid
                           │
                           ▼
                ┌─────────────────────┐
                │     JWT Service     │
                └──────────┬──────────┘
                           │
                     Generate JWT
                           │
                           ▼
                ┌─────────────────────┐
                │   Client receives   │
                │      JWT Token      │
                └─────────────────────┘
            
                    
                    Registration Flow
                    
                    Client
                      │
                      │ POST /api/v1/auth/register
                      ▼
                    Auth Controller
                      │
                      ▼
                    Auth Service
                      │
                      ├── Validate request
                      │
                      ├── Check email
                      │
                      ├── Hash password
                      │
                      └── Create user
                              │
                              ▼
                          PostgreSQL
                          
                          
                          Login Flow
                          
                            Client
                              │
                              │ Email + Password
                              ▼
                            Auth Controller
                              │
                              ▼
                            Authentication Manager
                              │
                              ▼
                            User Details Service
                              │
                              ▼
                            Database
                              │
                              ├── User found
                              │
                              └── Password verified
                                      │
                                      ▼
                                  JWT Service
                                      │
                                      ▼
                                  JWT Token
                                      │
                                      ▼
                                    Client

                    


