package example.micronaut.author;

import example.micronaut.author.get.GetAuthorResponse;
import jakarta.inject.Singleton;

@Singleton
public class AuthorMapper {
    public GetAuthorResponse toGetAuthorResponse(Author author) {
        return new GetAuthorResponse(author.getId(), author.getName());
    }
}
