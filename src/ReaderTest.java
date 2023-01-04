/**
 * Name: Michael Cervantes
 * Date: 31 Oct 2022
 * Explanation: Reader testing getters and setters,
 *  constructors, and methods.
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReaderTest {

    int cardNumber = 12345;
    String name = "John Doe";
    String phone = "555-555-0000";
    Book book1;
    Book book2;
    Book book3;
    List<Book> readerBooks;
    List<Book> readerBooks2;
    Reader reader;
    //Reader reader2;

    @BeforeEach
    void setUp() {
        System.out.println("Processing setUp");

        //Books setup
        book1 = new Book("123", "Papers", "Business", 100, "Michael Scott", LocalDate.now());
        book2 = new Book("555", "Throwing A Garden Party", "Etiquette", 250,
                "James Trickington", LocalDate.now());
        book3 = new Book("7862", "Off the Grid", "Survival", 1050,
                "Creed Bratton", LocalDate.now());

        //Book List
        readerBooks = new ArrayList<>();
        readerBooks.add(book1);
        //readerBooks2 = new ArrayList<>();
        //readerBooks2.add(book2);

        //reader2 = new Reader(343443, "Mike Wazowski", "555-333-4545");
        //reader2.setBooks(new ArrayList<>());

        //setup reader
        reader = new Reader(cardNumber, name, phone);

        //Check ToString
        //reader.addBook(book1);
        //reader.addBook(book3);
        //System.out.println(reader.toString());

    }

    @AfterEach
    void tearDown() {
        System.out.println("Processing tearDown");

        cardNumber = Integer.parseInt(null);
        name = null;
        phone = null;
        book1 = null;
        book2 = null;
        book3 = null;
        readerBooks = null;
        readerBooks2 = null;
        reader = null;
    }

    @Test
    void getCardNumber() {
        assertEquals(cardNumber, reader.getCardNumber());
    }

    @Test
    void setCardNumber() {
        assertEquals(cardNumber, reader.getCardNumber());
        reader.setCardNumber(9090909);
        assertNotEquals(cardNumber, reader.getCardNumber());
    }

    @Test
    void getName() {
        assertEquals(name, reader.getName());
    }

    @Test
    void setName() {
        assertEquals(name, reader.getName());
        reader.setName("Mike Tyson");
        assertNotEquals(name, reader.getName());
    }

    @Test
    void getPhone() {
        assertEquals(phone, reader.getPhone());
    }

    @Test
    void setPhone() {
        assertEquals(phone, reader.getPhone());
        reader.setPhone("555-888-1111");
        assertNotEquals(phone,reader.getPhone());
    }

    @Test
    void getBooks() {
        reader.setBooks(readerBooks);
        assertEquals(readerBooks, reader.getBooks());
    }

    @Test
    void setBooks() {
        reader.setBooks(readerBooks);
        assertEquals(readerBooks, reader.getBooks());
        reader.setBooks(readerBooks2);
        assertNotEquals(readerBooks, reader.getBooks());
    }


    // Test methods
    @Test
    void addBook() {
        assertEquals(reader.addBook(book3), Code.SUCCESS);
        assertNotEquals(reader.addBook(book3), Code.SUCCESS);
        assertEquals(reader.addBook(book3), Code.BOOK_ALREADY_CHECKED_OUT_ERROR);
    }

    @Test
    void removeBook() {
        assertEquals(reader.addBook(book2), Code.SUCCESS);
        assertEquals(reader.removeBook(book2), Code.SUCCESS);
        assertEquals(reader.removeBook(book2), Code.READER_DOESNT_HAVE_BOOK_ERROR);

    }

    @Test
    void hasBook() {
//        assertFalse(reader2.hasBook(book2));
//        reader2.addBook(book2);
//        assertTrue(reader2.hasBook(book2));
        assertFalse(reader.hasBook(book2));
        reader.addBook(book2);
        assertTrue(reader.hasBook(book2));

    }

    @Test
    void getBookCount() {
//        assertEquals(0, reader2.getBookCount());
//        reader2.setBooks(readerBooks);
//        assertEquals(1, reader2.getBookCount());
//        reader2.removeBook(book1);
//        assertEquals(0, reader2.getBookCount());
        assertEquals(0, reader.getBookCount());
        reader.setBooks(readerBooks);
        assertEquals(1, reader.getBookCount());
        reader.removeBook(book1);
        assertEquals(0, reader.getBookCount());
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }

    @Test
    void testToString() {
    }
}