# 🏔️ Adventure Planner for Hikers & Climbers

[![Java](https://img.shields.io/badge/Java-17-orange?logo=java&logoColor=white)](#)  
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-brightgreen?logo=springboot)](#)  
[![ React](https://img.shields.io/badge/React%20+%20TypeScript-Frontend-blue?logo=react)](#)  
[![Docker](https://img.shields.io/badge/Docker-Enabled-blue?logo=docker)](#)  
[![MySQL](https://img.shields.io/badge/MySQL-Database-4479A1?logo=mysql&logoColor=white)](#)  
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](#)

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
- [License](#-license)

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