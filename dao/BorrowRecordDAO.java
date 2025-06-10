package dao;

import model.BorrowRecord;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class BorrowRecordDAO {
    private final Connection conn;

    public BorrowRecordDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean borrowBook(String memberId, String bookId) throws SQLException {
    String query = "INSERT INTO borrow_records (member_id, book_id, borrow_date, due_date, submitted) VALUES (?, ?, ?, ?, FALSE)";
    try (PreparedStatement ps = conn.prepareStatement(query)) {
        LocalDate todayLocal = LocalDate.now();
        LocalDate dueDateLocal = todayLocal.plusDays(1);
        Date today = Date.valueOf(todayLocal);
        Date dueDate = Date.valueOf(dueDateLocal);

        ps.setString(1, memberId);
        ps.setString(2, bookId);
        ps.setDate(3, today);
        ps.setDate(4, dueDate);
        return ps.executeUpdate() > 0;
    }
}



    public boolean returnBook(String memberId, String bookId) throws SQLException {
    String query = "UPDATE borrow_records SET submitted = TRUE, return_date = ? WHERE member_id = ? AND book_id = ? AND submitted = FALSE";
    try (PreparedStatement ps = conn.prepareStatement(query)) {
        ps.setDate(1, Date.valueOf(LocalDate.now())); 
        ps.setString(2, memberId);
        ps.setString(3, bookId);
        return ps.executeUpdate() > 0;
    }
}

    public List<BorrowRecord> getBorrowRecordsByMemberId(String memberId) throws SQLException {
    String sql = "SELECT * FROM borrow_records WHERE member_id = ?";
    List<BorrowRecord> records = new ArrayList<>();
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, memberId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                records.add(mapResultSetToBorrowRecord(rs));
            }
        }
    }
    return records;
}


    private BorrowRecord mapResultSetToBorrowRecord(ResultSet rs) throws SQLException {
        Date returnDateSql = rs.getDate("return_date");
        LocalDate returnDate = returnDateSql != null ? returnDateSql.toLocalDate() : null;
        return new BorrowRecord(
            rs.getString("borrow_id"),
            rs.getString("member_id"),
            rs.getString("book_id"),
            rs.getDate("borrow_date").toLocalDate(),
            rs.getDate("due_date"),
            returnDate,
            rs.getBoolean("submitted")
);

    }
}
