package com.capitole.challenge.starwars.domain.model;

import lombok.Builder;

/**
 * @author alex.vall
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
@Builder
public record People(
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
    String url) implements Sorteable {

}
