package com.capitole.challenge.starwars.infrastructure.rest.client.dto;

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
