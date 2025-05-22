package example.micronaut.author.delete;

import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

@Serdeable
public record DeleteAuthorResponse(UUID id, boolean isDeleted) {}
