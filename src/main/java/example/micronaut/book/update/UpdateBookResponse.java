package example.micronaut.book.update;

import io.micronaut.serde.annotation.Serdeable;
import java.util.UUID;

@Serdeable
public record UpdateBookResponse(UUID id, boolean isUpdated) {}
