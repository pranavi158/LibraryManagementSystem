package model;
import java.sql.Date;
// CREATE TABLE fines (
//     fine_id INT AUTO_INCREMENT PRIMARY KEY,
//     borrow_id INT NOT NULL,
//     member_id VARCHAR(10) NOT NULL,
//     fine_amount DECIMAL(10,2) NOT NULL DEFAULT 0,
//     paid_status TINYINT(1) NOT NULL DEFAULT 0,
//     payment_date DATETIME,
//     FOREIGN KEY (borrow_id) REFERENCES borrow_records(borrow_id) ON DELETE CASCADE,
//     FOREIGN KEY (member_id) REFERENCES members(member_id) ON DELETE CASCADE
// );

public class Fine {
    String fine_id;
    int borrow_id;
    double fine_amount;
    boolean paid_status;
    Date payment_date;
    String member_id;
    
    
    public Fine(String fine_id, int borrow_id, double fine_amount, boolean paid_status, Date payment_date, String member_id){
        this.fine_id = fine_id;
        this.borrow_id = borrow_id;
        this.fine_amount = fine_amount;
        this.member_id = member_id;
        this.paid_status = paid_status;
        this.payment_date = payment_date;
    }

    public String getFineID(){
        return fine_id;
    }

    public int getBorrowID(){
        return borrow_id;
    }

    

    public double getFineAmount(){
        return fine_amount;
    }

    public boolean getPaidStatus(){
        return paid_status;
    }

    public Date getPaymentDate(){
        return payment_date;
    }

    public void setFineID(String fine_id){
        this.fine_id = fine_id;
    }

    public void setBorrowID(int borrow_id){
        this.borrow_id = borrow_id;
    }

    public void setFineAmount(double fine_amount){
        this.fine_amount = fine_amount;
    }

    public void setPaidStatus(boolean paid_status){
        this.paid_status = paid_status;
    }

    public void setPaymentDate(Date payment_date){
        this.payment_date = payment_date;
    }
}
