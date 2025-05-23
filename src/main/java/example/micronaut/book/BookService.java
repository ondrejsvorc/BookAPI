package example.micronaut.book;

import example.micronaut.author.Author;
import example.micronaut.author.IAuthorRepository;
import example.micronaut.book.create.CreateBookRequest;
import example.micronaut.book.create.CreateBookResponse;
import example.micronaut.book.delete.DeleteBookRequest;
import example.micronaut.book.delete.DeleteBookResponse;
import example.micronaut.book.get.GetBookRequest;
import example.micronaut.book.get.GetBookResponse;
import example.micronaut.book.get.GetBooksRequest;
import example.micronaut.book.get.GetBooksResponse;
import example.micronaut.book.update.UpdateBookRequest;
import example.micronaut.book.update.UpdateBookResponse;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class BookService implements IBookService {
    private final IBookRepository repository;
    private final BookValidator validator;
    private final BookMapper mapper;
    private final IAuthorRepository authorRepository;

    public BookService(IBookRepository repository, BookValidator validator, BookMapper mapper, IAuthorRepository authorRepository) {
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
        this.authorRepository = authorRepository;
    }

    @Override
    public GetBookResponse getBook(GetBookRequest request) {
        Optional<Book> book = repository.findById(request.id());
        return book.map(mapper::toGetBookResponse).orElse(null);
    }

    @Override
    public GetBooksResponse getBooks(GetBooksRequest request) {
        List<GetBookResponse> books = repository.findAll()
                .stream()
                .map(mapper::toGetBookResponse)
                .collect(Collectors.toList());
        return new GetBooksResponse(books);
    }

    @Override
    public CreateBookResponse createBook(CreateBookRequest request) {
        if (!validator.isValid(request.name(), request.authorId())) {
            return new CreateBookResponse(null, false);
        }

        Optional<Author> author = authorRepository.findById(request.authorId());
        if (author.isEmpty()) {
            return new CreateBookResponse(null, false);
        }

        Book book = new Book(request.name(), request.authorId());
        repository.create(book);
        return new CreateBookResponse(book.getId(), true);
    }

    @Override
    public UpdateBookResponse updateBook(UpdateBookRequest request) {
        if (!validator.isValid(request.name(), request.authorId())) {
            return new UpdateBookResponse(request.id(), false);
        }

        Optional<Author> author = authorRepository.findById(request.authorId());
        if (author.isEmpty()) {
            return new UpdateBookResponse(request.id(), false);
        }

        boolean isUpdated = repository.update(request.id(), request.name(), request.authorId());
        return new UpdateBookResponse(request.id(), isUpdated);
    }

    @Override
    public DeleteBookResponse deleteBook(DeleteBookRequest request) {
        boolean isDeleted = repository.delete(request.id());
        return new DeleteBookResponse(request.id(), isDeleted);
    }
}