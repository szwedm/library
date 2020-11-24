# Library REST API  
Library project implemented as Spring Boot REST API.  
## Technology stack
Programming language: Java 11  
Database: PostgreSQL  
Frameworks: Spring Boot, Hibernate  
Build tool: Maven  
Tests: Junit 5, Mockito  
## Security
HTTP basic authentication.  
Passwords are encrypted with bcrypt.  
Use following credentials to perform all supported requests:  
```
admin:super123
```
Use following credentials to perform reader requests:  
```
james.sunderland@email.com:pyramid
            OR
mary.shepherd@email.com:judgement
```
## Usage
Three accounts are created during project startup:
1. admin
2. james.sunderland@email.com
3. mary.shepherd@email.com

First account can perform all supported requests while the other two are limited to requests regarding own account.

Below is a summary of the available requests. Verbs before URLs indicate HTTP request method. Hostname & port were skipped to avoid repetitions.  
  
__GET: /users__  
Returns all users stored in the database as JSON array.  
  
__GET: /users/{id}__  
Returns one user as JSON object.  
  
__POST: /users__  
Creates and stores one user in the database.  
Request body: 
```
{
    "name":"username",
    "email":"user@email.com",
    "password":"userpassword"
}
```
  
__PUT: /users/{id}/change-password__  
Changes user's password.
  

__DELETE: /users/{id}__  
Deactivates the user.  
Request body: 
```
{
    "oldPassword":"oldPasswordHere",
    "newPassword":"newPasswordHere"
}
```
  
__POST: /books__  
Adds a book to the library.  
Request body: 
```
{
    "name":"Book's Title",
    "author":"Author"
}
```  
  
__DELETE: /books__  
Removes a book from the library.  
Request body: 
```
{
    "name":"Book's Title",
    "author":"Author"
}
```  
  
__POST: /library/borrow-book__  
Lends a book from the library for authenticated user.  
Request body: 
```
{
    "title":"Book's Title",
    "author":"Author"
}
```  
  
__POST: /library/return-book__  
Returns a book to the library from authenticated user.  
Request body: 
```
{
    "title":"Book's Title",
    "author":"Author"
}
``` 