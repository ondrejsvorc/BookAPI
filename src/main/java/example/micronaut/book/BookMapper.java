package example.micronaut.book;

import example.micronaut.author.Author;
import example.micronaut.author.AuthorRepository;
import example.micronaut.author.get.GetAuthorResponse;
import example.micronaut.book.get.GetBookResponse;
import jakarta.inject.Singleton;
import java.util.Optional;

@Singleton
public class BookMapper {
    private final AuthorRepository authorRepository;

    public BookMapper(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public GetBookResponse toGetBookResponse(Book book) {
        Optional<Author> author = authorRepository.findById(book.getAuthorId());

        if (author.isEmpty()) {
            throw new IllegalStateException("Author not found for book: " + book.getId());
        }

        return toGetBookResponse(book, author.get());
    }

    public GetBookResponse toGetBookResponse(Book book, Author author) {
        if (!book.getAuthorId().equals(author.getId())) {
            throw new IllegalArgumentException("Book authorId does not match provided author ID");
        }

        return new GetBookResponse(
            book.getId(),
            book.getName(),
            new GetAuthorResponse(author.getId(), author.getName())
        );
    }
}
