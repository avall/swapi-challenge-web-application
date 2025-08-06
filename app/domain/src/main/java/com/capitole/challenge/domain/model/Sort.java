package com.capitole.challenge.domain.model;

@lombok.Builder
public record Sort(String field, SortOrder order) {}
