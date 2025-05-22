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

public interface IBookService {
    GetBookResponse getBook(GetBookRequest request);
    GetBooksResponse getBooks(GetBooksRequest request);
    CreateBookResponse createBook(CreateBookRequest request);
    UpdateBookResponse updateBook(UpdateBookRequest request);
    DeleteBookResponse deleteBook(DeleteBookRequest request);
}
