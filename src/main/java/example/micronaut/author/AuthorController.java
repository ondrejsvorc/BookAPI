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
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import jakarta.inject.Inject;
import java.util.UUID;

@Controller("/authors")
public class AuthorController {
    private final IAuthorService authorService;

    @Inject
    public AuthorController(IAuthorService authorService) {
        this.authorService = authorService;
    }

    @Post
    public HttpResponse<CreateAuthorResponse> create(@Body CreateAuthorRequest request) {
        CreateAuthorResponse response = authorService.createAuthor(request);
        return response.isCreated()
                ? HttpResponse.created(response)
                : HttpResponse.status(HttpStatus.BAD_REQUEST).body(response);
    }    @Get("/{id}")
    public HttpResponse<GetAuthorResponse> getById(@PathVariable UUID id) {
        GetAuthorResponse response = authorService.getAuthor(new GetAuthorRequest(id));
        return response != null
                ? HttpResponse.ok(response)
                : HttpResponse.notFound();
    }

    @Get
    public HttpResponse<GetAuthorsResponse> getAll() {
        GetAuthorsResponse response = authorService.getAuthors(new GetAuthorsRequest());
        return HttpResponse.ok(response);
    }

    @Put
    public HttpResponse<UpdateAuthorResponse> update(@Body UpdateAuthorRequest request) {
        UpdateAuthorResponse response = authorService.updateAuthor(request);
        return response.isUpdated()
                ? HttpResponse.ok(response)
                : HttpResponse.notFound();
    }

    @Delete("/{id}")
    public HttpResponse<DeleteAuthorResponse> delete(@PathVariable UUID id) {
        DeleteAuthorResponse response = authorService.deleteAuthor(new DeleteAuthorRequest(id));
        return response.isDeleted()
                ? HttpResponse.ok(response)
                : HttpResponse.notFound();
    }
}
