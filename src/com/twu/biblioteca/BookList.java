package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookList {
    List<Book> listOfBooks;
    int totalBooks;
    List<String> listOfBooksTitle;

    public BookList(List<Book> listOfBooks) {
        this.listOfBooks = listOfBooks;
        this.totalBooks = this.listOfBooks.size();
    }

    boolean isExist(String bookName){
        for (Book book : this.listOfBooks){
            if (book.getTitle().equals(bookName)){
                return true;
            }
        }
        return false;
    }

    Book getBook(String bookName){
        for (Book book : this.listOfBooks){
            if (book.getTitle().equals(bookName)){
                return book;
            }
        }
        return null;
    }


}
