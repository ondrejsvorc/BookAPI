package example.micronaut.book;

import example.micronaut.author.AuthorRepository;
import jakarta.inject.Singleton;
import java.util.UUID;

@Singleton
public class BookValidator {
    private final AuthorRepository authorRepository;

    public BookValidator(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public boolean isValid(String name, UUID authorId) {
        return name != null && !name.isBlank() && authorId != null && authorRepository.findById(authorId).isPresent();
    }
}
