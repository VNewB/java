package onlan2.src.main.java;

import java.io.File;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class initDatabase {

    private static final String DB_PATH = "QLHangHoa.accdb";

    public static void main(String[] args) {

        try {
            taoDatabaseNeuChuaCo();

            try (Connection conn = ketNoi()) {
                taoBangLoaiHang(conn);
                taoBangHangHoa(conn);
                taoKhoaNgoai(conn);
            }

            System.out.println("Kiem tra CSDL QLHangHoa thanh cong.");

        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // =========================================================
    // KẾT NỐI DATABASE
    // =========================================================

    private static Connection ketNoi() throws SQLException {

        String url = "jdbc:ucanaccess://" + DB_PATH;

        return DriverManager.getConnection(url);
    }

    // =========================================================
    // TẠO DATABASE NẾU CHƯA CÓ
    // =========================================================

    private static void taoDatabaseNeuChuaCo() throws Exception {

        File database = new File(DB_PATH);

        if (database.exists()) {
            System.out.println("CSDL da ton tai: " + DB_PATH);
            return;
        }

        System.out.println("CSDL chua ton tai.");
        System.out.println("Dang tao CSDL...");

        /*
         * UCanAccess không tạo file Access rỗng trực tiếp.
         * Vì vậy dùng Jackcess để tạo database.
         */

        com.healthmarketscience.jackcess.Database db =
                com.healthmarketscience.jackcess.DatabaseBuilder
                        .create(com.healthmarketscience.jackcess.Database.FileFormat.V2010, database);

        db.close();

        System.out.println("Da tao CSDL: " + DB_PATH);
    }

    // =========================================================
    // TẠO BẢNG LOẠI HÀNG
    // =========================================================

    private static void taoBangLoaiHang(Connection conn)
            throws SQLException {

        if (bangTonTai(conn, "LoaiHang")) {
            System.out.println("Bang LoaiHang da ton tai.");
            return;
        }

        String sql = """
                CREATE TABLE LoaiHang (
                    MaLoai INTEGER NOT NULL,
                    TLH VARCHAR(50) NOT NULL,
                    TyLe INTEGER NOT NULL,
                    CONSTRAINT PK_LoaiHang PRIMARY KEY (MaLoai)
                )
                """;

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }

        System.out.println("Da tao bang LoaiHang.");
    }

    // =========================================================
    // TẠO BẢNG HÀNG HÓA
    // =========================================================

    private static void taoBangHangHoa(Connection conn)
            throws SQLException {

        if (bangTonTai(conn, "HangHoa")) {
            System.out.println("Bang HangHoa da ton tai.");
            return;
        }

        String sql = """
                CREATE TABLE HangHoa (
                    MHH INTEGER NOT NULL,
                    THH VARCHAR(50) NOT NULL,
                    MaLoai INTEGER NOT NULL,
                    TinhChat VARCHAR(50),
                    CONSTRAINT PK_HangHoa PRIMARY KEY (MHH)
                )
                """;

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }

        System.out.println("Da tao bang HangHoa.");
    }

    // =========================================================
    // TẠO KHÓA NGOẠI
    // =========================================================

    private static void taoKhoaNgoai(Connection conn)
            throws SQLException {

        if (khoaNgoaiTonTai(conn)) {
            System.out.println("Khoa ngoai da ton tai.");
            return;
        }

        String sql = """
                ALTER TABLE HangHoa
                ADD CONSTRAINT FK_HangHoa_LoaiHang
                FOREIGN KEY (MaLoai)
                REFERENCES LoaiHang(MaLoai)
                """;

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }

        System.out.println("Da tao khoa ngoai.");
    }

    // =========================================================
    // KIỂM TRA BẢNG
    // =========================================================

    private static boolean bangTonTai(Connection conn, String tenBang)
            throws SQLException {

        DatabaseMetaData metaData = conn.getMetaData();

        try (ResultSet rs = metaData.getTables(
                null,
                null,
                tenBang,
                new String[]{"TABLE"})) {

            return rs.next();
        }
    }

    // =========================================================
    // KIỂM TRA KHÓA NGOẠI
    // =========================================================

    private static boolean khoaNgoaiTonTai(Connection conn)
            throws SQLException {

        DatabaseMetaData metaData = conn.getMetaData();

        try (ResultSet rs =
                     metaData.getImportedKeys(null, null, "HangHoa")) {

            while (rs.next()) {

                String tenKhoa =
                        rs.getString("FK_NAME");

                if ("FK_HangHoa_LoaiHang".equalsIgnoreCase(tenKhoa)) {
                    return true;
                }
            }
        }

        return false;
    }
}