import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== CAR RENTAL SYSTEM ===");
            System.out.println("1. View Available Cars");
            System.out.println("2. Rent a Car");
            System.out.println("3. Return a Car");
            System.out.println("4. View Rental History");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayCars();
                    break;
                case 2:
                    rentCar(scanner);
                    break;
                case 3:
                    returnCar(scanner);
                    break;
                case 4:
                    viewRentalHistory();
                    break;
                case 5:
                    System.out.println("Thank you! Exiting system.");
                    return; // Stops the program
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // FEATURE 1: Display all cars from the database
    private static void displayCars() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM cars";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\n--- Car Inventory ---");
            System.out.println("ID\tBrand\tModel\t\tPrice/Day\tStatus");
            System.out.println("------------------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("car_id");
                String brand = rs.getString("brand");
                String model = rs.getString("model");
                double price = rs.getDouble("price_per_day");
                boolean available = rs.getBoolean("is_available");

                String status = available ? "Available" : "Rented";
                System.out.printf("%d\t%s\t%s\t\t$%.2f\t\t%s%n", id, brand, model, price, status);
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    // FEATURE 2: Rent a car (With Billing & Customer Name)
    private static void rentCar(Scanner scanner) {
        System.out.print("\nEnter the Car ID you want to rent: ");
        int carId = scanner.nextInt();

        System.out.print("Enter Customer Name: ");
        scanner.nextLine(); // Fix for scanner bug (consumes the leftover newline)
        String customerName = scanner.nextLine();

        System.out.print("Enter Number of Days for Rental: ");
        int days = scanner.nextInt();

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Step 1: Check availability and Get Price
            String checkSql = "SELECT is_available, brand, model, price_per_day FROM cars WHERE car_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, carId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                boolean isAvailable = rs.getBoolean("is_available");
                double pricePerDay = rs.getDouble("price_per_day");
                String carName = rs.getString("brand") + " " + rs.getString("model");

                if (isAvailable) {
                    // Step 2: Calculate Total Price
                    double totalPrice = pricePerDay * days;
                    System.out.printf("Confirmed! You are renting %s for %d days.%n", carName, days);
                    System.out.printf("TOTAL BILL: $%.2f%n", totalPrice);

                    // Step 3: Mark Car as RENTED
                    String updateCar = "UPDATE cars SET is_available = FALSE WHERE car_id = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateCar);
                    updateStmt.setInt(1, carId);
                    updateStmt.executeUpdate();

                    // Step 4: Save Rental Record (Who rented it?)
                    String saveRental = "INSERT INTO rentals (car_id, customer_name, days, total_fee, rental_date) VALUES (?, ?, ?, ?, CURDATE())";
                    PreparedStatement saveStmt = conn.prepareStatement(saveRental);
                    saveStmt.setInt(1, carId);
                    saveStmt.setString(2, customerName);
                    saveStmt.setInt(3, days);
                    saveStmt.setDouble(4, totalPrice);
                    saveStmt.executeUpdate();

                    System.out.println("SUCCESS! Rental record saved.");
                } else {
                    System.out.println("Sorry, that car is currently rented.");
                }
            } else {
                System.out.println("Invalid Car ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // FEATURE 3: Return a car
    private static void returnCar(Scanner scanner) {
        System.out.print("\nEnter the Car ID you want to return: ");
        int carId = scanner.nextInt();

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Update status to AVAILABLE (TRUE)
            String sql = "UPDATE cars SET is_available = TRUE WHERE car_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, carId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("SUCCESS! Car ID " + carId + " has been returned.");
            } else {
                System.out.println("Invalid Car ID. Please check and try again.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // FEATURE 4: View Rental History
    private static void viewRentalHistory() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM rentals";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\n--- Rental History ---");
            System.out.println("ID\tCustomer\tCar ID\tDays\tTotal Fee");
            System.out.println("------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%d\t%s\t%d\t%d\t$%.2f%n",
                        rs.getInt("id"),
                        rs.getString("customer_name"),
                        rs.getInt("car_id"),
                        rs.getInt("days"),
                        rs.getDouble("total_fee"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}