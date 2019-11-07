package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.Exception.HasInsufficientSoldInWalletException;
import fr.d2factory.libraryapp.library.Library;

import java.util.ArrayList;
import java.util.List;

/**
 * A member is a person who can borrow and return books to a {@link Library}
 * A member can be either a student or a resident
 */
public abstract class Member {

    /**
     * An initial sum of money the member has
     */
    protected float wallet;

    /**
     * list of books that a member had borrowed
     */
    private List<Book> borrowBooks;


    public float getWallet() {
        return wallet;
    }

    public void setWallet(float wallet) {
        this.wallet = wallet;
    }

    public List<Book> getBorrowBooks() {
        if (this.borrowBooks == null) {
            List<Book> newBorrowBooks = new ArrayList<>();
            this.borrowBooks = newBorrowBooks;
        }
        return this.borrowBooks;
    }

    public void setBorrowBooks(List<Book> borrowBooks) {
        this.borrowBooks = borrowBooks;
    }

    /**
     * The member should pay their books when they are returned to the library
     *
     * @param numberOfDays the number of days they kept the book
     */
    public abstract void payBook(int numberOfDays) throws HasInsufficientSoldInWalletException;


}
