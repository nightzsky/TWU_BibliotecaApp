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
    List<Book> bookList;
    String[] menuOption;

    @Before
    public void setUp(){
        bookList = Arrays.asList(new Book("Close to the Machine", "Ellen Ullman", 1997),
                new Book("The Art of Unix Programming", "Eric Raymond", 2003),
                new Book("Free Software, Free Society", "Richard M. Stallman", 2002));
        menuOption = new String[]{"List of Books"};
        bibliotecaApp = new BibliotecaApp(bookList, menuOption);
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
        for (int i=0; i < bookList.size(); i++){
            verify(actualPrintBookMenu).printf(listFormat, bookList.get(i).title, bookList.get(i).author, bookList.get(i).publicationYear);
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
        for (int i=0; i < bookList.size(); i++) {
            verify(actualResponseMessage).printf(listFormat, bookList.get(i).title, bookList.get(i).author, bookList.get(i).publicationYear);
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






}
