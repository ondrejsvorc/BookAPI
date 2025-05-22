package example.micronaut.book.create;

import io.micronaut.serde.annotation.Serdeable;
import java.util.UUID;

@Serdeable
public record CreateBookResponse(UUID id, boolean isCreated) {}