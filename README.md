# Car Rental Management System 🚗

A console-based Java application built to manage vehicle rentals, customer records, and billing calculations. This project demonstrates the implementation of **Core Java**, **JDBC**, and **MySQL** to handle real-world business logic.

## 🚀 Key Features
* **Car Inventory Management:** View real-time availability of vehicles.
* **Rental Processing:** Rent cars with automated status updates (Available → Rented).
* **Billing System:** Auto-calculate total cost based on rental duration and daily price.
* **Customer History:** Persist customer data and rental logs in MySQL database.
* **Return Logic:** Process returns and instantly update inventory availability.

## 🛠️ Tech Stack
* **Language:** Java (JDK 21)
* **Database:** MySQL
* **Connectivity:** JDBC (Java Database Connectivity)
* **IDE:** IntelliJ IDEA

## ⚙️ How to Run
1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/AnuragRai7/CarRentalSystem.git](https://github.com/YOUR_USERNAME/CarRentalSystem.git)
    ```
2.  **Setup Database:**
    * Import the SQL commands provided in the `database_setup.sql` (or create the `car_rental` DB manually).
3.  **Configure Connection:**
    * Open `DatabaseConnection.java` and update the `URL`, `USER`, and `PASSWORD` to match your local MySQL setup.
4.  **Run Application:**
    * Run `Main.java` to start the console interface.

## 📸 Project Flow
1.  Select "Rent a Car".
2.  Enter Customer Name & Days.
3.  System calculates Bill & Updates Database.
4.  View "Rental History" to see the record.
