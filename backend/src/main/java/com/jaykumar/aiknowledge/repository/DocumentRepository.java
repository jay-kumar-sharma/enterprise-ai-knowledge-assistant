package com.jaykumar.aiknowledge.repository;

import com.jaykumar.aiknowledge.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}