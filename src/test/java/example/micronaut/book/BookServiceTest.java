package example.micronaut.book;

import example.micronaut.author.Author;
import example.micronaut.author.AuthorRepository;
import example.micronaut.author.get.GetAuthorResponse;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookValidator bookValidator;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private AuthorRepository authorRepository;

    private BookService bookService;
    private UUID bookId;
    private UUID authorId;
    private Book book;
    private Author author;
    private GetBookResponse bookResponse;

    @BeforeEach
    void setUp() {
        bookService = new BookService(bookRepository, bookValidator, bookMapper, authorRepository);
        bookId = UUID.randomUUID();
        authorId = UUID.randomUUID();
        author = new Author(authorId, "Test Author");
        book = new Book(bookId, "Test Book", authorId);
        GetAuthorResponse authorResponse = new GetAuthorResponse(authorId, "Test Author");
        bookResponse = new GetBookResponse(bookId, "Test Book", authorResponse);
    }

    @Test
    void getBook_ShouldReturnBook_WhenBookExists() {
        GetBookRequest request = new GetBookRequest(bookId);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.toGetBookResponse(book)).thenReturn(bookResponse);

        GetBookResponse response = bookService.getBook(request);

        assertNotNull(response);
        assertEquals(bookId, response.id());
        assertEquals("Test Book", response.name());
        assertEquals(authorId, response.author().id());
        assertEquals("Test Author", response.author().name());
        verify(bookRepository).findById(bookId);
        verify(bookMapper).toGetBookResponse(book);
    }

    @Test
    void getBook_ShouldReturnNull_WhenBookDoesNotExist() {
        GetBookRequest request = new GetBookRequest(bookId);
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        GetBookResponse response = bookService.getBook(request);

        assertNull(response);
        verify(bookRepository).findById(bookId);
        verify(bookMapper, never()).toGetBookResponse(any());
    }

    @Test
    void getBooks_ShouldReturnAllBooks() {
        List<Book> books = new ArrayList<>();
        books.add(book);
        UUID anotherBookId = UUID.randomUUID();
        books.add(new Book(anotherBookId, "Another Book", authorId));

        List<GetBookResponse> bookResponses = new ArrayList<>();
        bookResponses.add(bookResponse);
        GetAuthorResponse authorResponse = new GetAuthorResponse(authorId, "Test Author");
        bookResponses.add(new GetBookResponse(anotherBookId, "Another Book", authorResponse));

        when(bookRepository.findAll()).thenReturn(books);
        when(bookMapper.toGetBookResponse(books.get(0))).thenReturn(bookResponses.get(0));
        when(bookMapper.toGetBookResponse(books.get(1))).thenReturn(bookResponses.get(1));

        GetBooksResponse response = bookService.getBooks(new GetBooksRequest());

        assertNotNull(response);
        assertEquals(2, response.books().size());
        verify(bookRepository).findAll();
        verify(bookMapper, times(2)).toGetBookResponse(any(Book.class));
    }

    @Test
    void createBook_ShouldCreateBook_WhenValidRequest() {
        CreateBookRequest request = new CreateBookRequest("Test Book", authorId);
        when(bookValidator.isValid("Test Book", authorId)).thenReturn(true);
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(bookRepository.create(any(Book.class))).thenReturn(true);

        CreateBookResponse response = bookService.createBook(request);

        assertNotNull(response);
        assertTrue(response.isCreated());
        assertNotNull(response.id());
        verify(bookValidator).isValid("Test Book", authorId);
        verify(authorRepository).findById(authorId);
        verify(bookRepository).create(any(Book.class));
    }

    @Test
    void createBook_ShouldNotCreateBook_WhenInvalidRequest() {
        CreateBookRequest request = new CreateBookRequest(null, authorId);
        when(bookValidator.isValid(null, authorId)).thenReturn(false);

        CreateBookResponse response = bookService.createBook(request);

        assertNotNull(response);
        assertFalse(response.isCreated());
        assertNull(response.id());
        verify(bookValidator).isValid(null, authorId);
        verify(bookRepository, never()).create(any(Book.class));
    }

    @Test
    void createBook_ShouldNotCreateBook_WhenAuthorDoesNotExist() {
        CreateBookRequest request = new CreateBookRequest("Test Book", authorId);
        when(bookValidator.isValid("Test Book", authorId)).thenReturn(true);
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        CreateBookResponse response = bookService.createBook(request);

        assertNotNull(response);
        assertFalse(response.isCreated());
        assertNull(response.id());
        verify(bookValidator).isValid("Test Book", authorId);
        verify(authorRepository).findById(authorId);
        verify(bookRepository, never()).create(any(Book.class));
    }

    @Test
    void updateBook_ShouldUpdateBook_WhenValidRequest() {
        UpdateBookRequest request = new UpdateBookRequest(bookId, "Updated Book", authorId);
        when(bookValidator.isValid("Updated Book", authorId)).thenReturn(true);
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(bookRepository.update(bookId, "Updated Book", authorId)).thenReturn(true);

        UpdateBookResponse response = bookService.updateBook(request);

        assertNotNull(response);
        assertTrue(response.isUpdated());
        assertEquals(bookId, response.id());
        verify(bookValidator).isValid("Updated Book", authorId);
        verify(authorRepository).findById(authorId);
        verify(bookRepository).update(bookId, "Updated Book", authorId);
    }

    @Test
    void updateBook_ShouldNotUpdateBook_WhenInvalidRequest() {
        UpdateBookRequest request = new UpdateBookRequest(bookId, null, authorId);
        when(bookValidator.isValid(null, authorId)).thenReturn(false);

        UpdateBookResponse response = bookService.updateBook(request);

        assertNotNull(response);
        assertFalse(response.isUpdated());
        assertEquals(bookId, response.id());
        verify(bookValidator).isValid(null, authorId);
        verify(bookRepository, never()).update(any(), any(), any());
    }

    @Test
    void updateBook_ShouldNotUpdateBook_WhenAuthorDoesNotExist() {
        UpdateBookRequest request = new UpdateBookRequest(bookId, "Updated Book", authorId);
        when(bookValidator.isValid("Updated Book", authorId)).thenReturn(true);
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        UpdateBookResponse response = bookService.updateBook(request);

        assertNotNull(response);
        assertFalse(response.isUpdated());
        assertEquals(bookId, response.id());
        verify(bookValidator).isValid("Updated Book", authorId);
        verify(authorRepository).findById(authorId);
        verify(bookRepository, never()).update(any(), any(), any());
    }

    @Test
    void deleteBook_ShouldDeleteBook_WhenBookExists() {
        DeleteBookRequest request = new DeleteBookRequest(bookId);
        when(bookRepository.delete(bookId)).thenReturn(true);

        DeleteBookResponse response = bookService.deleteBook(request);

        assertNotNull(response);
        assertTrue(response.isDeleted());
        assertEquals(bookId, response.id());
        verify(bookRepository).delete(bookId);
    }

    @Test
    void deleteBook_ShouldNotDeleteBook_WhenBookDoesNotExist() {
        DeleteBookRequest request = new DeleteBookRequest(bookId);
        when(bookRepository.delete(bookId)).thenReturn(false);

        DeleteBookResponse response = bookService.deleteBook(request);

        assertNotNull(response);
        assertFalse(response.isDeleted());
        assertEquals(bookId, response.id());
        verify(bookRepository).delete(bookId);
    }
}
