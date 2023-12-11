# Online Store Information System
This project consists of an online video game store. As a website user, you can place orders, create user accounts, and log in. As an administrator, you can create products, update information, and view orders or statistics. The backend of this system is hosted in this repository, developed using Spring, and includes unit tests. For the database I use PostgreSql.

## System Requirements

For the project's backend, using the Spring framework requires **Java** and **Maven** for Spring development on the system.

## Steps to execute the project

First make sure that this repository has been cloned and saved in a folder which you can identify.
- 1.- Open a terminal/command prompt, navigate to the project directory.
- 2.- Configures the database so that data can be added, updated or queried to the database
	 > For more information, see the **application.properties** which is located inside the project.
- 3.- Execute the command `mvn clean install` to build the project and download dependencies.
- 4.- Run the application using the appropriate command, such as `mvn spring-boot:run`
- 5.- Access the application through the provided URL or port [http://localhost:8080](http://localhost:8080/).
> **Note:**  Ensure that **Java** and **Maven** are installed on the target machine.

## Once the project has been executed
Upon backend execution, the absence of a user interface is expected, as frontend functionality manages that aspect. Backend execution facilitates handling database operations, including addition, modification, or retrieval of data. Interaction can occur through the frontend application or tools like Postman by calling endpoints specified in the "controllers" folder within the project.

## Features

- This project will start with some test data to facilitate the use of the project. 
- It has tests that check different functionalities of the project.
- Implements Spring security.
- The database is perfectly normalized and with soft delete implemented.
