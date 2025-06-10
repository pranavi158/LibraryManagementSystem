package model;

public class User {
    private String username;
    private String password;
    private String role; 
    private String member_id;


    public User(String username, String password, String role, String member_id) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.member_id = member_id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMemberID(){
        return member_id;
    }

    public void setMemberID(String member_id){
        this.member_id = member_id;
    }
}
