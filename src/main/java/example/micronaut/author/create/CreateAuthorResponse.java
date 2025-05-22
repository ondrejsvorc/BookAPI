package example.micronaut.author.create;

import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

@Serdeable
public record CreateAuthorResponse(UUID id, boolean isCreated) {}
