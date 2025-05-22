package example.micronaut.author;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAuthorRepository {
    Optional<Author> findById(UUID id);
    List<Author> findAll();
    boolean create(Author author);
    boolean update(UUID id, String newName);
    boolean delete(UUID id);
}

