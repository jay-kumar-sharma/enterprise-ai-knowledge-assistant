package com.jaykumar.aiknowledge.dto;

public interface VectorSearchProjection {

    Long getId();

    Long getDocumentId();

    Integer getChunkIndex();

    String getContent();

    Double getDistance();
}