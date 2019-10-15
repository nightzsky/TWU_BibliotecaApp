package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;



public class BibliotecaAppUnitTest {

    BibliotecaApp bibliotecaApp;
    List<Book> listOfBooks;
    String[] menuOption;
    BookList bookList;

    @Before
    public void setUp(){
        listOfBooks= Arrays.asList(new Book("Close to the Machine", "Ellen Ullman", 1997),
                new Book("The Art of Unix Programming", "Eric Raymond", 2003),
                new Book("Free Software, Free Society", "Richard M. Stallman", 2002));
        bookList = new BookList(listOfBooks);
        menuOption = new String[]{"List of Books, Checkout Book, Return Book, Quit Application"};
        bibliotecaApp = new BibliotecaApp(bookList, menuOption);
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
            if (!listOfBooks.get(i).isCheckOut()){
                verify(actualPrintBookMenu).printf(listFormat, listOfBooks.get(i).title, listOfBooks.get(i).author, listOfBooks.get(i).publicationYear);
            }
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

    @Test
    public void testUserInputInteger(){
        int expected = 5;
        InputStream input = new ByteArrayInputStream(Integer.toString(expected).getBytes());
        System.setIn(input);

        int actual = bibliotecaApp.getUserInputInteger();

        assertEquals(actual, expected);
    }

    @Test
    public void testUserInputString(){
        String expected = "Hello";
        InputStream input = new ByteArrayInputStream(expected.getBytes());
        System.setIn(input);

        String actual = bibliotecaApp.getUserInputString();

        assertEquals(actual, expected);
    }

    @Test
    public void testQuitApplicationByChangingTheQuitStateToTrue(){
        bibliotecaApp.quitApplication();
        assertTrue(bibliotecaApp.quit);
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
        bibliotecaApp.returnBook(bookName);

        verify(actualPrintStatement).println("This is not a valid book to return");
    }

    @Test
    public void testInvalidOptionIfNumberIsOutsideRange(){
        PrintStream actualInvalidMessage = mock(PrintStream.class);
        System.setOut(actualInvalidMessage);

        bibliotecaApp.selectOption(3);

        verify(actualInvalidMessage).println("Please select a valid option!");
    }

    @Test
    public void testInvalidOptionIfNumberIsNegative(){
        PrintStream actualInvalidMessage = mock(PrintStream.class);
        System.setOut(actualInvalidMessage);

        bibliotecaApp.selectOption(-1);
        verify(actualInvalidMessage).println("Please select a valid option!");
    }

    @Test
    public void testSelectViewListOfBooksOption(){
        BibliotecaApp bibliotecaAppSpy = spy(bibliotecaApp);
        bibliotecaAppSpy.selectOption(0);
        verify(bibliotecaAppSpy).viewListOfBooks();
    }

    @Test
    public void testSelectCheckOutBookOption(){
        BibliotecaApp bibliotecaAppSpy = spy(bibliotecaApp);
        String bookName = "Free Software, Free Society";
        InputStream input = new ByteArrayInputStream(bookName.getBytes());
        System.setIn(input);
        bibliotecaAppSpy.selectOption(1);
        verify(bibliotecaAppSpy).checkOutBook(bookName);
    }

    @Test
    public void testSelectReturnBookOption(){
        BibliotecaApp bibliotecaAppSpy = spy(bibliotecaApp);
        String bookName = "Free Software, Free Society";
        InputStream input = new ByteArrayInputStream(bookName.getBytes());
        System.setIn(input);
        bibliotecaAppSpy.selectOption(2);
        verify(bibliotecaAppSpy).returnBook(bookName);
    }

    @Test
    public void testSelectQuitApplication(){
        BibliotecaApp bibliotecaAppSpy = spy(bibliotecaApp);
        bibliotecaAppSpy.selectOption(3);
        verify(bibliotecaAppSpy).quitApplication();
    }

}

