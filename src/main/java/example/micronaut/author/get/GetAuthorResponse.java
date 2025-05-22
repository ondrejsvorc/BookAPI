package example.micronaut.author.get;

import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

@Serdeable
public record GetAuthorResponse(UUID id, String name) {};
