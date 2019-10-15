package com.twu.biblioteca;

import org.mockito.internal.exceptions.ExceptionIncludingMockitoWarnings;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BibliotecaApp {
    String[] menuList;
    Scanner scanner;
    BookList bookList;
    boolean quit;

    public BibliotecaApp(BookList bookList, String[] menuList){
        this.bookList = bookList;
        this.menuList = menuList;
        this.scanner = new Scanner(System.in);
        this.quit = false;
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
        return scanner.nextInt();
    }

    String getUserInputString(){
        return scanner.nextLine();
    }

    void selectOption(int index){
        System.out.println(index);
        if (index > menuList.length-1 && index < 0){
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
            System.out.println("TESTING");
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
        BookList bookList = new BookList(listOfBooks);
        String[] menuList = {"List of Books", "Check Out Book", "Return Book", "Quit Application"};
        int command;
        BibliotecaApp app = new BibliotecaApp(bookList, menuList);
        app.printWelcomeMessage();
        app.viewMenu();
        command = app.getUserInputInteger();
        app.selectOption(command);
    }
}
