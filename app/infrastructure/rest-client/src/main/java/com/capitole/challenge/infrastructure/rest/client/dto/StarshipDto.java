package com.capitole.challenge.infrastructure.rest.client.dto;

@lombok.Builder
public record StarshipDto (
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
    String url) {
}
