package com.twu.biblioteca;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;



public class ExampleTest {

    BibliotecaApp bibliotecaApp;
    BibliotecaApp bibliotecaAppMock;
    List<Book> listOfBooks;
    String[] menuOption;
    BookList bookList;

    @Before
    public void setUp(){
        listOfBooks= Arrays.asList(new Book("Close to the Machine", "Ellen Ullman", 1997),
                new Book("The Art of Unix Programming", "Eric Raymond", 2003),
                new Book("Free Software, Free Society", "Richard M. Stallman", 2002));
        bookList = new BookList(listOfBooks);
        menuOption = new String[]{"List of Books"};
        bibliotecaApp = new BibliotecaApp(listOfBooks, menuOption);
        bibliotecaAppMock = mock(BibliotecaApp.class);
    }

    @Test
    public void testWelcomeMessage()
    {
        PrintStream actualPrintStatement = mock(PrintStream.class);
        System.setOut(actualPrintStatement);
        bibliotecaApp.printWelcomeMessage();
        verify(actualPrintStatement).println("Welcome to Biblioteca. Your one-stop-shop for great book titles in Banglore!");
    }

    @Test
    public void testViewListOfBooks(){

        String listFormat = "%-45s%-25s%s\n";

        PrintStream actualPrintBookMenu = mock(PrintStream.class);
        System.setOut(actualPrintBookMenu);

        bibliotecaApp.viewListOfBooks();

        verify(actualPrintBookMenu).printf(listFormat, "Title", "Author", "Publication Year");
        for (int i=0; i < listOfBooks.size(); i++){
            verify(actualPrintBookMenu).printf(listFormat, listOfBooks.get(i).title, listOfBooks.get(i).author, listOfBooks.get(i).publicationYear);
        }
    }

    @Test
    public void testViewMenuList(){

        String menuFormat = "%5s  %s\n";

        PrintStream actualPrintMenuOption = mock(PrintStream.class);
        System.setOut(actualPrintMenuOption);

        bibliotecaApp.viewMenu();

        verify(actualPrintMenuOption).printf(menuFormat, "Index", "Option");

        for (int i = 0; i <menuOption.length; i++){
            verify(actualPrintMenuOption).printf(menuFormat, i, menuOption[i]);
        }
    }

   /* @Test
    public void testGetUserInputInteger(){
        int userInputInteger = 3;
        ByteArrayInputStream userIntegerInput = new ByteArrayInputStream(Integer.toString(userInputInteger).getBytes());
        System.setIn(userIntegerInput);
        assertEquals(3, user);
    }*/

    @Test
    public void testInvalidOption(){
        PrintStream actualInvalidMessage = mock(PrintStream.class);
        System.setOut(actualInvalidMessage);

        bibliotecaApp.selectOption(1);

        verify(actualInvalidMessage).println("Please select a valid option!");
    }

    /*@Test public void testSelectQuitApplicationOption()
    {
        PrintStream actualExitMessage = mock(PrintStream.class);
        System.setOut(actualExitMessage);
        bibliotecaApp.quitApplication();
        assertTrue(bibliotecaApp.exited);
        verify(actualExitMessage).println("Thank you for using Biblioteca App. Hope to see you again!");
    }*/

    @Test
    public void testSelectViewListOfBooksOption(){
        String listFormat = "%-45s%-25s%s\n";
        PrintStream actualResponseMessage = mock(PrintStream.class);
        System.setOut(actualResponseMessage);

        bibliotecaApp.selectOption(0);

        verify(actualResponseMessage).printf(listFormat, "Title", "Author", "Publication Year");
        for (int i=0; i < listOfBooks.size(); i++) {
            verify(actualResponseMessage).printf(listFormat, listOfBooks.get(i).title, listOfBooks.get(i).author, listOfBooks.get(i).publicationYear);
        }
    }

    @Test
    public void testChangeBookIsOnLeasedToTrueForCheckOutMethod() {
        Book book = new Book("Close to the Machine", "Ellen Ullman", 1997);
        book.checkOut();
        assertTrue(book.isOnLeased);
    }

    @Test
    public void testChangeBookIsOnLeasedToFalseForReturnBookMethod(){
        Book book = new Book("Close to the Machine", "Ellen Ullman", 1997);
        book.isOnLeased = true;
        book.returnBook();
        assertFalse(book.isOnLeased);
    }

    @Test
    public void testBookIsCheckOutWhenBookIsAvailable(){
        Book book = new Book("Close to the Machine", "Ellen Ullman", 1997);
        boolean isCheckOut = book.isCheckOut();
        assertFalse(isCheckOut);
    }

