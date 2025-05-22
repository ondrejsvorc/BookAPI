## BookAPI
Simplified RESTful API for managing books and authors.

### Setup

Clone the repository.
```bash
git clone https://github.com/ondrejsvorc/BookAPI.git
cd BookAPI
```
Run the application.
```bash
./mvnw mn:run
````
Run tests.
````bash
./mvnw test
````

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


