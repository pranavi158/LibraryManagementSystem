# Library Management System (Java Swing + MySQL)

This is a robust **Library Management System** built using **Java Swing** for the user interface and **MySQL** for backend data management. The system supports **role-based access control**—Admins and Members—enabling secure and efficient handling of library operations such as book management, borrowing activities, and fine processing.

---

## Features

### Role-Based Authentication

- **Admin**:
  - Add new books to the library.
  - View and manage borrowed book records.
  - Automatically calculate overdue fines (₹5 per day).
  - View a summary of all registered members.

- **Member**:
  - Borrow and return books.
  - View personal borrowing history.
  - Check and pay fines.
  - Search books by ID and browse available inventory.

---

## Tech Stack

| Layer        | Technology               |
|--------------|--------------------------|
| User Interface | Java Swing             |
| Backend Logic  | JDBC (Java Database Connectivity) |
| Database       | MySQL                  |
| Design Pattern | DAO (Data Access Object) |

---
## Project Structure

```

LibraryManagementSystem/
│
├── ui/                 # Swing UI classes
│   ├── LoginUI.java
│   ├── AdminUI.java
│   └── MemberUI.java
│
├── dao/                # Data Access Object classes
│   ├── BookDAO.java
│   ├── BorrowRecordDAO.java
│   ├── FineDAO.java
│   ├── MemberDAO.java
│   └── UserDAO.java
│
├── model/              # Java model classes
│   ├── Book.java
│   ├── BorrowRecord.java
│   ├── Fine.java
│   ├── Member.java
│   └── User.java
│
├── connect/
│   └── DBConnection.java
│
└── README.md

````

---

## How to Run the Project

### Step 1: Clone the Repository

```bash
git clone https://github.com/yourusername/LibraryManagementSystem.git
cd LibraryManagementSystem
````

### Step 2: Set Up the MySQL Database

* Create a new MySQL database named `library_db`.
* Run the `database.sql` file to generate the required tables and sample data.
* Open `DBConnection.java` and configure your database credentials (URL, username, password).

### Step 3: Compile and Launch the Application

* Open the project using an IDE like IntelliJ IDEA or Visual Studio Code.
* Ensure your classpath includes MySQL JDBC driver.
* Run the `LoginUI.java` file to launch the system.

---

## Sample Credentials

Use the following login details to test the application:

| Username | Password | Role   |
| -------- | -------- | ------ |
| admin1   | admin123 | ADMIN  |
| member   | mb001    | MEMBER |

---
