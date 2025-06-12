# Library Management System (Java Swing + MySQL)

A simple yet functional **Library Management System** built using **Java Swing** for GUI and **MySQL** as the backend database. This system supports **role-based access** (Admin & Member), allowing operations like book management, borrowing history, and fine handling.

---

## Features

### Role-Based Login
- **Admin**: Can add books, manage fines, and view member summaries.
- **Member**: Can borrow/return books, view borrowing history, and check/pay fines.

### Admin Functionalities
- Add new books with unique IDs.
- View borrowed books by member ID.
- Automatically calculate fines for late returns (₹5 per day).
- View all registered members and their details.

### Member Functionalities
- Borrow and return books.
- View current borrowing history.
- Check and pay fines.
- Search books by ID and browse all available books.

---

## Tech Stack

| Layer        | Technology               |
|--------------|--------------------------|
| UI           | Java Swing               |
| Backend      | JDBC (Java DB Connector) |
| Database     | MySQL                    |
| Architecture | DAO Pattern              |

---

##  Project Structure

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
├── model/              # Plain Old Java Objects
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



---

## How to Run

1. **Clone the repository**  
   bash
   git clone https://github.com/yourusername/LibraryManagementSystem.git
   cd LibraryManagementSystem

2. **Set up the database**

   * Create a MySQL database named `library_db`
   * Run the provided `database.sql` to create tables
   * Update `DBConnection.java` with your MySQL credentials

3. **Compile and Run**

   * Open the project in your IDE (e.g., IntelliJ IDEA, VS Code)
   * Run `LoginUI.java` as the entry point

---

## Sample Admin Credentials


| Username | Password | Role   |
| -------- | -------- | ------ |
| admin1   | admin123 | ADMIN  |
| member   | mb001    | MEMBER |

---

## 📸 Screenshots (Optional)

*Add a few screenshots here of login screen, admin dashboard, and member view for better visibility.*


