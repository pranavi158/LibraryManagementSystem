package model;
// CREATE TABLE books (
//     ->     book_id VARCHAR(10) PRIMARY KEY,  -- e.g., BK001, BK002
//     ->     title VARCHAR(255) NOT NULL,
//     ->     author VARCHAR(255),
//     ->     publisher VARCHAR(255),
//     ->     year_published YEAR,
//     ->     copies_available INT DEFAULT 0,
//     ->     CHECK (copies_available >= 0 AND copies_available <= 200)
//     -> );
public class Book {
    String book_id;
    String title;
    String author;
    String publisher;
    int year_published;
    int copies_available;

    public Book(String book_id,String title,String author,String publisher,int year_published,int copies_available){
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year_published = year_published;
        this.copies_available = copies_available;
    }

    public String getBookID() {
        return book_id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getYearPublished() {
        return year_published;
    }

    public int getCopiesAvailable() {
        return copies_available;
    }

    public void setBookID(String book_id) {
        this.book_id = book_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setYearPublished(int year_published) {
        this.year_published = year_published;
    }

    public void setCopiesAvailable(int copies_available) {
        this.copies_available = copies_available;
    }
}
