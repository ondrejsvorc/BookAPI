package example.micronaut.book;

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

@Controller("/books")
public class BookController {
    private final IBookService service;

    @Inject
    public BookController(IBookService service) {
        this.service = service;
    }

    @Post
    public HttpResponse<CreateBookResponse> create(@Body CreateBookRequest request) {
        CreateBookResponse response = service.createBook(request);
        return response.isCreated() ? HttpResponse.created(response) : HttpResponse.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @Get("/{id}")
    public HttpResponse<GetBookResponse> getById(@PathVariable UUID id) {
        GetBookResponse response = service.getBook(new GetBookRequest(id));
        return response != null ? HttpResponse.ok(response) : HttpResponse.notFound();
    }

    @Get
    public HttpResponse<GetBooksResponse> getAll() {
        GetBooksResponse response = service.getBooks(new GetBooksRequest());
        return HttpResponse.ok(response);
    }

    @Put
    public HttpResponse<UpdateBookResponse> update(@Body UpdateBookRequest request) {
        UpdateBookResponse response = service.updateBook(request);
        return response.isUpdated() ? HttpResponse.ok(response) : HttpResponse.notFound();
    }

    @Delete("/{id}")
    public HttpResponse<DeleteBookResponse> delete(@PathVariable UUID id) {
        DeleteBookResponse response = service.deleteBook(new DeleteBookRequest(id));
        return response.isDeleted() ? HttpResponse.ok(response) : HttpResponse.notFound();
    }
}
