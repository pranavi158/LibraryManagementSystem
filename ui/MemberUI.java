package ui;

import dao.BookDAO;
import dao.BorrowRecordDAO;
import dao.FineDAO;
import dao.MemberDAO;
import model.Book;
import model.BorrowRecord;
import model.Fine;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MemberUI extends JFrame {
    private final Connection conn;
    String memberId;
    BookDAO bookDAO;
    BorrowRecordDAO borrowRecordDAO;
    FineDAO fineDAO;
    MemberDAO memberDAO;
    JTable outputTable;
    DefaultTableModel tableModel;

    public MemberUI(Connection conn, String memberId) {
        this.conn = conn;
        this.memberId = memberId;
        this.bookDAO = new BookDAO(conn);
        this.borrowRecordDAO = new BorrowRecordDAO(conn);
        this.fineDAO = new FineDAO(conn);
        this.memberDAO = new MemberDAO(conn);

        setTitle("Member Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));
        JButton borrowButton = new JButton("Borrow Book");
        JButton returnButton = new JButton("Return Book");
        JButton historyButton = new JButton("View Borrow History");
        JButton finesButton = new JButton("Manage Fines");
        JButton viewBooksButton = new JButton("View All Books");
        JButton searchBookButton = new JButton("Search Book By ID");
        JButton paymentHistoryButton = new JButton("View Payment History");

        panel.add(borrowButton);
        panel.add(returnButton);
        panel.add(historyButton);
        panel.add(finesButton);
        panel.add(viewBooksButton);
        panel.add(searchBookButton);
        panel.add(paymentHistoryButton);

        add(panel, BorderLayout.WEST);

        tableModel = new DefaultTableModel();
        outputTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(outputTable);
        add(scrollPane, BorderLayout.CENTER);

        borrowButton.addActionListener(e -> borrowBook());
        returnButton.addActionListener(e -> returnBook());
        historyButton.addActionListener(e -> viewBorrowHistory());
        finesButton.addActionListener(e -> manageFines());
        viewBooksButton.addActionListener(e -> viewAllBooks());
        searchBookButton.addActionListener(e -> searchBookById());
        paymentHistoryButton.addActionListener(e -> viewPaymentHistory());

        setVisible(true);
    }

    private void borrowBook() {
        String bookId = JOptionPane.showInputDialog(this, "Enter Book ID to borrow:");
        if (bookId == null || bookId.trim().isEmpty()) return;

        try {
            boolean success = borrowRecordDAO.borrowBook(memberId, bookId.trim());
            tableModel.setRowCount(0);
            tableModel.setColumnIdentifiers(new String[]{"Action"});
            if (success) {
                tableModel.addRow(new Object[]{"Book ID " + bookId.trim() + " borrowed successfully."});
            } else {
                tableModel.addRow(new Object[]{"Failed to borrow book. Please check availability or Book ID."});
            }
        } catch (SQLException e) {
            showError("Error borrowing book: " + e.getMessage());
        }
    }

    private void returnBook() {
        String bookId = JOptionPane.showInputDialog(this, "Enter Book ID to return:");
        if (bookId == null || bookId.trim().isEmpty()) return;
        try {
            boolean success = borrowRecordDAO.returnBook(memberId, bookId.trim());
            tableModel.setRowCount(0);
            tableModel.setColumnIdentifiers(new String[]{"Action"});
            if (success) {
                tableModel.addRow(new Object[]{"Book ID " + bookId.trim() + " returned successfully."});
            } else {
                tableModel.addRow(new Object[]{"Failed to return book. Check if the book was borrowed and not yet returned."});
            }
        } catch (SQLException ex) {
            showError("Error returning book: " + ex.getMessage());
        }
    }

    private void viewBorrowHistory() {
        try {
            List<BorrowRecord> history = borrowRecordDAO.getBorrowRecordsByMemberId(memberId);
            tableModel.setRowCount(0);
            tableModel.setColumnIdentifiers(new String[]{"Borrow ID", "Book ID", "Borrow Date", "Due Date", "Returned", "Return Date"});

            if (history.isEmpty()) {
                tableModel.addRow(new Object[]{"No records found", "", "", "", "", ""});
                return;
            }

            for (BorrowRecord record : history) {
                String returnDateDisplay;
                if (record.getSubmitted() && record.getReturnDate() != null) {
                    returnDateDisplay = record.getReturnDate().toString();
                } else {
                    returnDateDisplay = "Not returned";
                }

                String returnedStatus;
                if (record.getSubmitted()) {
                    returnedStatus = "Yes";
                } else {
                    returnedStatus = "No";
                }

                tableModel.addRow(new Object[]{
                        record.getBorrowID(),
                        record.getBookID(),
                        record.getBorrowDate(),
                        record.getDueDate(),
                        returnedStatus,
                        returnDateDisplay
                });
            }
        } catch (SQLException e) {
            showError("Error fetching borrow history: " + e.getMessage());
        }
    }

    private void manageFines() {
        try {
            List<Fine> fines = fineDAO.getUnpaidFinesByMember(memberId.trim());

            tableModel.setRowCount(0);
            tableModel.setColumnIdentifiers(new String[]{"Fine ID", "Amount", "Paid", "Date"});

            if (fines.isEmpty()) {
                tableModel.addRow(new Object[]{"No fines", "", "", ""});
            } else {
                for (Fine fine : fines) {
                    String paidStatus;
                    if (fine.getPaidStatus()) {
                        paidStatus = "Yes";
                    } else {
                        paidStatus = "No";
                    }

                    String paymentDate;
                    if (fine.getPaymentDate() != null) {
                        paymentDate = fine.getPaymentDate().toString();
                    } else {
                        paymentDate = "Not paid yet";
                    }

                    tableModel.addRow(new Object[]{
                            fine.getFineID(),
                            "₹" + fine.getFineAmount(),
                            paidStatus,
                            paymentDate
                    });
                }
            }

            String payFineId = JOptionPane.showInputDialog(this, "Enter Fine ID to mark as paid (or Cancel):");
            if (payFineId != null && !payFineId.trim().isEmpty()) {
                try {
                    boolean paid = fineDAO.payFine(Integer.parseInt(payFineId.trim()));
                    if (paid) {
                        tableModel.addRow(new Object[]{"Fine ID " + payFineId.trim() + " marked as paid.", "", "", ""});
                    } else {
                        tableModel.addRow(new Object[]{"Failed to mark fine as paid.", "", "", ""});
                    }
                } catch (NumberFormatException ex) {
                    showError("Invalid Fine ID input.");
                }
            }
        } catch (SQLException e) {
            showError("Error managing fines: " + e.getMessage());
        }
    }

    private void viewAllBooks() {
        try {
            List<Book> books = bookDAO.getAllBooks();
            tableModel.setRowCount(0);
            tableModel.setColumnIdentifiers(new String[]{"Book ID", "Title", "Author", "Publisher", "Year", "Copies"});

            if (books.isEmpty()) {
                tableModel.addRow(new Object[]{"No books found", "", "", "", "", ""});
                return;
            }

            for (Book book : books) {
                tableModel.addRow(new Object[]{
                        book.getBookID(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getPublisher(),
                        book.getYearPublished(),
                        book.getCopiesAvailable()
                });
            }
        } catch (SQLException e) {
            showError("Error fetching books: " + e.getMessage());
        }
    }

    private void searchBookById() {
        String bookId = JOptionPane.showInputDialog(this, "Enter Book ID to search:");
        if (bookId == null || bookId.trim().isEmpty()) return;
        try {
            Book book = bookDAO.getBookById(bookId.trim());
            tableModel.setRowCount(0);
            tableModel.setColumnIdentifiers(new String[]{"Book ID", "Title", "Author", "Publisher", "Year", "Copies"});

            if (book != null) {
                tableModel.addRow(new Object[]{
                        book.getBookID(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getPublisher(),
                        book.getYearPublished(),
                        book.getCopiesAvailable()
                });
            } else {
                tableModel.addRow(new Object[]{"Book not found", "", "", "", "", ""});
            }
        } catch (SQLException e) {
            showError("Error searching for book: " + e.getMessage());
        }
    }

    private void viewPaymentHistory() {
        try {
            List<Fine> payments = fineDAO.getPaymentHistory(memberId.trim());
            tableModel.setRowCount(0);
            tableModel.setColumnIdentifiers(new String[]{"Fine ID", "Amount", "Paid On"});

            if (payments.isEmpty()) {
                tableModel.addRow(new Object[]{"No payment history", "", ""});
                return;
            }

            for (Fine fine : payments) {
                String paidOn;
                if (fine.getPaymentDate() != null) {
                    paidOn = fine.getPaymentDate().toString();
                } else {
                    paidOn = "N/A";
                }

                tableModel.addRow(new Object[]{
                        fine.getFineID(),
                        "₹" + fine.getFineAmount(),
                        paidOn
                });
            }
        } catch (SQLException e) {
            showError("Error retrieving payment history: " + e.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(new String[]{"Error"});
        tableModel.addRow(new Object[]{message});
    }
}
