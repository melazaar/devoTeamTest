package fr.d2factory.libraryapp.library;

import fr.d2factory.libraryapp.Exception.HasLateBooksException;
import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.book.ISBN;
import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.member.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static fr.d2factory.libraryapp.constant.Constant.*;
import static java.time.temporal.ChronoUnit.DAYS;

/**
 * the Library implementation.
 */
public class LibraryImpl implements Library {

    private BookRepository bookRepository;

    public LibraryImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book borrowBook(long isbnCode, Member member, LocalDate borrowedAt) throws HasLateBooksException {
        int thresholdDay = member instanceof Student ? STUDENT_THRESHOLD_DAY : RESIDENT_THRESHOLD_DAY;
        List<Book> booksMember = member.getBorrowBooks();
        //verify if Member has a book late
        if (null != booksMember && !booksMember.isEmpty() && hasLateBooks(member.getBorrowBooks(), thresholdDay)) {
            throw new HasLateBooksException(MEMBER_HAS_LATE_BOOKS);
        } else {
            //check if the  book is available
            ISBN isbn = new ISBN(isbnCode);
            Book book = bookRepository.findBook(isbn);
            if (null != book) {
                //remove the book from available
                bookRepository.getAvailableBooks().remove(book.getIsbn());
                //associate the borrowed book to Member
                book.setBorrowedAt(borrowedAt);
                if (null != booksMember) {
                    booksMember.add(book);
                } else {
                    List<Book> listBookMember = new ArrayList<>();
                    listBookMember.add(book);
                    member.setBorrowBooks(listBookMember);
                }
            }
            return book;
        }
    }

    /**
     * Method that check if member has book late
     *
     * @param listBook     the list of books borrowed by the member
     * @param thresholdDay the number of days a the must not exceed
     * @return boolean
     */
    private static boolean hasLateBooks(List<Book> listBook, int thresholdDay) {
        return !listBook.stream().filter(book -> DAYS.between(book.getBorrowedAt(), LocalDate.now()) > thresholdDay).collect(Collectors.toList()).isEmpty();
    }
}
