package example.micronaut.book.get;

import example.micronaut.author.get.GetAuthorResponse;
import io.micronaut.serde.annotation.Serdeable;
import java.util.UUID;

@Serdeable
public record GetBookResponse(UUID id, String name, GetAuthorResponse author) {}
