package com.capitole.challenge.starwars.domain.model;

import lombok.Builder;

/**
 * @author alex.vall
 *
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
