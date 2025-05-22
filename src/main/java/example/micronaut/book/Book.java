package example.micronaut.book;

import java.util.UUID;

public class Book {
    private final UUID id;
    private final String name;
    private final UUID authorId;

    public Book(UUID id, String name, UUID authorId) {
        this.id = id;
        this.name = name;
        this.authorId = authorId;
    }

    public Book(String name, UUID authorId) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.authorId = authorId;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public UUID getAuthorId() { return authorId; }
}