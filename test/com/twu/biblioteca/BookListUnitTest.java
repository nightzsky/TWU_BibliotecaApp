package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class BookListUnitTest {
    List<Book> listOfBooks;
    BookList bookList;

    @Before
    public void setUp(){
        listOfBooks= Arrays.asList(new Book("Close to the Machine", "Ellen Ullman", 1997),
                new Book("The Art of Unix Programming", "Eric Raymond", 2003),
                new Book("Free Software, Free Society", "Richard M. Stallman", 2002));
        bookList = new BookList(listOfBooks);
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

}
