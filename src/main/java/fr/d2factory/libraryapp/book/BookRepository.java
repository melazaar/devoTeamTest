package fr.d2factory.libraryapp.book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The book repository emulates a database via 2 HashMaps
 */
public class BookRepository {

    private  Map<ISBN, Book> availableBooks = new HashMap<>();

    /**
     * Add list book in Map
     * @param books the list of book
     */
    public void addBooks(List<Book> books) {
        availableBooks =  books.stream().collect(Collectors.toMap(Book::getIsbn,book->book));
    }

    /**
     * search a book by isbn
     * @param isbnCode the id book
     * @return Book
     */
    public Book findBook(ISBN isbnCode) {
        return availableBooks.get(isbnCode);
    }

    /**
     * @return Map of available books
     */
    public Map<ISBN, Book> getAvailableBooks() {
        return availableBooks;
    }


}
