package com.twu.biblioteca;

import java.util.Scanner;

public class BibliotecaApp {
    String[] menuList;
    Scanner scanner;
    BookList bookList;
    boolean quit;

    public BibliotecaApp(BookList bookList, String[] menuList){
        this.bookList = bookList;
        this.menuList = menuList;
        this.quit = false;
    }

    boolean isQuit(){
        return this.quit;
    }

    void printWelcomeMessage(){
        System.out.println("Welcome to Biblioteca. Your one-stop-shop for great book titles in Banglore!");
    }

    void viewListOfBooks(){
        String listFormat = "%-45s%-25s%s\n";
        System.out.printf(listFormat, "Title", "Author", "Publication Year");
        for (Book book : bookList.listOfBooks){
            System.out.printf(listFormat, book.getTitle(), book.getAuthor(), book.getPublicationYear());
        }
    }

    void viewMenu(){
        String menuFormat = "%5s  %s\n";
        System.out.printf(menuFormat, "Index", "Option");
        for (int i = 0; i < menuList.length; i++){
            System.out.printf(menuFormat, i, menuList[i]);
        }
    }

    int getUserInputInteger(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    String getUserInputString(){
        System.out.println("Please key in the book name:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    void selectOption(int index){
        if (index > menuList.length-1 || index < 0){
            System.out.println("Please select a valid option!");
        }
        if (index == 0){
            this.viewListOfBooks();
        }
        else if (index == 1){
            String bookName = this.getUserInputString();
            this.checkOutBook(bookName);
        }
        else if (index == 2){
            String bookName = this.getUserInputString();
            this.returnBook(bookName);
        }
        else if (index == 3){
            this.quitApplication();
        }
    }

    void quitApplication(){
        this.quit = true;
    }

    void checkOutBook(String bookName){
        if (bookList.isExist(bookName)){
            Book book = bookList.getBook(bookName);
            if (!book.isCheckOut()){
                bookList.getBook(bookName).checkOut();
                System.out.println("Thank you! Enjoy the book");
                return;
            }
        }
        System.out.println("Sorry, that book is not available");
    }

    void returnBook(String bookName){
        if (bookList.isExist(bookName)) {
            Book book = bookList.getBook(bookName);
            if (book.getIsOnLeased()) {
                book.returnBook();
                System.out.println("Thank you for returning the book");
                return;
            }
        }
        System.out.println("This is not a valid book to return");
    }
}
