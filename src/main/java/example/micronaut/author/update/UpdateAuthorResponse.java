package example.micronaut.author.update;

import io.micronaut.serde.annotation.Serdeable;
import java.util.UUID;

@Serdeable
public record UpdateAuthorResponse(UUID id, boolean isUpdated) {}
