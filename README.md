BOARD MANAGER
======
This project implements the CRM program model.
You can test all the methods at the following link after the startup app:
[localhost:8080](http://localhost:8080/swagger-ui.html#/)

USED TECHNOLOGIES:
======
1. Java 11
2. Maven
3. Hibernate, MySQL
4. Spring Boot
5. Swagger

TO START APP:
====== 
1. Install MySQL and MySQL Workbench.
   You can use your DB with changed settings in the properties.
2. Setup connection properties in **application.properties** file
* user: "your username"
* password: "your password"
* db.url=jdbc:mysql:jdbc:mysql://localhost:3306/boardDB?createDatabaseIfNotExist=true
3. Run application
4. In the login window enter:
* login: "user"
* password: is generated on the startup, copy it from console
5. Follow the link to operate with methods,
   or you can install PostMan and use it.
  
AUTHOR:
======
[Ilya Brinchuk](https://github.com/Ilya-brinchuk)
