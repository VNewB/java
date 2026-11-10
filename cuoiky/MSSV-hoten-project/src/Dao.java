import java.sql.*;
import java.util.*;

/** Mọi thao tác với Database Server. Không kết nối được -> ném SQLException. */
public class Dao {
    private static Connection conn() throws SQLException {
        Connection c = DBConnection.getConnection();
        if (c == null) throw new SQLException("Không kết nối được CSDL: " + DBConnection.lastError);
        return c;
    }

    // ---------- XE ----------
    public static List<Xe> getAllXe() throws SQLException {
        List<Xe> l = new ArrayList<>();
        try (Connection c = conn(); Statement s = c.createStatement();
             ResultSet rs = s.executeQuery("SELECT MaXe,TenXe,DungTich,DonGia FROM Xe ORDER BY MaXe")) {
            while (rs.next()) l.add(new Xe(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
        }
        return l;
    }

    public static boolean existsXe(int maXe) throws SQLException {
        try (Connection c = conn();
             PreparedStatement ps = c.prepareStatement("SELECT 1 FROM Xe WHERE MaXe=?")) {
            ps.setInt(1, maXe);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }

    public static void insertXe(Xe x) throws SQLException {
        try (Connection c = conn();
             PreparedStatement ps = c.prepareStatement("INSERT INTO Xe(MaXe,TenXe,DungTich,DonGia) VALUES(?,?,?,?)")) {
            ps.setInt(1, x.maXe); ps.setString(2, x.tenXe);
            ps.setInt(3, x.dungTich); ps.setInt(4, x.donGia);
            ps.executeUpdate();
        }
    }

    public static void deleteXe(int maXe) throws SQLException {
        try (Connection c = conn()) {
            c.setAutoCommit(false);
            try (PreparedStatement p1 = c.prepareStatement("DELETE FROM Mua_Xe WHERE MaXe=?");
                 PreparedStatement p2 = c.prepareStatement("DELETE FROM Xe WHERE MaXe=?")) {
                p1.setInt(1, maXe); p1.executeUpdate();
                p2.setInt(1, maXe); p2.executeUpdate();
                c.commit();
            } catch (SQLException e) { c.rollback(); throw e; }
        }
    }

    // ---------- CHI TIẾT (khách hàng đã mua xe) ----------
    /** ngay = null -> không lọc theo ngày. */
    public static List<ChiTiet> getChiTiet(int maXe, java.sql.Date ngay) throws SQLException {
        String sql = "SELECT k.MaKH,k.TenKH,k.CMND,k.GioiTinh,k.DiaChi,m.NgayMua,m.SoLuong,m.GhiChu "
                   + "FROM Mua_Xe m JOIN Khach_Hang k ON k.MaKH=m.MaKH WHERE m.MaXe=? "
                   + (ngay != null ? "AND m.NgayMua=? " : "") + "ORDER BY m.NgayMua, k.MaKH";
        List<ChiTiet> l = new ArrayList<>();
        try (Connection c = conn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, maXe);
            if (ngay != null) ps.setDate(2, ngay);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChiTiet t = new ChiTiet();
                    t.maXe = maXe;
                    t.maKH = rs.getInt(1); t.tenKH = rs.getString(2); t.cmnd = rs.getLong(3);
                    t.gioiTinh = rs.getString(4); t.diaChi = rs.getString(5);
                    t.ngayMua = rs.getDate(6); t.soLuong = rs.getInt(7); t.ghiChu = rs.getString(8);
                    l.add(t);
                }
            }
        }
        return l;
    }

    private static void upsertKhachHang(Connection c, ChiTiet t) throws SQLException {
        try (PreparedStatement up = c.prepareStatement(
                "UPDATE Khach_Hang SET TenKH=?,CMND=?,GioiTinh=?,DiaChi=? WHERE MaKH=?")) {
            up.setString(1, t.tenKH); up.setLong(2, t.cmnd); up.setString(3, t.gioiTinh);
            up.setString(4, t.diaChi); up.setInt(5, t.maKH);
            if (up.executeUpdate() > 0) return;
        }
        try (PreparedStatement in = c.prepareStatement(
                "INSERT INTO Khach_Hang(MaKH,TenKH,CMND,GioiTinh,DiaChi) VALUES(?,?,?,?,?)")) {
            in.setInt(1, t.maKH); in.setString(2, t.tenKH); in.setLong(3, t.cmnd);
            in.setString(4, t.gioiTinh); in.setString(5, t.diaChi);
            in.executeUpdate();
        }
    }

    public static void insertChiTiet(ChiTiet t) throws SQLException {
        try (Connection c = conn()) {
            c.setAutoCommit(false);
            try {
                upsertKhachHang(c, t);
                try (PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO Mua_Xe(MaKH,MaXe,NgayMua,SoLuong,GhiChu) VALUES(?,?,?,?,?)")) {
                    ps.setInt(1, t.maKH); ps.setInt(2, t.maXe); ps.setDate(3, t.ngayMua);
                    ps.setInt(4, t.soLuong); ps.setString(5, t.ghiChu);
                    ps.executeUpdate();
                }
                c.commit();
            } catch (SQLException e) { c.rollback(); throw e; }
        }
    }

    public static void updateChiTiet(ChiTiet t, java.sql.Date ngayCu) throws SQLException {
        try (Connection c = conn()) {
            c.setAutoCommit(false);
            try {
                upsertKhachHang(c, t);
                try (PreparedStatement ps = c.prepareStatement(
                        "UPDATE Mua_Xe SET NgayMua=?,SoLuong=?,GhiChu=? WHERE MaKH=? AND MaXe=? AND NgayMua=?")) {
                    ps.setDate(1, t.ngayMua); ps.setInt(2, t.soLuong); ps.setString(3, t.ghiChu);
                    ps.setInt(4, t.maKH); ps.setInt(5, t.maXe); ps.setDate(6, ngayCu);
                    ps.executeUpdate();
                }
                c.commit();
            } catch (SQLException e) { c.rollback(); throw e; }
        }
    }

    public static void deleteChiTiet(int maKH, int maXe, java.sql.Date ngay) throws SQLException {
        try (Connection c = conn();
             PreparedStatement ps = c.prepareStatement("DELETE FROM Mua_Xe WHERE MaKH=? AND MaXe=? AND NgayMua=?")) {
            ps.setInt(1, maKH); ps.setInt(2, maXe); ps.setDate(3, ngay);
            ps.executeUpdate();
        }
    }
}
