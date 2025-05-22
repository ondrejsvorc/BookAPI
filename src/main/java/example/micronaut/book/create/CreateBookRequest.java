package example.micronaut.book.create;

import java.util.UUID;

public record CreateBookRequest(String name, UUID authorId) {}
