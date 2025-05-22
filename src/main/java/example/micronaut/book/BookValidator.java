package example.micronaut.book;

import jakarta.inject.Singleton;

import java.util.UUID;

@Singleton
public class BookValidator {
    public boolean isValid(String name, UUID authorId) {
        return name != null && !name.isBlank() && authorId != null;
    }
}
