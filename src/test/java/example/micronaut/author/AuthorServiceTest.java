package example.micronaut.author;

import example.micronaut.author.create.CreateAuthorRequest;
import example.micronaut.author.create.CreateAuthorResponse;
import example.micronaut.author.delete.DeleteAuthorRequest;
import example.micronaut.author.delete.DeleteAuthorResponse;
import example.micronaut.author.get.GetAuthorRequest;
import example.micronaut.author.get.GetAuthorResponse;
import example.micronaut.author.get.GetAuthorsRequest;
import example.micronaut.author.get.GetAuthorsResponse;
import example.micronaut.author.update.UpdateAuthorRequest;
import example.micronaut.author.update.UpdateAuthorResponse;
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
public class AuthorServiceTest {
    @Mock
    private IAuthorRepository authorRepository;

    @Mock
    private AuthorValidator authorValidator;

    @Mock
    private AuthorMapper authorMapper;

    private AuthorService authorService;
    private UUID authorId;
    private Author author;
    private GetAuthorResponse authorResponse;

    @BeforeEach
    void setUp() {
        authorService = new AuthorService(authorRepository, authorValidator, authorMapper);
        authorId = UUID.randomUUID();
        author = new Author(authorId, "Test Author");
        authorResponse = new GetAuthorResponse(authorId, "Test Author");
    }

    @Test
    void getAuthor_ShouldReturnAuthor_WhenAuthorExists() {
        // Arrange
        GetAuthorRequest request = new GetAuthorRequest(authorId);
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(authorMapper.toGetAuthorResponse(author)).thenReturn(authorResponse);

        // Act
        GetAuthorResponse response = authorService.getAuthor(request);

        // Assert
        assertNotNull(response);
        assertEquals(authorId, response.id());
        assertEquals("Test Author", response.name());
        verify(authorRepository).findById(authorId);
        verify(authorMapper).toGetAuthorResponse(author);
    }

    @Test
    void getAuthor_ShouldReturnNull_WhenAuthorDoesNotExist() {
        // Arrange
        GetAuthorRequest request = new GetAuthorRequest(authorId);
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        // Act
        GetAuthorResponse response = authorService.getAuthor(request);

        // Assert
        assertNull(response);
        verify(authorRepository).findById(authorId);
        verify(authorMapper, never()).toGetAuthorResponse(any());
    }

    @Test
    void getAuthors_ShouldReturnAllAuthors() {
        // Arrange
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        authors.add(new Author(UUID.randomUUID(), "Another Author"));

        List<GetAuthorResponse> authorResponses = new ArrayList<>();
        authorResponses.add(authorResponse);
        authorResponses.add(new GetAuthorResponse(UUID.randomUUID(), "Another Author"));

        when(authorRepository.findAll()).thenReturn(authors);
        when(authorMapper.toGetAuthorResponse(authors.get(0))).thenReturn(authorResponses.get(0));
        when(authorMapper.toGetAuthorResponse(authors.get(1))).thenReturn(authorResponses.get(1));

        // Act
        GetAuthorsResponse response = authorService.getAuthors(new GetAuthorsRequest());

        // Assert
        assertNotNull(response);
        assertEquals(2, response.authors().size());
        verify(authorRepository).findAll();
        verify(authorMapper, times(2)).toGetAuthorResponse(any(Author.class));
    }

    @Test
    void createAuthor_ShouldCreateAuthor_WhenValidRequest() {
        // Arrange
        CreateAuthorRequest request = new CreateAuthorRequest("Test Author");
        when(authorValidator.isValid("Test Author")).thenReturn(true);
        when(authorRepository.create(any(Author.class))).thenReturn(true);

        // Act
        CreateAuthorResponse response = authorService.createAuthor(request);

        // Assert
        assertNotNull(response);
        assertTrue(response.isCreated());
        assertNotNull(response.id());
        verify(authorValidator).isValid("Test Author");
        verify(authorRepository).create(any(Author.class));
    }

    @Test
    void createAuthor_ShouldNotCreateAuthor_WhenInvalidRequest() {
        // Arrange
        CreateAuthorRequest request = new CreateAuthorRequest(null);
        when(authorValidator.isValid(null)).thenReturn(false);

        // Act
        CreateAuthorResponse response = authorService.createAuthor(request);

        // Assert
        assertNotNull(response);
        assertFalse(response.isCreated());
        assertNull(response.id());
        verify(authorValidator).isValid(null);
        verify(authorRepository, never()).create(any(Author.class));
    }

    @Test
    void updateAuthor_ShouldUpdateAuthor_WhenValidRequest() {
        // Arrange
        UpdateAuthorRequest request = new UpdateAuthorRequest(authorId, "Updated Author");
        when(authorValidator.isValid("Updated Author")).thenReturn(true);
        when(authorRepository.update(authorId, "Updated Author")).thenReturn(true);

        // Act
        UpdateAuthorResponse response = authorService.updateAuthor(request);

        // Assert
        assertNotNull(response);
        assertTrue(response.isUpdated());
        assertEquals(authorId, response.id());
        verify(authorValidator).isValid("Updated Author");
        verify(authorRepository).update(authorId, "Updated Author");
    }

    @Test
    void updateAuthor_ShouldNotUpdateAuthor_WhenInvalidRequest() {
        // Arrange
        UpdateAuthorRequest request = new UpdateAuthorRequest(authorId, null);
        when(authorValidator.isValid(null)).thenReturn(false);

        // Act
        UpdateAuthorResponse response = authorService.updateAuthor(request);

        // Assert
        assertNotNull(response);
        assertFalse(response.isUpdated());
        assertEquals(authorId, response.id());
        verify(authorValidator).isValid(null);
        verify(authorRepository, never()).update(any(), any());
    }

    @Test
    void deleteAuthor_ShouldDeleteAuthor_WhenAuthorExists() {
        // Arrange
        DeleteAuthorRequest request = new DeleteAuthorRequest(authorId);
        when(authorRepository.delete(authorId)).thenReturn(true);

        // Act
        DeleteAuthorResponse response = authorService.deleteAuthor(request);

        // Assert
        assertNotNull(response);
        assertTrue(response.isDeleted());
        assertEquals(authorId, response.id());
        verify(authorRepository).delete(authorId);
    }

    @Test
    void deleteAuthor_ShouldNotDeleteAuthor_WhenAuthorDoesNotExist() {
        // Arrange
        DeleteAuthorRequest request = new DeleteAuthorRequest(authorId);
        when(authorRepository.delete(authorId)).thenReturn(false);

        // Act
        DeleteAuthorResponse response = authorService.deleteAuthor(request);

        // Assert
        assertNotNull(response);
        assertFalse(response.isDeleted());
        assertEquals(authorId, response.id());
        verify(authorRepository).delete(authorId);
    }
}