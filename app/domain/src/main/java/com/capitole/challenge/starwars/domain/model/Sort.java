package com.capitole.challenge.starwars.domain.model;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@lombok.Builder
public record Sort(String field, String order) {

  /** Supported sortable fields. Using enum avoids hardcoding strings in comparator logic. */
  private enum SortField { NAME, CREATED }
  private enum SortOrder { ASC, DESC }

  private static final Map<SortField, Function<Sorteable, String>> EXTRACTORS = Map.of(
      SortField.NAME, Sorteable::name,
      SortField.CREATED, Sorteable::created
  );

  /** Instance method used by adapters to obtain a Comparator for any Sorteable. */
  public Comparator<Sorteable> getComparator() {
    SortField sortField = parseField(field);
    SortOrder sortOrder = parseOrder(order);
    Comparator<String> stringComparator = Comparator.nullsLast(String::compareToIgnoreCase);
    Comparator<Sorteable> comparator = Comparator.comparing(EXTRACTORS.get(sortField), stringComparator);
    if (sortOrder == SortOrder.DESC) {
      comparator = comparator.reversed();
    }
    return comparator;
  }

  private static SortField parseField(String f) {
    if (f == null || f.isBlank()) {
      return SortField.NAME;
    }
    try {
      return SortField.valueOf(f.trim().toUpperCase());
    } catch (IllegalArgumentException ex) {
      return SortField.NAME;
    }
  }

  private static SortOrder parseOrder(String o) {
    if (Objects.isNull(o)) { return SortOrder.ASC; };
    try {
      return SortOrder.valueOf(o.trim().toUpperCase());
    } catch (IllegalArgumentException ex) {
      return SortOrder.ASC;
    }
  }
}
