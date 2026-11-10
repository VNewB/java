import java.sql.*;

public class DBConnection {
    // >>> SỬA lại cho đúng máy của bạn <<<
    private static final String URL =
        "jdbc:sqlserver://localhost:1433;databaseName=QLMuaBanXe;encrypt=false;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASS = "123456";

    public static String lastError = "";

    /** Trả về Connection, hoặc null nếu kết nối thất bại. */
    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            lastError = e.getMessage();
            return null;
        }
    }
}
