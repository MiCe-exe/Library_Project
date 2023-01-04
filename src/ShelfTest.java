/**
 * Name: Michael Cervantes
 * Date: 8 November 2022
 * Explanation: Testing the Shelf object see if it stores books correctly.
 */
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ShelfTest {
    public Shelf s2 = new Shelf(1, "History");
    public Book b1 = new Book("232-1212d", "The Longest Title",
            "Fiction", 100, "Sir McBoatFace", LocalDate.now());
    public Book b2 = new Book("123-7726y", "World War 2", "History", 1000,
            "Rick Never", LocalDate.now());
    public Book b3 = new Book("002-6432t", "The shot heard Around the world", "History",
            730, "Sam G. Reader", LocalDate.now());

    Code results;

    @BeforeEach
    void setUp() {
        System.out.println("Processing setUp");

    }

    @AfterEach
    void tearDown() {
        System.out.println("Processing tearDown");

    }

    @Test
    void ShelfTest() {
        Shelf s1 = null;
        assertNull(s1);
        s1 = new Shelf(1, "History");
        assertNotNull(s1);
    }

    @Test
    void addBook() {
        results = s2.addBook(b2);
        assertEquals(Code.SUCCESS, results);
        assertEquals(1, s2.getBookCount(b2));
        s2.addBook(b2);
        assertEquals(2, s2.getBookCount(b2));
        results = s2.addBook(b1);
        assertEquals(Code.SHELF_SUBJECT_MISMATCH_ERROR, results);

    }

    @Test
    void getBookCount() {
        Random rnd = new Random();
        int num = rnd.nextInt(10);

        for(int i = 0; i < num; i++){
            s2.addBook(b3);
        }
        assertEquals(s2.getBookCount(b3), num);
        s2.removeBook(b3);
        num -= 1;
        assertEquals(s2.getBookCount(b3), num);

        for(int i = 0; i < num; i++){
            s2.removeBook(b3);
        }
        num = 0;
        assertEquals(s2.getBookCount(b3), 0);
        assertEquals(s2.getBookCount(b1), -1);
    }

    @Test
    void removeBook() {
        results =  s2.removeBook(b1);
        assertEquals(Code.BOOK_NOT_IN_INVENTORY_ERROR, results);
        //s2.addBook(b2);
        assertEquals(Code.SUCCESS, s2.addBook(b2));
        assertEquals(1, s2.getBookCount(b2));
        s2.addBook(b2);
        s2.addBook(b2);
        assertEquals(3,s2.getBookCount(b2));
        for(int i = 0; i < 3; i++){
            s2.removeBook(b2);
        }
        assertEquals(0, s2.getBookCount(b2));

    }

    @Test
    void listBooks() {
        String testStr = "";
        s2 = new Shelf(99, "History");
        s2.addBook(b2);
        testStr = "1 books on shelf: 99 History\nWorld War 2 by Rick Never ISBN: 123-7726y\n";
        assertEquals(testStr, s2.listBooks());
    }

    @Test
    void getShelfNumber() {
        assertEquals(1, s2.getShelfNumber());
    }

    @Test
    void setShelfNumber() {
        assertEquals(1, s2.getShelfNumber());
        s2.setShelfNumber(404);
        assertNotEquals(1, s2.getShelfNumber());
    }

    @Test
    void getSubject() {
        assertEquals("History", s2.getSubject());
    }

    @Test
    void setSubject() {
        assertEquals("History", s2.getSubject());
        s2.setSubject("Fiction");
        assertNotEquals("History", s2.getSubject());
    }

    @Test
    void getBooks() {
        HashMap<Book, Integer> m = new HashMap<>();
        m.put(b2, 1);

        s2 = new Shelf(1, "History");
        s2.addBook(b2);

        assertEquals(m, s2.getBooks());

    }

    @Test
    void setBooks() {
        HashMap<Book, Integer> m = new HashMap<>();
        m.put(b2, 1);

        s2 = new Shelf(1, "History");
        s2.setBooks(m);

        assertEquals(m, s2.getBooks());
    }

    @Test
    void testEquals() {
        Shelf s1 = new Shelf(1, "History");
        Shelf s2 = new Shelf(1, "History");

        assertEquals(s1, s2);
    }

    @Test
    void testHashCode() {
    }

    @Test
    void testToString() {
        s2 = new Shelf(1, "History");
        assertEquals("1 : History", s2.toString());
    }
}