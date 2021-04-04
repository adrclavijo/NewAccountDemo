# AccountDemo

This is a simple Spring Boot project for a REST service, where you work with bank accounts.

### Features

 - Account seaching by ID and Name.
 - Account creation.
 - Account listing.
 - Transfer money between accounts.
 
### Notes 

For proper account localization, I decided to make some fields not-nullable, like "name".
That way I could implement a findByName service.

Also the query needed to return a List in order to avoid a duplicated entry exception. By now, we just return the first one.

Multiple currencies are admitted, but in order to transfer money both accounts have to share the same currency.

Rest routes are stored in a own class, so they are easier to read and also easier to build JUnit tests for controllers

Unit testing built with JUnit 4.