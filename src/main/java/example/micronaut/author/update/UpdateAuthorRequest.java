package example.micronaut.author.update;

import java.util.UUID;

public record UpdateAuthorRequest(UUID id, String name) {}
