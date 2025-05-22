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
import jakarta.inject.Singleton;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class AuthorService implements IAuthorService {
    private final IAuthorRepository repository;
    private final AuthorValidator validator;
    private final AuthorMapper mapper;

    public AuthorService(IAuthorRepository repository, AuthorValidator validator, AuthorMapper mapper) {
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Override
    public GetAuthorResponse getAuthor(GetAuthorRequest request) {
        Optional<Author> author = repository.findById(request.id());
        return author.map(mapper::toGetAuthorResponse).orElse(null);
    }

    @Override
    public GetAuthorsResponse getAuthors(GetAuthorsRequest request) {
        List<GetAuthorResponse> authors = repository.findAll()
                .stream()
                .map(mapper::toGetAuthorResponse)
                .collect(Collectors.toList());
        return new GetAuthorsResponse(authors);
    }

    @Override
    public CreateAuthorResponse createAuthor(CreateAuthorRequest request) {
        if (!validator.isValid(request.name())) {
            return new CreateAuthorResponse(null, false);
        }

        Author author = new Author(request.name());
        repository.create(author);
        return new CreateAuthorResponse(author.getId(), true);
    }

    @Override
    public UpdateAuthorResponse updateAuthor(UpdateAuthorRequest request) {
        if (!validator.isValid(request.name())) {
            return new UpdateAuthorResponse(request.id(), false);
        }

        boolean updated = repository.update(request.id(), request.name());
        return new UpdateAuthorResponse(request.id(), updated);
    }

    @Override
    public DeleteAuthorResponse deleteAuthor(DeleteAuthorRequest request) {
        boolean deleted = repository.delete(request.id());
        return new DeleteAuthorResponse(request.id(), deleted);
    }
}
