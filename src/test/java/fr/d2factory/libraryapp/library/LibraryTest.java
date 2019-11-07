package fr.d2factory.libraryapp.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.d2factory.libraryapp.Exception.HasLateBooksException;
import fr.d2factory.libraryapp.Exception.HasInsufficientSoldInWalletException;
import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.book.ISBN;
import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.member.Resident;
import fr.d2factory.libraryapp.member.Student;
import fr.d2factory.libraryapp.member.Year;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class LibraryTest {

    private static Logger logger = Logger.getAnonymousLogger();
    private Library library;
    private BookRepository bookRepository;

    @Before
    public void setup() {
        bookRepository = new BookRepository();
        library = new LibraryImpl(bookRepository);
        bookRepository.addBooks(convertJsonBooksFileToList());
    }

    @Test
    public void member_can_borrow_a_book_if_book_is_available() throws HasLateBooksException {

        // check the size of initial book list
        assertEquals(4, bookRepository.getAvailableBooks().size());
        ISBN harryPotterIsbn = new ISBN(46578964513L);

        //check that the book is available
        Book book = bookRepository.findBook(harryPotterIsbn);
        assertEquals(book.getIsbn(), harryPotterIsbn);

        //check that a Member has no borrowed book
        Member student = new Student();
        assertEquals(0, student.getBorrowBooks().size());

        //borrow book
        library.borrowBook(46578964513L, student, LocalDate.now());
        //chekc list of borrow book of Member is not empty
        assertTrue(!student.getBorrowBooks().isEmpty());
        assertEquals(harryPotterIsbn, student.getBorrowBooks().get(0).getIsbn());
        assertEquals("Harry Potter", student.getBorrowBooks().get(0).getTitle());


    }

    @Test
    public void borrowed_book_is_no_longer_available() throws HasLateBooksException {

        ISBN harryPotterIsbn = new ISBN(46578964513L);
        Member student = new Student();

        //borrow book
        library.borrowBook(46578964513L, student, LocalDate.now());
        //check that the book is no longer available
        Book book = bookRepository.findBook(harryPotterIsbn);
        assertNull(book);
        assertEquals(3, bookRepository.getAvailableBooks().size());
    }

    @Test
    public void residents_are_taxed_10cents_for_each_day_they_keep_a_book() throws HasInsufficientSoldInWalletException {

        Resident resident = new Resident();
        resident.setWallet(50);
        resident.payBook(10);
        assertEquals(49.00f, resident.getWallet(), 0.0f);
    }

    @Test
    public void students_pay_10_cents_the_first_30days() throws HasInsufficientSoldInWalletException {
        Student student = new Student();
        student.setWallet(50);
        student.setYear(Year.OTHER);
        student.payBook(30);
        assertEquals(47.00f, student.getWallet(), 0.0f);
    }

    @Test
    public void students_in_1st_year_are_not_taxed_for_the_first_15days_with_15_days() throws HasInsufficientSoldInWalletException {
        Student student = new Student();
        student.setWallet(50);
        student.setYear(Year.FIRST);
        student.payBook(15); // here the student will pay nothing :)
        assertEquals(50.00f, student.getWallet(), 0.0f);
    }

    @Test
    public void students_in_1st_year_are_not_taxed_for_the_first_15days_with_20_days() throws HasInsufficientSoldInWalletException {
        Student student = new Student();
        student.setWallet(50);
        student.setYear(Year.FIRST);
        student.payBook(20);// here the student will pay just 5 days
        assertEquals(49.50f, student.getWallet(), 0.0f);
    }

    @Test
    public void students_pay_15cents_for_each_day_they_keep_a_book_after_the_initial_30days() throws HasInsufficientSoldInWalletException {
        Student student = new Student();
        student.setWallet(50);
        student.setYear(Year.OTHER);
        student.payBook(32);
        assertEquals(45.2f, student.getWallet(), 0.0f);
    }

    @Test
    public void residents_pay_20cents_for_each_day_they_keep_a_book_after_the_initial_60days() throws HasInsufficientSoldInWalletException {
        Resident resident = new Resident();
        resident.setWallet(50);
        resident.payBook(61);
        assertEquals(37.8f, resident.getWallet(), 0.0f);
    }

    @Test(expected = HasLateBooksException.class)
    public void members_cannot_borrow_book_if_they_have_late_books() throws HasLateBooksException {
        List<Book> listBook = Arrays.asList(
                new Book("title_1", "author_1", new ISBN(4657L), LocalDate.of(2019, 9, 10)),//late book
                new Book("title_2", "author_2", new ISBN(4622L), LocalDate.of(2019, 11, 04)));

        Student student = new Student();
        student.setBorrowBooks(listBook);
        library.borrowBook(3326456467846L, student, LocalDate.now());

    }

    @Test(expected = HasInsufficientSoldInWalletException.class)
    public void resident_cannot_pay_book_if_he_have_no_sold_on_wallet() throws HasInsufficientSoldInWalletException {
        Resident resident = new Resident();
        resident.setWallet(0.50f);
        resident.payBook(40);
    }

    @Test(expected = HasInsufficientSoldInWalletException.class)
    public void student_cannot_pay_book_if_he_have_no_sold_on_wallet() throws HasInsufficientSoldInWalletException {
        Student student = new Student();
        student.setWallet(0.50f);
        student.setYear(Year.FIRST);
        student.payBook(40);
    }

    /**
     * Utility method that convert a json file to book list
     *
     * @return List of book
     */
    private static List<Book> convertJsonBooksFileToList() {
        List<Book> listBook = new ArrayList<>();
        try {
            ClassLoader classLoader = LibraryTest.class.getClassLoader();
            File file = new File(classLoader.getResource("books.json").getFile());
            ObjectMapper mapper = new ObjectMapper();
            listBook = Arrays.asList(mapper.readValue(file, Book[].class));
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, "An unexpected error raised when trying read books.json file", e);
        }
        return listBook;
    }

}
