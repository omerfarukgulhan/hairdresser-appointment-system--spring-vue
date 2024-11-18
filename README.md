# hairdresser-appointment-system--spring-vue

## Table of Contents

1. [Introduction](#introduction)
2. [Features](#features)
3. [Tech Stack](#tech-stack)
4. [Installation and Usage](#installation-and-usage)
5. [API Endpoints](#api-endpoints)

## Introduction

The Hairdresser Appointment System is a full-stack application developed using Spring for the backend and Vue.js for the frontend. It provides functionality for booking and managing hairdressing appointments, managing user roles, and maintaining hairdresser profiles. The system supports role-based access control for administrators, hairdressers, and users, ensuring that each role has access to the appropriate features.

## Features

- **Role-Based Access Control**

  - Admin, Hairdresser, and User roles, each with specific permissions.

- **Appointment Management**

  - Users can book, update, and cancel appointments.
  - Hairdressers can mark appointments as complete.

- **Hairdresser Profiles**

  - Hairdressers can create and update profiles, including adding images.
  - Public access to view hairdresser profiles and availability.

- **Treatment Management**

  - Hairdressers can add, update, and remove treatments.
  - Treatments are viewable by all users.

- **Reviews**

  - Users can add, edit, and delete reviews for hairdressers.
  - Reviews are publicly viewable.

- **User Management**

  - Admins can manage users, roles, and permissions.
  - Users can update their profiles, passwords, and activate/reset accounts via tokens.

- **Admin Tools**
  - Manage hairdresser approvals, inactive hairdressers, and system roles.

## Tech Stack

### Backend

The backend is built with **Spring Boot**, using the following key dependencies:

- **Spring Boot Starter Data JPA**: For database operations.
- **Spring Boot Starter Security**: For authentication and authorization.
- **Spring Boot Starter Web**: For building REST APIs.
- **PostgreSQL**: As the database.
- **Lombok**: To reduce boilerplate code.
- **Twilio SDK**: For SMS notifications.
- **JSON Web Tokens (JJWT)**: For JWT-based authentication.

### Frontend

The frontend is developed with **Vue.js**, including:

- **Vue.js**: For building the user interface.
- **Vue Router**: For client-side routing.
- **Vuex**: For state management.
- **Axios**: For making HTTP requests.
- **Bootstrap**: For styling and responsive design.

This streamlined stack highlights the core technologies that power the application.

## Installation and Usage

### Prerequisites

Before setting up the project, ensure you have the following installed:

- **Java 17 or higher**
- **Maven**
- **PostgreSQL**
- **Node.js** and **npm**

### Backend Setup

1. Clone the repository and navigate to the backend directory:

   ```bash
   git clone https://github.com/omerfarukgulhan/hairdresser-appointment-system--spring-vue.git
   cd hairdresser-appointment-system--spring-vue
   cd server
   ```

2. Set up the backend environment variables

   ```bash
   DB_URL=<your-database-url>
   DB_USERNAME=<your-database-username>
   DB_PASSWORD=<your-database-password>
   SECURITY_USER_PASSWORD=<default-admin-password>
   EMAIL_USERNAME=<your-email-username>
   EMAIL_PASSWORD=<your-email-password>
   JWT_SECRET_KEY=<your-jwt-secret-key>
   TWILIO_ACCOUNT_SID=<your-twilio-account-sid>
   TWILIO_AUTH_TOKEN=<your-twilio-auth-token>
   TWILIO_PHONE_NUMBER=<your-twilio-phone-number>
   CLIENT_HOST=<frontend-client-url>
   ```

3. Build and run the backend:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

### Frontend Setup

1. Navigate to the frontend directory:

   ```bash
   cd client
   ```

2. Set up the fronetnd in .env file environment variables

   ```bash
   VITE_SERVER_URL=http://localhost:8080
   VITE_SERVER_ASSETS_URL=http://localhost:8080/assets
   ```

3. Build and run the frontend:

   ```bash
   npm install
   npm run dev
   ```

App will start on http://localhost:5173 by default.

# API Endpoints

## Admin Endpoints
- **Approve Hairdresser**  
  `PUT /admin/approve/hairdressers/{hairdresserId}`  
  Requires `ROLE_ADMIN` authority.  

- **Manage Inactive Hairdressers**  
  `PUT /admin/hairdressers/inactives`  
  Requires `ROLE_ADMIN` authority.  

- **Manage Roles**  
  - `GET /roles`  
  - `GET /roles/{roleId}`  
  - `POST /roles`  
  - `PUT /roles/{roleId}`  
  - `DELETE /roles/{roleId}`  
  Requires `ROLE_ADMIN` authority.  

- **Manage Users**  
  - `GET /users`  
  - `GET /users/{userId}`  
  - `POST /users`  
  Requires `ROLE_ADMIN` authority.  

---

## Appointment Endpoints
- **View Appointments**  
  `GET /appointments`  
  Requires `ROLE_USER` authority.  

- **Create Appointment**  
  `POST /appointments`  
  Requires `ROLE_USER` authority.  

- **Update Appointment**  
  `PUT /appointments/{appointmentId}`  
  Requires `ROLE_USER` authority.  

- **Complete Appointment**  
  `PUT /appointments/complete/{appointmentId}`  
  Requires `ROLE_HAIRDRESSER` authority.  

- **Delete Appointment**  
  `DELETE /appointments/{appointmentId}`  
  Requires `ROLE_USER` authority.  

---

## Hairdresser Endpoints
- **View All Hairdressers**  
  `GET /hairdressers`  

- **View Specific Hairdresser**  
  `GET /hairdressers/{hairdresserId}`  

- **Create Hairdresser Profile**  
  `POST /hairdressers`  
  Requires `ROLE_USER` authority.  

- **Update Hairdresser Profile**  
  - `PUT /hairdressers/{hairdresserId}`  
  - `PUT /hairdressers/{hairdresserId}/update-main-image`  
  - `PUT /hairdressers/{hairdresserId}/update-side-images`  
  Requires `ROLE_HAIRDRESSER` authority.  

- **Delete Hairdresser Profile**  
  `DELETE /hairdressers/{hairdresserId}`  
  Requires `ROLE_HAIRDRESSER` authority.  

---

## Availability Endpoints
- **View Hairdresser Availability**  
  `GET /availabilities/{hairdresserId}`  

---

## Review Endpoints
- **View Reviews for a Hairdresser**  
  `GET /reviews/{hairdresserId}`  

- **Add Review**  
  `POST /reviews`  
  Requires `ROLE_USER` authority.  

- **Update Review**  
  `PUT /reviews/{reviewId}`  
  Requires `ROLE_USER` authority.  

- **Delete Review**  
  `DELETE /reviews/{reviewId}`  
  Requires `ROLE_USER` authority.  

---

## Treatment Endpoints
- **View Treatments**  
  - `GET /treatments/hairdresser/{hairdresserId}`  
  - `GET /treatments/{treatmentId}`  

- **Manage Treatments**  
  - `POST /treatments`  
  - `PUT /treatments/{treatmentId}`  
  - `DELETE /treatments/{treatmentId}`  
  Requires `ROLE_HAIRDRESSER` authority.  

---

## User Endpoints
- **Activate Account**  
  `PUT /users/activate/{token}`  

- **Update Password**  
  `PUT /users/update-password/{userId}`  
  Requires `ROLE_USER` authority.  

- **Reset Password**  
  - `PUT /users/reset-password`  
  - `PUT /users/reset-password/verify/{token}`  

- **Delete User**  
  `DELETE /users/{userId}`  
  Requires `ROLE_USER` authority.  
