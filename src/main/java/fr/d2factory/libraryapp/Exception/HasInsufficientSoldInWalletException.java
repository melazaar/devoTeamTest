package fr.d2factory.libraryapp.Exception;
/**
 * This exception is thrown when a member has no sold in his wallet
 */
public class HasInsufficientSoldInWalletException extends Exception {
    public HasInsufficientSoldInWalletException(String message) {
        super(message);
    }
}
