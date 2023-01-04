/**
 * Name: Michael Cervantes
 * Date: 31 Oct 2022
 * Explanation: Book test assignment. Checking setters and getters,
 *  a constructor, and testing equals override.
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    String isbn = "123";
    String title = "Papers";
    String subject = "Business";
    int pageCount = 100;
    String author = "Michael Scott";
    LocalDate dueDate = LocalDate.now();
    Book book;
    Book bookOfTheYear;
    Book book3;

    @BeforeEach
    void setUp() {
        System.out.println("Processing setUp");

        book = new Book(isbn, title, subject ,pageCount, author, dueDate);
        bookOfTheYear = new Book("555", "Throwing A Garden Party", "Etiquette", 250, "James Trickington", LocalDate.now());
        book3 = new Book("555", "Throwing A Garden Party", "Etiquette", 250, "James Trickington", LocalDate.now());

    }

    @AfterEach
    void tearDown() {
        System.out.println("Processing tearDown");
        isbn = null;
        title = null;
        subject = null;
        pageCount = Integer.parseInt(null);
        author = null;
        dueDate = null;
        //book = null;
        bookOfTheYear = null;
        //book3 = null;
    }

    @Test
    void constructorTest(){
        Book book = null;
        assertNull(book);
//        book = new Book();
        book = new Book("1250135583",
                "Gangsters of Capitalism: Smedley Butler, the Marines, and the Making and Breaking of America's Empire",
                "History",
                432,
                "Jonathan M. Katz",
                LocalDate.now());

        assertNotNull(book);

    }

    @Test
    void getIsbn() {
        assertEquals(isbn, book.getIsbn());
    }

    @Test
    void setIsbn() {
        assertEquals(isbn, book.getIsbn());
        book.setIsbn("098");
        assertNotEquals(isbn, book.getIsbn());
    }

    @Test
    void getTitle() {
        assertEquals(title, book.getTitle());
    }

    @Test
    void setTitle() {
        assertEquals(title, book.getTitle());
        book.setTitle("Love of Hockey");
        assertNotEquals(title, book.getTitle());
        //title
    }

    @Test
    void getSubject() {
        assertEquals(subject, book.getSubject());
    }

    @Test
    void setSubject() {
        assertEquals(subject, book.getSubject());
        book.setSubject("Sports");
        assertNotEquals(subject, book.getSubject());
        //subject
    }

    @Test
    void getPageCount() {
        assertEquals(pageCount, book.getPageCount());
    }

    @Test
    void setPageCount() {
        assertEquals(pageCount, book.getPageCount());
        book.setPageCount(1);
        assertNotEquals(pageCount, book.getPageCount());
        //pageCount
    }

    @Test
    void getAuthor() {
        assertEquals(author, book.getAuthor());
    }

    @Test
    void setAuthor() {
        assertEquals(author, book.getAuthor());
        book.setAuthor("Creed Bratton");
        assertNotEquals(author, book.getAuthor());
        //author
    }

    @Test
    void getDueDate() {
        assertEquals(dueDate, book.getDueDate());
    }

    @Test
    void setDueDate() {
        assertEquals(isbn, book.getIsbn());
        book.setDueDate(LocalDate.parse("2022-12-05"));
        assertNotEquals(dueDate, book.getDueDate());
        //dueDate
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

    @Test
    public void bookEquals(){
        System.out.println(book);
        System.out.println(bookOfTheYear);
        System.out.println(book3);
        //check if not equal
        System.out.println("Checking if book and bookOfTheYear are equal");
        assertNotEquals(book, bookOfTheYear);
        //check if they're not equal
        System.out.println("Checking if bookOfTheYear is equal to Book3");
        assertEquals(bookOfTheYear, book3);
    }
}