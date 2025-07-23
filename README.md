# Notes-Manager

Notes-Manager is a simple Java Spring Boot application for making CRUD operations with notes, it implements JWT token for secure access to notes.

## Usage

Firstly, you have to create a PostgreSQL DB:

I included DB backup for PostgreSQL 17.

Secondly, configure the application.properties, assign datasource url/username/password and create your own JWT Secret

Secondly, you have to register to the system: 

```http request
POST http://localhost:8080/api/auth/register
Content-Type: application/json

{
    "email": "test@example.com",
    "name": "testuser",
    "password": "testpass"
}
```

This HTTP request returns:

```http request
User registered successfully!
```

This means that there is now a new record in the database.
After that you have to get JWT Token

```http request
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
    "name": "testuser",
    "password": "testpass"
}
```

This request returns some JWT token:

```http request
XXXXX.YYYYY.ZZZZZ
```

And finally you can use this token for CRUD operations with notes:


```http request
GET http://localhost:8080/api/notes
Authorization: Bearer YOUR_JWT_TOKEN
``` 

``` http request
GET http://localhost:8080/api/notes/{{id}}
Authorization: Bearer YOUR_JWT_TOKEN
```

```http request
POST http://localhost:8080/api/notes
Authorization: Bearer YOUR_JWT_TOKEN
Content-Type: application/json

{
  "noteHeader": "Test Note Header",
  "noteBody": "Test Note Body"
}
```

```http request
PUT http://localhost:8080/api/notes/{{id}}
Authorization: Bearer YOUR_JWT_TOKEN 
Content-Type: application/json"
{
 "title": "Updated Title", 
 "content": "Updated content"
}
```

This is POSTMAN collection for testing(in the Authorization bar change the JWT/Bearer Code)

[POSTMAN](https://kairatulyerasil-8092173.postman.co/workspace/My-Workspace~8bc6bedc-a719-4f34-a0fb-e303dfdce2f4/collection/46974788-2ed4c12c-e17c-4f7a-a744-bac2d6488fa8?action=share&source=copy-link&creator=46974788)
