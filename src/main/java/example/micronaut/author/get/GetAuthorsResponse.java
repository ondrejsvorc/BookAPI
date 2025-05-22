package example.micronaut.author.get;

import io.micronaut.serde.annotation.Serdeable;

import java.util.List;

@Serdeable
public record GetAuthorsResponse(List<GetAuthorResponse> authors) {}
