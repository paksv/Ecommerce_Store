**Ecommerce Store** is a online shopping project built with **Java Spring Boot MVC** and **Thymeleaf**. This web application provides a good user experience through a frontend developed with **HTML, CSS, JavaScript,** and **jQuery**, including powerful **jQuery DataTables** for enhanced data management.

The backend used **Spring Boot** for clean, structured code with a **service layer**, **Spring Data JPA** for efficient database operations using **MySQL**, and **Spring Security** to protect user data with **authentication** and **role-based authorization**. Optimized with **Lombok** to reduce boilerplate code, this project is packaged as a **WAR** file for easy deployment on any servlet container, like **Apache Tomcat**.

# Features
  - **Spring Boot MVC Architecture:** This project follows a well-organized architecture with Service, Repository, and Controller layers to ensure clean code, easy maintenance, and scalability.
  - **Frontend Technologies:**
      -  **Thymeleaf:** Server-side Java templating engine for rendering dynamic HTML pages.
      - **HTML5/CSS3:** Responsive design for modern browsers and mobile devices.
      - **JavaScript & jQuery:** Enhance user interactivity with dynamic elements.
      - **jQuery DataTables:** Efficient data handling and display with pagination, sorting, and search functionalities.
  
 - **Backend Technologies:**
      - **Java Spring Boot (3.3.2):** Core framework for the application backend, handling business logic and database interaction.
      - **Spring Data JPA:** Simplified data access and database queries with custom JPA finder methods.
      - **MySQL:** Relational database used for data persistence.
      - **Spring Security:** Secure the application with user authentication and role-based authorization.
      - **Lombok:** Simplifies code with annotations to eliminate boilerplate code (like **getters**, **setters**, **constructors**).
      - **Maven:** Build tool for project management and dependency management.
  
 - **Security Features:**
      - User authentication and authorization with Spring Security.
      - Protected routes and roles-based access control.
  
 - **Deployment:** Packaged as a WAR file, suitable for deployment on any servlet container like Apache Tomcat.

 - **Modern Development Practices:**
      - Spring Boot DevTools for faster development and hot reload.
      - Custom JPA Queries for efficient data retrieval and management.



# Requirements
  - Java 17 (project setup supports Java 17 but can be adjusted for Java 8 environments)
  - MySQL
  -Maven
  -Apache Tomcat (for WAR deployment)

# Installation
   **1. Clone the repository:**
```bash
git clone https://github.com/mdtalalwasim/Ecommerce_Store.git
```
   **2. Navigate to the project directory:**
```bash
cd Ecommerce_Store
```
  **3. Set up the MySQL database:**
```bash
CREATE DATABASE ecommerce_store;
```
  **4. Configure database connection settings in application.properties:**
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_store
spring.datasource.username=your-username
spring.datasource.password=your-password
```
**5. Run the application:** 
**Access the app at http://localhost:8080.**
