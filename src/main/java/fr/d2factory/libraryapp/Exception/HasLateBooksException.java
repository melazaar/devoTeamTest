package fr.d2factory.libraryapp.Exception;

/**
 * This exception is thrown when a member who owns late books tries to borrow another book
 */
public class HasLateBooksException extends Exception {

    public HasLateBooksException(String message) {
        super(message);
    }
}
