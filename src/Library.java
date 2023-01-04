/**
 * Name: Michael Cervantes
 * Date: 22 November 2022
 * Explanation: Library.java file. Where we create an object to store all the library inventory.
 * Inventory tracking and as well as Patron.
 * Updated: 29 November 2022
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class Library {
    public static final int LENDING_LIMIT = 5;
    private String name;
    private static int libraryCard;
    private List<Reader> readers = new ArrayList<>();
    private HashMap<String, Shelf> shelves = new HashMap<>();
    private HashMap<Book, Integer> books = new HashMap<>();

    public Library(String name) {
        this.name = name;
    }

    // does everything
    public Code init(String filename){

        //setup Scanner here
        //Check if filename exist
        File f = new File(filename);
        Scanner scan = null;

        //try to make scanner
        try{
            scan = new Scanner(f);
        }catch(FileNotFoundException e){
            System.out.println("Could not find the file " + e );
            return Code.FILE_NOT_FOUND_ERROR;
        }

        //init Books
        String temp = scan.nextLine();
        int num = convertInt(temp, Code.BOOK_COUNT_ERROR);

        if(num < 0){
            //return Code.BOOK_COUNT_ERROR;
            return errorCode(num);
        }

        initBooks(num, scan);

        //Call list books
        listBooks();

        //initShelf
        temp = scan.nextLine();
        num = convertInt(temp, Code.SHELF_COUNT_ERROR);

        if(num < 0){
            return errorCode(num);
        }

        //initShelves
        initShelves(num, scan);

        //add books to shelves
        for(Map.Entry<Book, Integer> bookMap : books.entrySet()){
            int LEN = bookMap.getValue();
            for(int i = 0; i < LEN; i++){
                addBookToShelf(bookMap.getKey(), shelves.get(bookMap.getKey().getSubject()));
            }
        }

        //initReader
        temp = scan.nextLine();
        num = convertInt(temp, Code.READER_COUNT_ERROR);

        if(num < 0){
            return errorCode(num);
        }

        initReader(num, scan);

        return Code.SUCCESS;
    }

    private Code initBooks(int bookCount, Scanner scan){
        String temp;

        if(bookCount < 1){
            return Code.PAGE_COUNT_ERROR;
        }

        //get number of books first

       for(int i = 0; i < bookCount; i++){
           temp = scan.nextLine();
           String[] fields = null;
           Book newBook = null;
           fields = temp.split(",");
           int pageNum = convertInt(fields[Book.PAGE_COUNT], Code.PAGE_COUNT_ERROR);
           LocalDate date = convertDate(fields[Book.DUE_DATE_], Code.DATE_CONVERSION_ERROR);

           if(pageNum < 0){
               return Code.PAGE_COUNT_ERROR;
           }

           if(date == null){
               return Code.DATE_CONVERSION_ERROR;
           }

           newBook = new Book(fields[Book.ISBN_], fields[Book.TITLE_], fields[Book.SUBJECT_],
                   pageNum, fields[Book.AUTHOR_], date);

           if(newBook != null){
               addBook(newBook);
           }
       }
        return Code.SUCCESS;
    }

    private Code initShelves(int shelfCount, Scanner scan) {

        if(shelfCount < 1 ){
            return Code.SHELF_COUNT_ERROR;
        }

        String temp;

        for(int i = 0; i < shelfCount; i++){
            temp = scan.nextLine();
            String[] fields = null;
            fields = temp.split(",");
            int shelfNum = convertInt(fields[Shelf.SHELF_NUMBER], Code.SHELF_NUMBER_PARSE_ERROR);

            if(shelfNum < 0){
                return Code.SHELF_NUMBER_PARSE_ERROR;
            }

            Shelf newShelf = new Shelf(shelfNum, fields[Shelf.SUBJECT_]);
            addShelf(newShelf);
        }

        if(shelves.size() == shelfCount){
            return Code.SUCCESS;
        }else{
            System.out.println("Number of shelves doesn't match expected");
            return Code.SHELF_NUMBER_PARSE_ERROR;
        }
    }

    private Code initReader(int readerCount, Scanner scan) {

        if(readerCount < 0){
            return Code.READER_COUNT_ERROR;
        }

        String temp;

        for(int i = 0; i < readerCount; i++){
            temp = scan.nextLine();
            String[] fields = temp.split(",");
            int cardNum = convertInt(fields[Reader.CARD_NUMBER_], Code.READER_CARD_NUMBER_ERROR);

            if(cardNum < 0){
                return Code.READER_CARD_NUMBER_ERROR;
            }

            Reader patron = new Reader(cardNum, fields[Reader.NAME_], fields[Reader.PHONE_]);
            addReader(patron);

            int maxValue = convertInt(fields[Reader.BOOK_COUNT_], Code.READER_COUNT_ERROR);
            if(maxValue < 0){
                return Code.READER_COUNT_ERROR;
            }

            for(int k = Reader.BOOK_START_; k < fields.length; k += 2){
                Book patronBook = getBookByISBN(fields[k]);

                if(patronBook == null){
                    System.out.println("Error");
                }else{
                    LocalDate date = convertDate(fields[k+1],Code.DATE_CONVERSION_ERROR);
                    patronBook.setDueDate(date);

                    checkOutBook(patron, patronBook);
                }
            }
        }

        return Code.SUCCESS;
    }

    public Code addBook(Book newBook) {

        if(books.containsKey(newBook)){
            books.computeIfPresent(newBook, (k,v) -> v + 1);
            System.out.println(books.get(newBook) + " copies of " + newBook.toString() + " in the stacks");
        }else{
            books.put(newBook, 1);
            System.out.println(newBook.getTitle() + " added to the stacks.");
        }

        if(shelves.containsKey(newBook.getSubject())){
            shelves.get(newBook.getSubject()).addBook(newBook);
            return Code.SUCCESS;
        }else{
            System.out.println("No shelf for " + newBook.getSubject() + " books");
            return Code.SHELF_EXISTS_ERROR;
        }
    }

    public Code returnBook(Reader reader, Book book) {

        if(!reader.hasBook(book)){
            System.out.println(reader.getName() + " doesn't have " + book.getTitle() + " checked out");
            return Code.READER_DOESNT_HAVE_BOOK_ERROR;
        }

        if(!books.containsKey(book)){
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }

        System.out.println(reader.getName() + " is returning " + book.toString());
        Code code = reader.removeBook(book);

        if(code == Code.SUCCESS){
            return returnBook(book);
        }else{
            System.out.println("Could not return " + book.toString());
            return code;
        }
    }

    public Code returnBook(Book book) {

        if(shelves.containsKey(book.getSubject())){
            return shelves.get(book.getSubject()).addBook(book);
        }else{
            System.out.println("No shelf for " + book.toString());
            return Code.SHELF_EXISTS_ERROR;
        }
    }

    private Code addBookToShelf(Book book, Shelf shelf) {

        if(returnBook(book) == Code.SUCCESS){
            return Code.SUCCESS;
        }

        if(!shelf.getSubject().equals(book.getSubject())){      //Fix
            return Code.SHELF_SUBJECT_MISMATCH_ERROR;
        }

        Code c = shelf.addBook(book);
        if(c == Code.SUCCESS){
            System.out.println(book.toString() + " added to shelf");
            return Code.SUCCESS;
        }else {
            System.out.println("Could not add " + book.toString() + " to shelf");
            return c;
        }
    }

    public int listBooks() {
        int totalBooks = 0;

        for(Map.Entry<Book, Integer> entry : books.entrySet() ){
            System.out.println(entry.getValue() + " copies of " + entry.getKey().toString());
            totalBooks += entry.getValue();
        }

        return totalBooks;
    }

    public Code checkOutBook(Reader reader, Book book) {
        if (!readers.contains(reader)){
            System.out.println(reader.getName() + " doesn't have an account here.");
            return Code.READER_NOT_IN_LIBRARY_ERROR;
        }

        if(reader.getBookCount() >= LENDING_LIMIT){
            System.out.println(reader.getName() + " has reached the lending limit, (" + LENDING_LIMIT + ")");
        }

        if(!books.containsKey(book)){
            System.out.println("Error: could not find " + book.toString());
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }

        if(!shelves.containsKey(book.getSubject())){
            System.out.println("No shelf for " + book.getSubject() + " books!");
            return Code.SHELF_EXISTS_ERROR;
        }

        if(shelves.get(book.getSubject()).getBookCount(book) < 1){                      //if(shelves.get(book.getSubject()).getBookCount(book) < 1){
            System.out.println("Error: no copies of " + book.toString() + " remain");
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }

        Code c = reader.addBook(book);
        if(c != Code.SUCCESS){
            System.out.println("Couldn't checkout " + book.toString());
            return c;
        }

        c = shelves.get(book.getSubject()).removeBook(book);
        if(c == Code.SUCCESS){
            System.out.println(book.toString() + " checked out successfully.");
        }

        return c;
    }

    public Book getBookByISBN(String isbn) {
        for(Book b : books.keySet()){
            if(b.getIsbn().equals(isbn)){            // Fix if(b.getIsbn() == isbn){
                return b;
            }
        }

        System.out.println("Error: Could not find a book with ISBN: " + isbn);
        return null;
    }

    public Code listShelves(boolean showbooks) {

        if(showbooks){
            for (Map.Entry<String, Shelf> m : shelves.entrySet()){
                m.getValue().listBooks();
            }
        }else{
            for (Map.Entry<String, Shelf> m : shelves.entrySet()){
                System.out.println(m.getValue().toString());
            }
        }

        return Code.SUCCESS;
    }

    public Code addShelf(String shelfSubject) {
        Shelf s = new Shelf(shelves.size() + 1, shelfSubject);
        //shelves.put(shelfSubject, s);
        addShelf(s);
        return Code.SUCCESS;
    }
        //private HashMap<String, Shelf> shelves;

    public Code addShelf(Shelf shelf) {

        if(shelves.containsValue(shelf)){
            System.out.println("ERROR: Shelf already exists " + shelf.toString());
            return Code.SHELF_EXISTS_ERROR;
        }

        if(shelf.getShelfNumber() > 0){
            shelves.put(shelf.getSubject(), shelf);
            return Code.SUCCESS;
        }

        shelf.setShelfNumber(shelves.size() + 1);
        shelves.put(shelf.getSubject(), shelf);

        return Code.SUCCESS;
    }

    public Shelf getShelf(Integer shelfNumber) {
        for(Map.Entry<String, Shelf> s : shelves.entrySet()){
            if(shelfNumber == s.getValue().getShelfNumber()){
                return s.getValue();
            }
        }

        System.out.println("No shelf for " + shelfNumber + " books");
        return null;
    }

    public Shelf getShelf(String subject) {

        if(shelves.containsKey(subject)){
            return shelves.get(subject);
        }else{
            System.out.println("No shelf for " + subject + " books");
            return null;
        }
    }

    public int listReaders() {
        int total = 0;
        for(Reader r : readers){
            System.out.println(r.toString());
            total++;
        }
        return total;
    }

    public int listReaders(boolean showBooks) {
        int total = 0;

        if(showBooks){
            for(Reader r : readers){
                System.out.print(r.getName() + " has the following books: [");
                for(int i = 0; i < r.getBookCount(); i++){
                    System.out.print(r.getBooks().get(i).toString() + ", ");
                }
                System.out.print("]\n");
                total++;
            }
        }else{
            for(Reader r : readers){
                System.out.println(r.toString());
                total++;
            }
        }

        return total;
    }

    public Reader getReaderByCard(int cardNumber) {

        for(Reader r : readers){
            if(r.getCardNumber() == cardNumber){
                return r;
            }
        }

        System.out.println("Could not find a reader with card #" + cardNumber);
        return null;
    }

    public Code addReader(Reader reader){

        if(readers.contains(reader)){
            System.out.println(reader.getName() +" already has an account!");
            return Code.READER_ALREADY_EXISTS_ERROR;
        }

        for(Reader r : readers){
            if(r.getCardNumber() == reader.getCardNumber()){
                System.out.println(r.getName() + " and " + reader.getName() +
                                    " have the same card number!");
                return Code.READER_CARD_NUMBER_ERROR;
            }
        }

        readers.add(reader);
        System.out.println(reader.getName() + " added to the library!");
        if(reader.getCardNumber() > libraryCard){
            libraryCard = reader.getCardNumber();
        }

        return Code.SUCCESS;
    }

    public Code removeReader(Reader reader) {

        if(reader.getBookCount() >= 1 && readers.contains(reader)){
            System.out.println(reader.getName() + " must return all books!");
            return Code.READER_STILL_HAS_BOOKS_ERROR;
        }

        if(!readers.contains(reader)){
            System.out.println(reader.getName() + " is not part of this Library.");
            return Code.READER_NOT_IN_LIBRARY_ERROR;
        }

        if(readers.contains(reader) && reader.getBookCount() == 0){
            readers.remove(reader);
            return Code.SUCCESS;
        }

        return Code.UNKNOWN_ERROR;
    }

    public static int convertInt(String recordCountString, Code code) {
        int temp = 0;

        try{
            temp = Integer.parseInt(recordCountString);
        }catch(NumberFormatException e){
            System.out.println("Value which caused the error: " + recordCountString);   //Fix typo

            if(code == Code.BOOK_COUNT_ERROR || code == Code.PAGE_COUNT_ERROR || code == Code.DATE_CONVERSION_ERROR){
                System.out.println("Error: " + code.getMessage());
            }else{
                System.out.println("Error: [" + Code.UNKNOWN_ERROR.getMessage());
            }

            return code.getCode();
        }

        return temp;
    }

    public static LocalDate convertDate(String date, Code errorCode) {
        LocalDate defaultDate = LocalDate.of(1970, Month.JANUARY, 1);

        if(date.equals("0000")){                 //Fix if(date == "0000"){
            return defaultDate;
        }

        String[] dateField = date.split("-");

        if(dateField.length < 3){
            System.out.println("Error: date conversion error, could not parse " + date);
            return defaultDate;
        }

        int year = Integer.parseInt(dateField[0]);
        int month = Integer.parseInt(dateField[1]);
        int day = Integer.parseInt(dateField[2]);

        if(year == 0 || month == 0 || day == 0){
            System.out.println("Error converting date: Year " + year);
            System.out.println("Error converting date: Month " + month);
            System.out.println("Error converting date: Day " + day);
            System.out.println("Using default date (01-jan-1970");

            return defaultDate;
        }

        return LocalDate.of(year, month, day);
    }

    public static int getLibraryCardNumber() {
        return libraryCard + 1;
    }

    private Code errorCode(int codeNumber) {

        for(Code c : Code.values()){
            if(c.getCode() == codeNumber){
                return c;
            }
        }

        return Code.UNKNOWN_ERROR;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int getLibraryCard() {
        return libraryCard + 1;
    }

    public static void setLibraryCard(int libraryCard) {
        Library.libraryCard = libraryCard;
    }

    public List<Reader> getReaders() {
        return readers;
    }

    public void setReaders(List<Reader> readers) {
        this.readers = readers;
    }

    public HashMap<String, Shelf> getShelves() {
        return shelves;
    }

    public void setShelves(HashMap<String, Shelf> shelves) {
        this.shelves = shelves;
    }

    public HashMap<Book, Integer> getBooks() {
        return books;
    }

    public void setBooks(HashMap<Book, Integer> books) {
        this.books = books;
    }
}
