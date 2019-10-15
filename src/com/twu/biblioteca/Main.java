package com.twu.biblioteca;

import java.util.Arrays;
import java.util.List;

public class Main {
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
        BibliotecaApp app = new BibliotecaApp(bookList, menuList);
        app.printWelcomeMessage();

        while (!app.isQuit()){
            int command;
            app.viewMenu();
            System.out.println("Please key in the index of the option.");
            command = app.getUserInputInteger();
            app.selectOption(command);
        }
    }
}
