package model;
// CREATE TABLE members (
//     ->     member_id VARCHAR(10) PRIMARY KEY,
//     ->     name VARCHAR(100) NOT NULL,
//     ->     email VARCHAR(100) UNIQUE,
//     ->     phone VARCHAR(15),
//     ->     membership_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
//     -> );

import java.time.LocalDate;

public class Member {
    String member_id;
    String name;
    String email;
    String phone;
    LocalDate membership_date;

    public Member(String member_id, String name, String email, String phone, LocalDate membership_date){
        this.member_id = member_id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.membership_date = membership_date;
    }

    public String getMemberID(){
        return member_id;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getPhone(){
        return phone;
    }

    public LocalDate getMembershipDate(){
        return membership_date;
    }

    public void setMemberID(String member_id){
        this.member_id = member_id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public void setMembershipDate(LocalDate membership_date){
        this.membership_date = membership_date;
    }

}
