package com.capitole.challenge.domain.model;

import lombok.Builder;

@Builder
public record Starship(
    String name,
    String model,
    String manufacturer,
    String costInCredits,
    String length,
    String maxAtmospheringSpeed,
    String crew,
    String passengers,
    String cargoCapacity,
    String consumables,
    String hyperdriveRating,
    String mglt,
    String starshipClass,
    String created,
    String edited,
    String url) implements Sorteable {
}
