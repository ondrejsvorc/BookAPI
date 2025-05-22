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

public interface IAuthorService {
    GetAuthorResponse getAuthor(GetAuthorRequest request);
    GetAuthorsResponse getAuthors(GetAuthorsRequest request);
    CreateAuthorResponse createAuthor(CreateAuthorRequest request);
    UpdateAuthorResponse updateAuthor(UpdateAuthorRequest request);
    DeleteAuthorResponse deleteAuthor(DeleteAuthorRequest request);
}
