package example.micronaut.book;

import example.micronaut.author.Author;
import example.micronaut.author.AuthorRepository;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class BookRepository implements IBookRepository {
    private final List<Book> books = new ArrayList<>();
    private final AuthorRepository authorRepository;

    @PostConstruct
    public void init() {
        create(new Book("Harry Potter and the Sorcerer's Stone", UUID.fromString("1d3a3822-e9f9-491d-8f24-fb3432e7f3b5")));
        create(new Book("Game of Thrones", UUID.fromString("6d8d90f8-62aa-41e1-bae3-a204f0697276")));
        create(new Book("The Lord of the Rings", UUID.fromString("c3efc3bf-92d0-4b97-b596-545717d69cdc")));
    }

    public BookRepository(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Optional<Book> findById(UUID id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst();
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books);
    }

    @Override
    public boolean create(Book book) {
        return books.add(book);
    }

    @Override
    public boolean update(UUID id, String newName, UUID authorId) {
        Optional<Book> book = findById(id);
        Optional<Author> author = authorRepository.findById(authorId);

        if (book.isEmpty() || author.isEmpty()) {
            return false;
        }

        books.remove(book.get());
        books.add(new Book(id, newName, authorId));
        return true;
    }

    @Override
    public boolean delete(UUID id) {
        return books.removeIf(book -> book.getId().equals(id));
    }
}
