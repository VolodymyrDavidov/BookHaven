
![White Minimalist Bookshop Day Book Instagram Story-2](https://github.com/VolodymyrDavidov/BookHaven/assets/115085088/e0c96d34-fd50-496f-a5b3-1dc391d0fc04)

### Examples of endpoints

#### User Management

- User registration with optional shipping address.
- Secure user login with JWT-based authentication.

#### Available endpoints for User Management

*(for non-authenticated users)*

- POST: /api/auth/register

  Example of request body to register:

  ```json
  {
    "email": "example@example.com",
    "password": "Password123",
    "repeatPassword": "Password123",
    "firstName": "Bob",
    "lastName": "Dou",
    "shippingAddress": "1 Main St, City, Country"
  }
  ```

- POST: /api/auth/login

  Example of request body to log-in:

  ```json
  {
    "email": "example@example.com",
    "password": "Password123"
  }
  ```

#### Available endpoints for Book Management

*(with USER role)*

- GET: /api/books
- GET: /api/books/{id}
- GET: /api/books/search

*(with ADMIN role)*

- POST: /api/books/
- DELETE: /api/books/{id}
- PUT: /api/books/{id}

  Example of request body to create a new book:

  ```json
  {
    "title": "Book title",
    "author": "Book author",
    "price": "9.99",
    "description": "Description for book",
    "coverImage": "Book image",
    "isbn": "978-0-06-112118-4",
    "categoryIds": [1, 2]
  }
  ```

  To update a book, use the same request body as for creating a new book.

#### Available endpoints for Category Management

*(with USER role)*

- GET: /api/categories
- GET: /api/categories/{id}
- GET: /api/categories/{id}/books

*(with ADMIN role)*

- POST: /api/categories
- PUT: /api/categories/{id}
- DELETE: /api/categories/{id}

  Example of request body to create a new category:

  ```json
  {
    "name": "Category name",
    "description": "Category description"
  }
  ```

#### Available endpoints for Shopping Cart Management

*(with USER role)*

- POST: /api/cart
- GET: /api/cart
- PUT: /api/cart/books/{id}
- DELETE: /api/cart/cart-items/{cartItemId}

  Example of request body to add items to the cart:

  ```json
  {
    "bookId": 1,
    "quantity": 1
  }
  ```

  Example of request body to update book quantity in the cart:

  ```json
  {
    "quantity": 1
  }
  ```

#### Available endpoints for Order Management

*(with USER role)*

- POST: /api/orders
- GET: /api/orders
- GET: /api/orders/{orderId}/items
- GET: /api/orders/{orderId}/items/{itemId}

*(with ADMIN role)*

- PUT: /api/orders/{id}

  Example of request body to update order status:

  ```json
  {
    "status": "CANCELED"
  }
  ```

### Project Structure

- **model**: Entity models representing the database schema.
- **repository**: Spring Data JPA repositories for database operations.
- **service**: Business logic implementation.
- **controller**: Contains controllers for handling HTTP requests.
- **dto**: Data Transfer Objects for communication between the client and server.
- **mapper**: Mapper interfaces for mapping between DTOs and entity models.

### Postman

For detailed API usage, you can use provided request samples.
