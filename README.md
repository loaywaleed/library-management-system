# Library Management System


### Description:
This is a simple library management system. It allows to manage books, authors and users (librarians). It provides a RESTful API to perform CRUD operations on these entities and all endpoints are secured with JWT token based authentication.

### Technologies:

- Java Version: 8 (OpenJDK)

- Database:`H2 In-Memory Database`

- Framework:`Spring Boot`

- ORM:`Spring Data JPA`

### Authentication:
`JWT` - JSON Web Token
- All endpoints are protected with JWT. except `"/user/signin"` for login and `"/user/signup"` for registration.
- For testing you can use the following credentials:
    - username: `admin`
    - password: `admin` 
    - example: POST http://localhost:8080/users/signin?username=admin&password=admin 
  to return a JWT for the sake of testing then use it as bearer token to access all the other endpoints.

### API Documentation:
`Swagger` - `http://localhost:8080/swagger-ui.html`

### Build:
```sh
mvn clean install
```

### Run:
```sh
mvn spring-boot:run
```