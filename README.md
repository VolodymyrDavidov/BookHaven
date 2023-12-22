Functionalities

User Management

User registration with optional shipping address.
Secure user login with JWT-based authentication.
Available endpoints for User Management

(for non-authenticated users)

POST: /api/auth/register
Example of request body to register:

{
  "email": "john.doe@example.com",
  "password": "securePassword123",
  "repeatPassword": "securePassword123",
  "firstName": "John",
  "lastName": "Doe",
  "shippingAddress": "123 Main St, City, Country"
}
POST: /api/auth/login
Example of request body to log-in:

{
  "email": "john.doe@example.com",
  "password": "securePassword123"
}
Book Management

Create, retrieve, update, and delete books.
Search for books based on various parameters.
Associate books with multiple categories.
Available endpoints for Book Management

with USER role

GET: /api/books

GET: /api/books/{id} 

GET: /api/books/search
with ADMIN role

POST: /api/books/

DELETE: /api/books/{id}

PUT: /api/books/{id}

Example of request body to create new book:

{
  "title": "Book title",
  "author": "Book author",
  "price": "200", 
  "description": "Description for book",
  "coverImage": "Book image",
  "isbn": "ISBN-123456",
  "categoryIds": [1, 2]
}
If you want to add category to book, you should crate category first, or update book later. Field categoryIds is an optional field.

To update Book you should use same request body as for creation of a new book.

Category Management

Create, retrieve, update, and delete book categories.
Available endpoints for Category Management

with USER role

GET: /api/categories

GET: /api/categories/{id}

GET: /api/categories/{id}/books
with ADMIN role

POST: /api/categories 

PUT: /api/categories/{id}

DELETE: /api/categories/{id}
Example of request body to create new category:

{
  "name": "Category name",
  "description": "Category description"
}
Shopping Cart and Order Management

Add books to the shopping cart.
View and manage shopping cart items.
Place orders, update order status, and retrieve order details.
Available endpoints for Shopping Cart Management

with USER role

POST: /api/cart

GET: /api/cart

PUT: /api/cart/books/{id}

DELETE: /api/cart/cart-items/{cartItemId}
Example of request body to add items in cart:

{
  "bookId": 1,
  "quantity": 1
}
Example of request body to update book qty in cart:

{
"quantity": 1
}
Available endpoints for Order Management

with USER role

POST: /api/orders

GET: /api/orders

GET: /api/orders/{orderId}/items

GET: /api/orders/{orderId}/items/{itemId}
Example of request body to post order:

{
"shippingAddress": "Kyiv, Shevchenko st 23"
}
with ADMIN role

PUT: /api/orders/{id}
Example of request body to update order status:

{
"status": "CANCELED"
}
Project Structure

The project follows a modular structure:

model: Entity models representing the database schema.
repository: Spring Data JPA repositories for database operations.
service: Business logic implementation.
controller: Contains controllers for handling HTTP requests.
dto: Data Transfer Objects for communication between the client and server.
mapper: Mapper interfaces for mapping between DTOs and entity models.
Setup

To set up and use the Online Book Store, follow these steps:

Clone the repository to your local machine.

Configure the database settings in the application properties.

Build and run the application using your preferred Java IDE or build tool.

Access the Swagger documentation: http://localhost:8080/swagger-ui.html The API uses JWT (JSON Web Tokens) for authentication.

To access protected endpoints first login to api, then include the generated JWT token in the Authorization header of your requests.

Challenges and Solutions

Challenge: Implementing secure user authentication.

Solution: Utilized Spring Security and JWT for a robust authentication mechanism.

Challenge: Efficiently managing shopping carts and order processing.

Solution: Designed a ShoppingCartService and OrderService to handle cart operations and order management.

Postman

For detailed API usage, you can use provided requests samples.

Conclusion

The online-book-store is designed to offer a seamless experience for managing bookstore operations.

Whether you're a developer looking to understand the codebase or a user interested in utilizing the features, this README provides a comprehensive guide to get started.

