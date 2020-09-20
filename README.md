# library  
  
  -- TO DO --  
  1. Zarzadzanie uzytkownikami  
  2. Aktywacja/deaktywacja uzytkownika (nie usuwac z db/ przechowywac dane historycznie)  
  3. Walidacja requestow - Spring Validation (pole email, haslo, name, login musi byc mailem)  
  4. Podstawowy RESTowy error handling na controller (unikalny email)  
  5. Wszystko ma byc zwracane w postacji JSON  
  6. Obiekty immutable (brak setterow, konstruktorow) - static factory method DTO ( 3 obiekty reprezentujace ten sam model, UserResponse - zwraca metoda serwisowa, UserEntity - service, CreateUserRequest - przyjmuje metoda serwisowa, EditUserRequest -  przyjmuje metoda serwisowa)  
  7. Odpowiedzi z API musza miec konstruktor domyslny prywatny, brak setterow  
    
    
  n. Potwierdzenie mailowe (Java Email Service)*  

CreateUserRequest 
{
"email":"blabla@mail.com",
"name":"M Szwed",
"password":"mojeHasło!@3"
}

ChangeNameRequest
{
"name":"nowy name"
}

ChangePasswordRequest
{
"oldPassword":"old",
"newPassword":"new"
}

UserResponse 
{
"id":"UUID",
"email":"email",
"name":"name",
"active":"true"
}
