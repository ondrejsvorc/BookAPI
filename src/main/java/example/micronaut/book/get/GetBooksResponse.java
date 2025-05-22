package example.micronaut.book.get;

import io.micronaut.serde.annotation.Serdeable;
import java.util.List;

@Serdeable
public record GetBooksResponse(List<GetBookResponse> books) {}
