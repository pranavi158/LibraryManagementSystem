package model;

import java.time.LocalDate;
import java.sql.Date;

// CREATE TABLE borrow_records (
//     ->     borrow_id VARCHAR(10) PRIMARY KEY,
//     ->     member_id VARCHAR(10),
//     ->     book_id VARCHAR(10),
//     ->     borrow_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//     ->     return_date TIMESTAMP NULL,
//     ->     returned BOOLEAN DEFAULT FALSE,
//     ->     FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE,
//     ->     FOREIGN KEY (member_id) REFERENCES members(member_id) ON DELETE CASCADE);
public class BorrowRecord {
    String borrow_id;
    String member_id;
    String book_id;
    LocalDate borrow_date;
    Date due_date;
    LocalDate return_date;
    boolean submitted;
    

    public BorrowRecord(String borrow_id,String member_id,String book_id,LocalDate borrow_date,Date due_date,LocalDate return_date,boolean submitted){
        this.borrow_id = borrow_id;
        this.member_id = member_id;
        this.book_id = book_id;
        this.borrow_date = borrow_date;
        this.due_date = due_date;
        this.return_date = return_date;
        this.submitted = submitted;
    }
//     @Override
// public String toString() {
//     return String.format("Borrow ID: %d | Book ID: %s | Borrow Date: %s | Returned: %s",
//         borrow_id, book_id, borrow_date.toString(), submitted ? "Yes" : "No");
// }


    public String getBorrowID(){
        return borrow_id;
    }

    public Date getDueDate(){
        return due_date;
    }

    public String getMemberID(){
        return member_id;
    }

    public String getBookID(){
        return book_id;
    }

    public LocalDate getBorrowDate(){
        return borrow_date;
    }

    public LocalDate getReturnDate(){
        return return_date;
    }

    public boolean getSubmitted(){
        return submitted;
    }

    public void setBorrowID(String borrow_id){
        this.borrow_id = borrow_id;
    }

    public void setMemberID(String member_id){
        this.member_id = member_id;
    }

    public void setBookID(String book_id){
        this.book_id = book_id;
    }

    public void setBorrowDate(LocalDate borrow_date){
        this.borrow_date = borrow_date;
    }

    public void setDueDate(Date due_date){
        this.due_date = due_date;
    }

    public void setReturnDate(LocalDate return_date){
        this.return_date = return_date;
    }

    public void setSubmitted(boolean submitted){
        this.submitted = submitted;
    }
}
