package example.micronaut.book.update;

import java.util.UUID;

public record UpdateBookRequest(UUID id, String name, UUID authorId) {}
