# Messenger Application

# Project Overview


# Description: 
This project is a Messenger Application developed using Spring Boot, Angular, and MySQL.

# Features:
Implemented Spring Security with JWT authentication mechanism for secure user authentication and authorization.
Designed and developed backend functionalities using Spring Boot, including RESTful APIs for handling user authentication, messaging, and data storage.
Created responsive user interfaces using Angular, providing an intuitive user experience across various devices.
Utilized MySQL for database management, including schema design, querying, and data manipulation.

# Why Local File Storage?
In this application, we have chosen to store files, such as media attachments, in a local folder (D:\local-storage) instead of within the database. This decision was made to improve performance and scalability. Storing large files directly in the database can lead to slow operations during storage and retrieval, as databases are optimized for structured data rather than binary files.

While paid cloud services are often used for file storage, we have opted for local storage for simplicity and cost-effectiveness. The D:\local-storage folder serves as a convenient solution for storing encrypted files securely on the local filesystem.



# Data Encryption
It's important to note that while files are stored locally, sensitive data such as messages and passwords are still stored in the database. However, to ensure security and privacy, this sensitive data is encrypted before being stored in the database.

# cGetting Started
Before running the Messenger Application, ensure you have the D:\local-storage folder created on your system for file storage.

To create the D:\local-storage folder:

Open File Explorer: Navigate to your file explorer on your Windows system.
Create Folder: Navigate to the D:\ directory and create a new folder named local-storage.
Encryption: Ensure that the folder is set up for encrypting data as per your application's requirements.
Once the D:\local-storage folder is created and set up, you can proceed to run the Messenger Application.


# And run spring boot application in 8080 port number as i hardcoded it but in real time practicer its not the scenario, but as of now keep it 8080.



# Below are my application properties , if you want change it accordingly

spring.datasource.url=jdbc:mysql://localhost:3306/messengerdb
spring.datasource.username=root
spring.datasource.password=virat1718
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update


spring.servlet.multipart.max-file-size=2MB
spring.servlet.multipart.max-request-size=2MB
