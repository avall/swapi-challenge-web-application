package com.capitole.challenge.starwars.infrastructure.rest.client.dto;

/**
 * @author alex.vall
 * People entity from swapi.info
 *
 * @param name
 * @param height
 * @param mass
 * @param hairColor
 * @param skinColor
 * @param eyeColor
 * @param birthYear
 * @param gender
 * @param homeworld
 * @param created
 * @param edited
 * @param url
 */
@lombok.Builder
public record PeopleDto (
    String name,
    String height,
    String mass,
    String hairColor,
    String skinColor,
    String eyeColor,
    String birthYear,
    String gender,
    String homeworld,
    String created,
    String edited,
    String url) {
}
