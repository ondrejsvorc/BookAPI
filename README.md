## BookAPI
Simplified RESTful API for managing books and authors.

### Possible improvements
- Connect to relational DB instead of in-memory one
- Improve update method by updating the existing record
- Add RepositoryFactory and inject only its interface instead of injecting repositories one by one
- Utilize Optional<T> more
- Utilize .map and .flatMap as it works well with Optional<T>
- Add M:N relation between books and authors
- Add logging
- Add pagination for books and authors
- Add custom exceptions (and global exception handler) or introduce Result pattern
- Standardize error messages
- Increase code coverage of core parts by adding more unit tests and by introducing integration tests
- Add authentication and authorization
- Use Micronaut's built-in validation annotations
- Setup CI/CD pipeline to run tests after each commit/merge

### Setup

Clone the repository.
```bash
git clone https://github.com/ondrejsvorc/BookAPI.git
cd BookAPI
```
Run the application.
```bash
./mvnw mn:run
```
Run tests.
```bash
./mvnw test
```

### Technology
- Java 21, Micronaut, Maven, JUnit

### Features
- CRUD operations for Authors
- CRUD operations for Books

### API Endpoints

#### Books
| Method | Path            | Description          |
|--------|-----------------|----------------------|
| GET    | /books          | Get books            |
| GET    | /books/{id}     | Get book with author |
| POST   | /books          | Create book          |
| PUT    | /books          | Update book          |
| DELETE | /books/{id}     | Delete book          |

#### Authors
| Method | Path            | Description     |
|--------|-----------------|-----------------|
| GET    | /authors        | Get authors     |
| GET    | /authors/{id}   | Get author      |
| POST   | /authors        | Create author   |
| PUT    | /authors        | Update author   |
| DELETE | /authors/{id}   | Delete author   |