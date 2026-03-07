# Ocean View Resort - Reservation System

By: Rushdi Ramzaan

## Project Overview

A comprehensive web-based room reservation system for Ocean View Resort built with Java EE technologies. The system implements a three-tier architecture following SOLID principles and design patterns, providing seamless reservation management for guests, staff, and administrators.

## Technologies Used

- **Backend**: Java EE, Servlets, JSP
- **Database**: MySQL with JDBC
- **Server**: Apache Tomcat 9.0
- **Build Tool**: Maven
- **Testing**: JUnit 5
- **Frontend**: HTML5, CSS3, JavaScript, TailwindCSS
- **Architecture**: Three-tier (Presentation → Controller → Service → Repository → Database)

## System Features

### User Roles & Authentication
- **Guest Authentication**: Verify identity using reservation number and contact
- **Staff Authentication**: Secure login for reservation management
- **Admin Authentication**: Administrative access for system management

### Reservation Management
- **Create Reservations**: New booking creation with room availability checking
- **View Reservations**: Search by contact number or reservation ID
- **Update Reservations**: Modify dates, room assignments, and booking details
- **Cancel Reservations**: Handle booking cancellations with availability updates

### Room Management
- **Availability Checking**: Real-time room availability based on dates and room types
- **Room Status Management**: Track room statuses (Available, Occupied, Maintenance)
- **Room Type Management**: Manage different room categories (Standard, Deluxe, Suite)

### Billing System
- **Bill Generation**: Automatic calculation based on stay duration and room rates
- **Payment Processing**: Handle guest payments and billing records

### Help System
- **Staff Help Articles**: Comprehensive guides for staff operations
- **Contextual Help**: Role-specific assistance for system features


## Database Setup

1. Install **MySQL 8+** and make sure it is running.

2. Import the database file located in the project:

database/ocean_view_resort.sql

You can import it using **MySQL Workbench**:

- Open MySQL Workbench
- Go to **Server → Data Import**
- Select **Import from Self-Contained File**
- Choose `MySQL database structure and data.sql`
- Click **Start Import**

3. The script will automatically create the database and tables.

## Database Connection

The application connects using:

jdbc:mysql://127.0.0.1:3306/ocean_view_resort

If your MySQL username or password is different, update them in:
`
DBConnectionManager.java
`

## Running the Application

1. Import the project into **IntelliJ IDEA**.
2. Ensure **MySQL is running** and the database has been imported.
3. Run the project using **Apache Tomcat 9**.

## Testing

### Testing Strategy
The project employs comprehensive testing at multiple levels:

#### Unit Tests (DAO Layer)
- **BillDAOTest**: Test billing data operations
- **GuestDAOTest**: Test guest data management
- **ReservationDAOTest**: Test reservation CRUD operations
- **RoomDAOTest**: Test room availability and management

#### Integration Tests (Service Layer)
- **AuthenticationServiceIntegrationTest**: Test authentication for all user types
- **AdminServiceIntegrationTest**: Test administrative functions
- **BillServiceIntegrationTest**: Test billing service integration
- **ReservationServiceIntegrationTest**: Test reservation workflow integration

### Running Tests

#### Using Maven
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=AuthenticationServiceIntegrationTest