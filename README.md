# 🏔️ Adventure Planner for Hikers & Climbers

[![Java](https://img.shields.io/badge/Java-21-orange?logo=java&logoColor=white)](#)  
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-brightgreen?logo=springboot)](#)  
[![ React](https://img.shields.io/badge/React%20+%20TypeScript-Frontend-blue?logo=react)](#)  
[![Docker](https://img.shields.io/badge/Docker-Enabled-blue?logo=docker)](#)  
[![MySQL](https://img.shields.io/badge/MySQL-Database-4479A1?logo=mysql&logoColor=white)](#)

A **full-stack adventure planning web app** for hikers, climbers, and outdoor enthusiasts. Plan hikes, manage gear checklists, log training sessions, and keep track of upcoming adventures — all in one place.

---

## 📖 Table of Contents
- [Overview](#-overview)
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Installation & Setup](#-installation--setup)
- [Configuration](#-configuration)
- [API Endpoints](#-api-endpoints)

---

## 🌟 Overview
The **Adventure Planner** combines:
- Adventure & hike planning
- Gear checklist management
- Training session logging
- Secure user accounts

It’s built with **Java Spring Boot**, **Hibernate**, **MySQL**, and **React + TypeScript**, running in **Docker** for easy setup.

---

## ✨ Features
- 👤 **User Accounts** – Register and log in to save adventures and gear lists.
- 🗺️ **Adventure Management** – Choose a pre-made hike/trail or create your own.
- 🎒 **Gear Checklist** – Track and customize gear needed for each adventure.
- 🏋️ **Training Log** – Record gym sessions, Krav Maga, and other training activities.
- 🗄️ **MySQL Database** – Store user data securely.
- 🐳 **Docker Setup** – Run the entire stack with one command.

---

## 🛠 Tech Stack

| Layer        | Technology |
|--------------|------------|
| **Backend**  | Java 17, Spring Boot 3, Hibernate (JPA) |
| **Frontend** | React + TypeScript |
| **Database** | MySQL |
| **API**      | REST API (JSON) |
| **Tools**    | Maven, Git, Docker |

---

## 🏗 Architecture

<pre>Controller → Service → Repository → Database</pre>

### Packages:
- controller – Handles HTTP requests/responses
- service – Business logic
- repository – Data access layer using Spring Data JPA
- model – JPA entities
- config – Application configuration

### Entities:
- User
- AdventureTemplate
- UserAdventure
- GearItem
- TrainingLog
- TravelPlan

---

## ⚙ Installation & Setup
### 1️⃣ Clone the Repository

<pre>git clone https://github.com/yourusername/adventure-planner.git
cd adventure-planner</pre>

### 2️⃣ Docker Setup
Make sure Docker is installed. Build and run the app with:
<pre>docker-compose up --build</pre>
This will start both the backend and frontend, including the MySQL database.

### 3️⃣ Access the App
- Backend REST API: http://localhost:8080
- Frontend: http://localhost:3000 (React app)

---

## ⚙ Configuration without Docker

### 1️⃣ Set up MySQL and craete DB/user
Install Mysql and create database

<pre>CREATE DATABASE adventure CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE USER 'adventure_user'@'%' IDENTIFIED BY 'change_me';
GRANT ALL PRIVILEGES ON adventure.* TO 'adventure_user'@'%';
FLUSH PRIVILEGES;</pre>

### ️2️⃣Configure backend
Create/edit backend/src/main/resources/application.properties:

<pre>spring.application.name=app_name

spring.datasource.url=jdbc:mysql://localhost:3306/adventure?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=change_me
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

spring.session.store-type=jdbc
spring.session.jdbc.initialize-schema=always


app.security.enabled=true</pre>

### 3️⃣ Run your app

Backend

<pre>cd backend
./mvnw spring-boot:run   # on Windows: mvnw.cmd spring-boot:run</pre>

Frontend    

<pre>cd frontend
npm install
npm run <dev></dev></pre>

Now open the app: http://localhost:5173/
Backend API: http://localhost:8080

---

## 📡 API Endpoints

| Method | Endpoint                                | Description                                | Auth Required |
|--------|-----------------------------------------|--------------------------------------------|---------------|
| **Auth** |
| GET    | `/auth/me`                              | Get current authenticated user             | Yes           |
| **Users** |
| GET    | `/users`                                | Get all users                              | Yes (admin)   |
| POST   | `/users`                                | Create a new user                          | Yes (admin)   |
| GET    | `/users/{id}`                           | Get user by ID                             | Yes           |
| PUT    | `/users/{id}`                           | Update user by ID                          | Yes           |
| DELETE | `/users/{id}`                           | Delete user by ID                          | Yes           |
| **Adventure Templates** |
| GET    | `/adventure-template`                   | List all adventure templates               | No            |
| POST   | `/adventure-template`                   | Create a new template                      | Yes (admin)   |
| GET    | `/adventure-template/{id}`              | Get template by ID                         | No            |
| PUT    | `/adventure-template/{id}`              | Update template by ID                      | Yes (admin)   |
| DELETE | `/adventure-template/{id}`              | Delete template by ID                      | Yes (admin)   |
| **User Adventures** |
| GET    | `/user-adventure`                       | Get all user adventures                    | Yes           |
| POST   | `/user-adventure`                       | Create a new user adventure                | Yes           |
| GET    | `/user-adventure/{id}`                  | Get user adventure by ID                   | Yes           |
| PUT    | `/user-adventure/{id}`                  | Update user adventure                      | Yes           |
| DELETE | `/user-adventure/{id}`                  | Delete user adventure                      | Yes           |
| GET    | `/user-adventure/user/{userId}`         | Get all adventures for a specific user     | Yes           |
| **Gear Items** |
| GET    | `/gear-items`                           | List all gear items                        | Yes           |
| GET    | `/gear-items/{id}`                      | Get gear item by ID                        | Yes           |
| POST   | `/gear-items/adventure/{id}`            | Add gear item to a specific adventure      | Yes           |
| PUT    | `/gear-items/{id}`                      | Update gear item                           | Yes           |
| DELETE | `/gear-items/{id}`                      | Delete gear item                           | Yes           |
| GET    | `/gear-items/adventure/{adventureId}`   | Get all gear items for a specific adventure| Yes           |
| **Travel Plans** |
| GET    | `/travel-plan`                          | Get all travel plans                       | Yes           |
| GET    | `/travel-plan/{id}`                     | Get travel plan by ID                      | Yes           |
| POST   | `/travel-plan/adventure/{adventureId}`  | Add travel plan for a specific adventure   | Yes           |
| PUT    | `/travel-plan/{id}`                     | Update travel plan                         | Yes           |
| DELETE | `/travel-plan/{id}`                     | Delete travel plan                         | Yes           |
| GET    | `/travel-plan/adventure/{userAdventureId}` | Get all travel plans for a user adventure | Yes           |