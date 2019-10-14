package com.twu.biblioteca;

public class Book {
    String title;
    String author;
    int publicationYear;
    boolean isOnLeased;

    public Book(String title, String author, int publicationYear) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isOnLeased = false;
    }

    void checkOut(){
        this.isOnLeased = true;
    }

    void returnBook(){
        this.isOnLeased = false;
    }
}
