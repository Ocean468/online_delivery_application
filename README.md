# online_delivery_application

Online Delivery Application
Project Overview
The Online Delivery Application is a Spring Boot-based web application that enables users to browse products, place orders, and track their delivery status. Customers can also view the real-time location of their products using Kafka integration.


**Technologies Used**

Backend:
•	Java (Spring Boot)
•	Spring Security (OAuth, JWT Authentication)
•	Hibernate (ORM for database interaction)
•	Kafka (Real-time product tracking)
•	MySQL (Database)


Frontend :
•	HTML, CSS, JavaScript


Features

•	User Authentication: Login and Registration (OAuth & JWT)

•	Product Management: Add, update, and view products

•	Order Management: Place and track orders

•	Real-time Tracking: Kafka-based product destination updates

•	Admin Panel: Manage users and orders

•	Security: Encrypted passwords, role-based access control





**Installation Guide**


Prerequisites
•	Java 17+
•	MySQL Database
•	Apache Kafka
•	Maven


Steps to Run Locally
1.	Clone the Repository 
2.	git clone https://github.com/Ocean468/online-delivery-app.git
3.	cd online-delivery-app
4.	Configure Database 
5.  Update application.properties with MySQL credentials
6.  Run database_dump.sql in MySQL
7.	Start Kafka (Optional for Tracking) 
8.	bin/zookeeper-server-start.sh config/zookeeper.properties
9.	bin/kafka-server-start.sh config/server.properties
10.	Run the Application 
11.	mvn spring-boot:run


    
API Endpoints:
Endpoint	Method	Description

/api/auth/login	POST	User Login

/api/auth/register	POST	User Registration

/api/products	GET	List All Products

/api/orders	POST	Place Order

/api/orders/{id}	GET	View Order Details

/api/orders/track/{id}	GET	Track Product Location (Kafka)



**Contributors**
•	Ocean Singh Bhati
•	GitHub Profile





**Visual demonstrations of the projects:**

![image](https://github.com/user-attachments/assets/282b7cb3-bc32-4652-a877-8c2ed5b8cf56)
![image](https://github.com/user-attachments/assets/0ccd1252-f54b-4e5e-920a-70116565df65)
![image](https://github.com/user-attachments/assets/0aa8acdb-fcef-4d24-b28b-4c43a245652c)
![image](https://github.com/user-attachments/assets/836fb221-d087-4431-814c-6cf1bb9b454f)
![image_2025-03-04_18-03-30](https://github.com/user-attachments/assets/7f907680-20b1-49c2-91b8-4d875299f1e3)





