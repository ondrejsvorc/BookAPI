package example.micronaut.book.delete;

import io.micronaut.serde.annotation.Serdeable;
import java.util.UUID;

@Serdeable
public record DeleteBookResponse(UUID id, boolean isDeleted) {}
