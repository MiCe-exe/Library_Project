/**
 * Name: Michael Cervantes
 * Date: 8 November 2022
 * Explanation: Shelf object that keeps track of books .
 * Updated: 29 November 2022
 */
import java.util.HashMap;
import java.util.Map;

public class Shelf {
    public static final int SHELF_NUMBER = 0;
    public static final int SUBJECT_ = 1;

    private int shelfNumber;
    private String subject;
    private HashMap<Book, Integer> books;

    public Shelf(int shelfNumber, String subject) {
        this.shelfNumber = shelfNumber;
        this.subject = subject;
        this.books = new HashMap<>();
    }

    public Code addBook(Book book){
//        -Adds the parameter 'book' to the HashMap of books stored on the shelf

//        -If the book already exists in the HashMap, the count should be incremented,
//        and a SUCCESS code should be returned.

//        -If the book does NOT exist on the shelf, and the subject matches, add the
//        book to the shelf, set the count of the book to 1, and return a SUCCESS Code.

//        -If the book does NOT exist on the shelf, and the subject DOES NOT match, return
//        a SHELF_SUBJECT_MISMATCH_ERROR Code.

//        If the book is successfully added, print a message that displays the toString for the Book, concatenated with " added to shelf " concatenated with the toString of the current Shelf.

        if(this.books.containsKey(book)){
            this.books.put(book,this.books.get(book) + 1);
            System.out.println(book.toString() + " added to shelf " + this.toString());
            return Code.SUCCESS;
        } else{
            if(book.getSubject().equals(subject)) // FIX. OLD: if(book.getSubject() == this.subject)
            {
                this.books.put(book, 1);
                System.out.println(book.toString() + " added to shelf " + this.toString());
                return Code.SUCCESS;
            } else {
                return Code.SHELF_SUBJECT_MISMATCH_ERROR;
            }
        }
    }

    public int getBookCount(Book book){
        // This returns the count of the book parameter stored('HashMap<Book, Integer> books') on the shelf.
        // If the book is not stored on the shelf it should return a -1

        if(this.books.containsKey(book)){
            return this.books.get(book);
        }

        return -1;
    }

    public Code removeBook(Book book){
//        If the parameter book is not present in the books hashMap, return a BOOK_NOT_IN_INVENTORY_ERROR
//        Code and print a message like
//        Hitchhikers Guide To the Galaxy is not on shelf sci-fi
//
//        If the parameter book IS present in the books hashMap but has a count of 0, return a
//        BOOK_NOT_IN_INVENTORY_ERROR Code and print a message like
//        No copies of Hitchhikers Guide To the Galaxy remain on shelf sci-fi
//
//        If the parameter book is in the books hashMap and the count is greater than 0 decrement the count of books in the hashMap, return a SUCCESS Code and print a message like
//        Hitchhikers Guide To the Galaxy successfully removed from shelf sci-fi

        if(this.books.containsKey(book) == false) {
            System.out.println(book.getTitle() + " is not on the shelf " + this.subject);
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }

        if(this.books.containsKey(book) && this.books.get(book) == 0){
            System.out.println("No copies of " + book.getTitle() + " remain on the shelf " + this.subject);
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        } else {
            this.books.put(book, this.books.get(book) - 1);
            System.out.println(book.getTitle() + " Successfully removed from shelf " + this.subject);
            return Code.SUCCESS;
        }

    }
    public String listBooks(){
//        Returns a String listing all of the books on the shelf. The listing of books should match the following  (Each of the following is a separate shelf):
//
//        2 books on shelf: 2 : education
//        Headfirst Java by Grady Booch ISBN:1337 2
//
//        1 book on shelf: 3 : Adventure
//        Count of Monte Cristo by Alexandre Dumas ISBN:5297 1
//
//        3 books on shelf: 1 : sci-fi
//        Hitchhikers Guide To the Galaxy by Douglas Adams ISBN:42-w-87 2
//        Dune by Frank Herbert ISBN:34-w-34 1
        StringBuilder str = new StringBuilder();

        for(Map.Entry<Book, Integer> b : books.entrySet()){
            // books.getValue + " books on the shelf: " + this.shelfNumber + " " + this.subjecct + "\n"
            str = new StringBuilder();

            str.append(b.getValue());
            str.append(" books on shelf: " + this.shelfNumber + " " + this.subject + "\n");
            // book.title + " by " + book.Author + " ISBN: " + book.getISBN
            str.append(b.getKey().getTitle() + " by " + b.getKey().getAuthor() + " ISBN: " +
                    b.getKey().getIsbn() + "\n");
        }

        return str.toString();
    }

    public int getShelfNumber() {
        return shelfNumber;
    }

    public void setShelfNumber(int shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public HashMap<Book, Integer> getBooks() {
        return books;
    }

    public void setBooks(HashMap<Book, Integer> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shelf shelf = (Shelf) o;

        if (getShelfNumber() != shelf.getShelfNumber()) return false;
        return getSubject() != null ? getSubject().equals(shelf.getSubject()) : shelf.getSubject() == null;
    }

    @Override
    public int hashCode() {
        int result = getShelfNumber();
        result = 31 * result + (getSubject() != null ? getSubject().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        // toString()
        //Returns a string that looks like:
        //2 : education
        // Where 2 is the shelfNumber field and  education is the subject.
        return shelfNumber + " : " + subject;
    }
}
