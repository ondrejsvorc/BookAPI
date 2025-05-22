package example.micronaut.book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IBookRepository {
    Optional<Book> findById(UUID id);
    List<Book> findAll();
    boolean create(Book book);
    boolean update(UUID id, String newName, UUID authorId);
    boolean delete(UUID id);
}
