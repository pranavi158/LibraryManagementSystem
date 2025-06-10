package ui;

import dao.BookDAO;
import dao.BorrowRecordDAO;
import dao.FineDAO;
import dao.MemberDAO;
import model.Book;
import model.BorrowRecord;
import model.Member;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AdminUI extends JFrame {

    private final Connection conn;
    BookDAO bookDAO;
    BorrowRecordDAO borrowRecordDAO;
    FineDAO fineDAO;
    MemberDAO memberDAO;

    JTable outputTable;
    DefaultTableModel tableModel;

    public AdminUI(Connection conn) {
        this.conn = conn;
        this.bookDAO = new BookDAO(conn);
        this.borrowRecordDAO = new BorrowRecordDAO(conn);
        this.fineDAO = new FineDAO(conn);
        this.memberDAO = new MemberDAO(conn);

        setTitle("Admin Dashboard - Library Management");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        tableModel = new DefaultTableModel();
        outputTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(outputTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 20));

        JButton addBookBtn = new JButton("Add Book");
        JButton viewBorrowedBooksBtn = new JButton("View Borrowed Books");
        JButton manageFinesBtn = new JButton("Manage Fines");
        JButton viewMembersSummaryBtn = new JButton("View Members Summary");

        buttonPanel.add(addBookBtn);
        buttonPanel.add(viewBorrowedBooksBtn);
        buttonPanel.add(manageFinesBtn);
        buttonPanel.add(viewMembersSummaryBtn);

        add(buttonPanel, BorderLayout.WEST);

        addBookBtn.addActionListener(e -> addBook());
        viewBorrowedBooksBtn.addActionListener(e -> viewBorrowedBooks());
        manageFinesBtn.addActionListener(e -> manageFines());
        viewMembersSummaryBtn.addActionListener(e -> viewMembersSummary());

        setLocationRelativeTo(null);
    }

    private void addBook() {
        JTextField bookIDField = new JTextField();
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField publisherField = new JTextField();
        JTextField yearPublishedField = new JTextField();
        JTextField copiesAvailableField = new JTextField();

        Object[] message = {
                "Book ID (unique):", bookIDField,
                "Title:", titleField,
                "Author:", authorField,
                "Publisher:", publisherField,
                "Year Published:", yearPublishedField,
                "Copies Available:", copiesAvailableField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add New Book", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String book_id = bookIDField.getText().trim();
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                String publisher = publisherField.getText().trim();
                int year_published = Integer.parseInt(yearPublishedField.getText().trim());
                int copies_available = Integer.parseInt(copiesAvailableField.getText().trim());

                if (book_id.isEmpty() || title.isEmpty() || author.isEmpty()) {
                    showError("Book ID, Title, and Author are required.");
                    return;
                }

                Book book = new Book(book_id, title, author, publisher, year_published, copies_available);

                boolean success = bookDAO.addBook(book);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Book added successfully: " + title);
                } else {
                    showError("Failed to add book. Book ID might already exist.");
                }

            } catch (NumberFormatException ex) {
                showError("Year Published and Copies Available must be valid numbers.");
            } catch (SQLException ex) {
                showError("Database error while adding book: " + ex.getMessage());
            }
        }
    }

    private void viewBorrowedBooks() {
        try {
            String memberId = JOptionPane.showInputDialog(this, "Enter Member ID:");
            if (memberId == null || memberId.trim().isEmpty()) {
                return;
            }

            List<BorrowRecord> records = borrowRecordDAO.getBorrowRecordsByMemberId(memberId.trim());
            tableModel.setRowCount(0);
            tableModel.setColumnIdentifiers(new String[]{"Book ID", "Borrowed Date", "Returned Date"});

            if (records.isEmpty()) {
                tableModel.addRow(new Object[]{"No records found", "", ""});
            } else {
                for (BorrowRecord br : records) {
                    tableModel.addRow(new Object[]{
                            br.getBookID(),
                            br.getBorrowDate(),
                            br.getReturnDate() == null ? "Not returned" : br.getReturnDate()
                    });
                }
            }
        } catch (SQLException e) {
            showError("Error fetching borrow records: " + e.getMessage());
        }
    }

    private void manageFines() {
        String memberId = JOptionPane.showInputDialog(this, "Enter Member ID to check total unpaid fines:");
        if (memberId == null || memberId.trim().isEmpty()) {
            return;
        }

        try {
            memberId = memberId.trim();
            List<BorrowRecord> records = borrowRecordDAO.getBorrowRecordsByMemberId(memberId);

            java.time.LocalDate today = java.time.LocalDate.now();
            for (BorrowRecord record : records) {
                if (!record.getSubmitted() && record.getDueDate().toLocalDate().isBefore(today)) {
                    long daysLate = java.time.temporal.ChronoUnit.DAYS.between(record.getDueDate().toLocalDate(), today);
                    double fine = daysLate * 5.0; 
                    fineDAO.createFineIfNotExists(Integer.parseInt(record.getBorrowID()), record.getMemberID(), fine);

                }
            }


            double totalFines = fineDAO.getTotalUnpaidFines(memberId);
            tableModel.setRowCount(0);
            tableModel.setColumnIdentifiers(new String[]{"Member ID", "Total Unpaid Fine"});

            if (totalFines == 0.0) {
                tableModel.addRow(new Object[]{memberId, "No fines"});
            } else {
                tableModel.addRow(new Object[]{memberId, String.format("₹ %.2f", totalFines)});
            }
        } catch (SQLException e) {
            showError("Error fetching total unpaid fines: " + e.getMessage());
        }
    }


    private void viewMembersSummary() {
    try {
        List<Member> allMembers = memberDAO.getAllMembers();  
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(new String[]{"Member ID", "Name", "Email", "Phone", "Membership Date"});

        if (allMembers.isEmpty()) {
            tableModel.addRow(new Object[]{"No members found.", "", "", "", ""});
        } else {
            for (Member member : allMembers) {
                tableModel.addRow(new Object[]{
                    member.getMemberID(),
                    member.getName(),
                    member.getEmail(),
                    member.getPhone(),
                    member.getMembershipDate().toString() 
                });
            }
        }
    } catch (SQLException e) {
        showError("Error fetching member details: " + e.getMessage());
    }
}



    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
