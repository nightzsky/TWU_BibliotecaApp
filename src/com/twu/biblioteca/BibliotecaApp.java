package com.twu.biblioteca;

import org.mockito.internal.exceptions.ExceptionIncludingMockitoWarnings;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BibliotecaApp {
    List<Book> listOfBooks;
    String[] menuList;
    Scanner scanner;
    BookList bookList;

    public BibliotecaApp(List<Book> listOfBooks, String[] menuList){
        this.listOfBooks = listOfBooks;
        this.bookList = new BookList(this.listOfBooks);
        this.menuList = menuList;
        this.scanner = new Scanner(System.in);
    }
    public void printWelcomeMessage(){
        System.out.println("Welcome to Biblioteca. Your one-stop-shop for great book titles in Banglore!");
    }

    public void viewListOfBooks(){
        String listFormat = "%-45s%-25s%s\n";
        System.out.printf(listFormat, "Title", "Author", "Publication Year");
        for (Book book : listOfBooks){
            System.out.printf(listFormat, book.title, book.author, book.publicationYear);
        }
    }

    public void viewMenu(){
        String menuFormat = "%5s  %s\n";
        System.out.printf(menuFormat, "Index", "Option");
        for (int i = 0; i < menuList.length; i++){
            System.out.printf(menuFormat, i, menuList[i]);
        }
    }

    public int getUserInputInteger(String message){
        System.out.println(message);
        return scanner.nextInt();
    }

    public void selectOption(int index){
        if (index > menuList.length-1){
            System.out.println("Please select a valid option!");
            //this.getUserInputInteger("Please key in again.");
            //prompt for user input
        }
        if (index == 0){
            this.viewListOfBooks();
        }
        else if (index == 2){
            quitApplication();
        }
    }

    public void quitApplication(){
        System.out.println("Thank you for using Biblioteca App. Hope to see you again!");
        System.exit(0);
    }

    public void checkOutBook(String bookName){
        if (bookList.isExist(bookName)){
            Book book = bookList.getBook(bookName);
            if (!book.isCheckOut()){
                bookList.getBook(bookName).checkOut();
                System.out.println("Thank you! Enjoy the book");
            }
            else {
                System.out.println("Sorry, that book is not available");
            }
        }
        else {
            System.out.println("Sorry, that book is not available");
        }
    }

    public void returnBook(String bookName){
        if (bookList.isExist(bookName)) {
            Book book = bookList.getBook(bookName);
            if (book.isOnLeased){
                book.returnBook();
                System.out.println("Thank you for returning the book");
            }
            else {
                System.out.println("This is not a valid book to return");
            }
        }
        else {
            System.out.println("This is not a valid book to return");
        }
    }



    public static void main(String[] args)
    {
        List<Book> listOfBooks = Arrays.asList(new Book("Harry Potter and the Sorcerer's Stone", "J. K. Rowling", 1997),
                new Book("Harry Potter and the Chamber of Secrets", "J.K. Rowling", 1998),
                new Book("The Hobbit", "J. R. R. Tolkien", 1937),
                new Book("1984", "George Orwell", 1949),
                new Book("Twilight", "Stephenie Meyer",2005),
                new Book("The Da Vinci Code", "Dan Brown", 2000),
                new Book("The Hunger Games", "Suzanne Collins", 2008),
                new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925),
                new Book("Brave New World", "Aldous Huxley", 1932),
                new Book("Fahrenheit 451", "Ray Bradbury", 1953));
        String[] menuList = {"List of Books", "Check Out Book", "Quit Application"};
        String userInputPromptIntegerMessage = "Please key in the index of your selection.";
        int command;

        BibliotecaApp app = new BibliotecaApp(listOfBooks, menuList);
        app.printWelcomeMessage();
        app.viewMenu();
        command = app.getUserInputInteger(userInputPromptIntegerMessage);
        app.selectOption(command);


    }
}
