Mini Kindle-like Application

Overview
--------------------------------------------------------------------------------------------
This repository contains a mini Kindle-like application built with Java and Spring Boot.
The application provides functionalities for user management, book management, and reading progress tracking.
It uses RESTful APIs for communication, JWT for authentication, and MySQL for data persistence.
Maven is used for dependency management.


Features
----------------------------------------------------------------------------------------------
- User authentication and registration
- Book management (add, update, delete, retrieve)
- Reading progress tracking (save, resume, jump to page)
- JWT-based authentication

Technologies Used
-------------------------------------------------------------------------------
- Java: Programming language
- Spring Boot: Framework for building the application
- Spring MVC: For creating RESTful web services
- JWT (JSON Web Tokens): For authentication
- MySQL: Database for storing user and book data
- Maven: Dependency management

Prerequisites
------------------------------------------------------------------------------------------------
1. Java JDK 17 used
2. MySQL: Install MySQL and set up a database. You can download it from the [MySQL website](https://dev.mysql.com/downloads/mysql/).

3. Maven: Install Maven for managing dependencies. You can download it from the [Maven website](https://maven.apache.org/download.cgi).

Setting Up the Application
--------------------------------------------------------------------------------------------
### 1. Clone the Repository

```bash
git clone https://github.com/Priyankaneelam55/Kindle_application-


 Configure MySQL
 ----------------------------------------------------------------------------------
1.Create a Database: Log in to your MySQL server and create a new database.
 CREATE DATABASE kindle_app;

2.Update Application Properties: Open src/main/resources/application.properties and configure your MySQL database settings.
spring.application.name=kindle
spring.datasource.username=${MYSQL_USER:root}
spring.datasource.password=${MYSQL_PASSWORD:Dhruv1430@}
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:${MYSQL_PORT:3306}/kindle_db
spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=true

jwt.algorithm.key=asddghjkl-ahbbnjegbbj-ajnjbhbgbw-jawdjwd
jwt.issuer=kindle_application
jwt.expiration=86400000

server.port=8080

book.base.path=D:\\kindle_app\\kindle\\Books

3. Install Dependencies

mvn clean install
mvn spring-boot:run

4 API Endpoints
For detailed information on the API endpoints, including sample requests and responses, please refer to the API Documentation file in this repository.


API DOCUMENTATION: https://documenter.getpostman.com/view/33617257/2sA3kYhzPb

Note: "I've never worked with Dockerization before. I attempted to create an image by following YouTube tutorials, but I was unsuccessful."
