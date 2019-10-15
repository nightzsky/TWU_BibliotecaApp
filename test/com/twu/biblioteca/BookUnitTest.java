package com.twu.biblioteca;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BookUnitTest {
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
