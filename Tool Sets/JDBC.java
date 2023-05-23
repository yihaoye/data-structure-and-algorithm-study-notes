// Transaction example (by chatgpt):
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://hostname:port/database";
        String user = "user";
        String password = "password";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            conn.setAutoCommit(false);

            stmt.executeUpdate("BEGIN;");
            stmt.executeUpdate("DECLARE @id INT;");
            ResultSet rs = stmt.executeQuery("SELECT @id := id FROM goods WHERE status = 'available' LIMIT 1 FOR UPDATE;");
            int updatedID = -1;
            if (rs.next()) {
                updatedID = rs.getInt(1);
            }
            stmt.executeUpdate("UPDATE goods SET status = 'pending' WHERE id = @id;");
            rs = stmt.executeQuery("SELECT @id AS updated_id;");
            if (rs.next()) {
                updatedID = rs.getInt("updated_id");
            }
            stmt.executeUpdate("COMMIT;");

            System.out.println("Updated ID: " + updatedID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



// Same with above but without sql injection risk (by chatgpt)
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://hostname:port/database";
        String user = "user";
        String password = "password";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            conn.setAutoCommit(false);

            String query = "BEGIN; " +
                    "DECLARE @id INT; " +
                    "SELECT @id := id FROM goods WHERE status = ? LIMIT 1 FOR UPDATE; " +
                    "UPDATE goods SET status = ? WHERE id = @id; " +
                    "SELECT @id AS updated_id; " +
                    "COMMIT;";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, "available");
                stmt.setString(2, "pending");

                ResultSet rs = stmt.executeQuery();

                int updatedID = -1;
                if (rs.next()) {
                    updatedID = rs.getInt("updated_id");
                }

                conn.commit();

                System.out.println("Updated ID: " + updatedID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
