package com.capitole.challenge.starwars.infrastructure.rest.client.dto;

/**
 /**
 * @author alex.vall
 * Starship entity from swapi.info

 * @param name
 * @param model
 * @param manufacturer
 * @param costInCredits
 * @param length
 * @param maxAtmospheringSpeed
 * @param crew
 * @param passengers
 * @param cargoCapacity
 * @param consumables
 * @param hyperdriveRating
 * @param mglt
 * @param starshipClass
 * @param created
 * @param edited
 * @param url
 */
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
