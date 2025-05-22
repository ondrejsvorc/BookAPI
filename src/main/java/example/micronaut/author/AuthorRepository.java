package example.micronaut.author;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class AuthorRepository implements IAuthorRepository {
    private final List<Author> authors = new ArrayList<>();

    @PostConstruct
    public void init() {
        create(new Author(UUID.fromString("1d3a3822-e9f9-491d-8f24-fb3432e7f3b5"), "J.K. Rowling"));
        create(new Author(UUID.fromString("6d8d90f8-62aa-41e1-bae3-a204f0697276"), "George R.R. Martin"));
        create(new Author(UUID.fromString("c3efc3bf-92d0-4b97-b596-545717d69cdc"), "J.R.R. Tolkien"));
    }

    @Override
    public Optional<Author> findById(UUID id) {
        return authors.stream().filter(author -> author.getId().equals(id)).findFirst();
    }

    @Override
    public List<Author> findAll() {
        return new ArrayList<>(authors);
    }

    @Override
    public boolean create(Author author) {
        return authors.add(author);
    }

    @Override
    public boolean update(UUID id, String newName) {
        Optional<Author> author = findById(id);

        if (author.isEmpty()) {
            return false;
        }

        authors.remove(author.get());
        authors.add(new Author(id, newName));
        return true;
    }

    @Override
    public boolean delete(UUID id) {
        return authors.removeIf(author -> author.getId().equals(id));
    }
}
