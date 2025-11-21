import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // 1. The URL tells Java where MySQL is located (localhost) and which DB to use (car_rental)
    private static final String URL = "jdbc:mysql://localhost:3306/car_rental";

    // 2. Your MySQL username (usually 'root')
    private static final String USER = "root";

    // 3. YOUR PASSWORD HERE (Change "root123" to the password you set in Phase 1)
    private static final String PASSWORD = "Anuragrai";

    // This method is called whenever we need to talk to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}