package dao;

import model.Fine;
import java.sql.*;
import java.util.*;

public class FineDAO {
    private final Connection conn;

    public FineDAO(Connection conn) {
        this.conn = conn;
    }


    public boolean payFine(int fineId) throws SQLException {
        String sql = "UPDATE fines SET paid_status = 1, payment_date = NOW() WHERE fine_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, fineId);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Fine> getUnpaidFinesByMember(String memberId) throws SQLException {
        List<Fine> list = new ArrayList<>();
        String sql = "SELECT * FROM fines WHERE member_id = ? AND paid_status = 0";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, memberId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapFine(rs));
                }
            }
        }
        return list;
    }

    public List<Fine> getPaymentHistory(String memberId) throws SQLException {
        List<Fine> list = new ArrayList<>();
        String sql = "SELECT * FROM fines WHERE member_id = ? AND paid_status = 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, memberId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapFine(rs));
                }
            }
        }
        return list;
    }

    public double getTotalUnpaidFines(String memberId) throws SQLException {
    String sql = "SELECT COALESCE(SUM(fine_amount), 0) AS total_fines FROM fines WHERE member_id = ? AND paid_status = 0";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, memberId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                double total = rs.getDouble("total_fines");
                System.out.println("DEBUG: Total unpaid fines for member " + memberId + " = " + total);
                return total;
            }
        }
    }
    return 0.0;
}

public void createFineIfNotExists(int borrowId, String memberId, double fineAmount) throws SQLException {
    String checkSql = "SELECT * FROM fines WHERE borrow_id = ?";
    try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
        checkStmt.setInt(1, borrowId);
        try (ResultSet rs = checkStmt.executeQuery()) {
            if (!rs.next()) {
                String insertSql = "INSERT INTO fines (borrow_id, member_id, fine_amount, paid_status) VALUES (?, ?, ?, FALSE)";
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setInt(1, borrowId);
                    insertStmt.setString(2, memberId);
                    insertStmt.setDouble(3, fineAmount);
                    insertStmt.executeUpdate();
                }
            }
        }
    }
}

private Fine mapFine(ResultSet rs) throws SQLException {
        return new Fine(
            String.valueOf(rs.getInt("fine_id")),
            rs.getInt("borrow_id"),
            rs.getDouble("fine_amount"),
            rs.getBoolean("paid_status"),
            rs.getDate("payment_date"),
            rs.getString("member_id")
        );
    }


}
