package example.micronaut.author;

import jakarta.inject.Singleton;

@Singleton
public class AuthorValidator {
    public boolean isValid(String name) {
        return name != null && !name.isBlank() && name.length() <= 100;
    }
}