    @Test
    public void testBookIsCheckOutWhenBookIsNotAvailable(){
        Book book = new Book("Close to the Machine", "Ellen Ullman", 1997);
        book.checkOut();
        boolean isCheckOut = book.isCheckOut();
        assertTrue(isCheckOut);
    }

    @Test
    public void testWhenTheBookIsNotInTheListOfBooks(){
        String bookName = "Happy Day";
        boolean actualOutput = this.bookList.isExist(bookName);
        assertFalse(actualOutput);
    }

    @Test
    public void testWhenTheBookIsInTheListOfBooks(){
        String bookName = "Free Software, Free Society";
        boolean actualOutput = this.bookList.isExist(bookName);
        assertTrue(actualOutput);
    }

    @Test
    public void testGetBookByBookNameIfBookExists(){
        String bookName = "Free Software, Free Society";
        Book expectedBook = new Book("Free Software, Free Society", "Richard M. Stallman", 2002);
        Book actualBook = bookList.getBook(bookName);
        assertEquals(actualBook.title, expectedBook.title);
        assertEquals(actualBook.author, expectedBook.author);
        assertEquals(actualBook.publicationYear, expectedBook.publicationYear);
        assertEquals(actualBook.isOnLeased, expectedBook.isOnLeased);
    }

    @Test
    public void testUserCanCheckOutBookByChangingTheStateOfTheBookToTrue(){
        String inputBookName = "Free Software, Free Society";

        bibliotecaApp.checkOutBook(inputBookName);

        assertTrue(bookList.getBook(inputBookName).isOnLeased);

    }

    @Test
    public void testUserSuccessfullyCheckOutBookIfBookExistAndIsNotOnLeased(){
        PrintStream actualPrintStatement = mock(PrintStream.class);
        System.setOut(actualPrintStatement);
        String inputBookName = "Free Software, Free Society";

        bibliotecaApp.checkOutBook(inputBookName);

        verify(actualPrintStatement).println("Thank you! Enjoy the book");
    }

    @Test
    public void testUserFailToCheckOutBookIfBookExistAndIsOnLeased(){
        PrintStream actualPrintStatement = mock(PrintStream.class);
        System.setOut(actualPrintStatement);
        String inputBookName = "Free Software, Free Society";
        bookList.getBook(inputBookName).checkOut();
        bibliotecaApp.checkOutBook(inputBookName);

        verify(actualPrintStatement).println("Sorry, that book is not available");
    }

    @Test
    public void testUserFailToCheckOutBookIfBookDoesNotExist(){
        PrintStream actualPrintStatement = mock(PrintStream.class);
        System.setOut(actualPrintStatement);
        String inputBookName = "Frez";

        bibliotecaApp.checkOutBook(inputBookName);

        verify(actualPrintStatement).println("Sorry, that book is not available");
    }

    @Test
    public void testUserCanReturnBookByChangingTheStateOfBookToFalse(){
        String inputBookName = "Free Software, Free Society";
        Book book = bookList.getBook(inputBookName);
        book.checkOut();
        bibliotecaApp.returnBook(inputBookName);

        assertFalse(book.isOnLeased);
    }

    @Test
    public void testNotifyUserOnSuccessfulReturnIfBookExistsAndIsOnLeased(){
        PrintStream actualPrintStatement = mock(PrintStream.class);
        System.setOut(actualPrintStatement);

        String bookName = "Free Software, Free Society";
        Book book = bookList.getBook(bookName);
        book.checkOut();
        bibliotecaApp.returnBook(bookName);

        verify(actualPrintStatement).println("Thank you for returning the book");
    }

    @Test
    public void testNotifyFailReturnIfBookDoestNotExists(){
        PrintStream actualPrintStatement = mock(PrintStream.class);
        System.setOut(actualPrintStatement);

        String bookName = "Free";

        bibliotecaApp.returnBook(bookName);

        verify(actualPrintStatement).println("This is not a valid book to return");
    }

    @Test
    public void testNotifyFailReturnIfBookExistsAndIsNotOnLeased(){
        PrintStream actualPrintStatement = mock(PrintStream.class);
        System.setOut(actualPrintStatement);

        String bookName = "Free Software, Free Society";
        Book book = bookList.getBook(bookName);
        bibliotecaApp.returnBook(bookName);

        verify(actualPrintStatement).println("This is not a valid book to return");
    }



}
