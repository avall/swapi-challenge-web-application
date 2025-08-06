package com.capitole.challenge.domain.model;

import lombok.Builder;

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
    String url) {

}
