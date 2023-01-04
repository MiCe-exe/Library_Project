import java.util.ArrayList;
import java.util.List;

public class Reader {
    public static final int CARD_NUMBER_ = 0;
    public static final int NAME_ = 1;
    public static final int PHONE_ = 2;
    public static final int BOOK_COUNT_ = 3;
    public static final int BOOK_START_ = 4;

    private int cardNumber;
    private String name;
    private String phone;
    private List<Book> books;

    public Reader(int cardNumber, String name, String phone) {
        this.cardNumber = cardNumber;
        this.name = name;
        this.phone = phone;
        this.books = new ArrayList<>();
    }

    // Getters and Setters

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {

        this.books = books;
    }

    //Methods
    public Code addBook(Book book){
        if(hasBook(book))
        {
            return Code.BOOK_ALREADY_CHECKED_OUT_ERROR;
        }

        this.books.add(book);

        return Code.SUCCESS;
    }

    public Code removeBook(Book book){
        //check if it's there
        if (hasBook(book) == false)
        {
            return Code.READER_DOESNT_HAVE_BOOK_ERROR;
        }

        //If it is there try to remove it.
        try {
            this.books.remove(book);
            return Code.SUCCESS;

        }catch (Exception e){
            return Code.READER_COULD_NOT_REMOVE_BOOK_ERROR;
        }
    }

    public Boolean hasBook(Book book){
        if(this.books == null)
        {
            return false;
        }

        if(this.books.contains(book))
        {
            return true;
        }

        return false;
    }

    public int getBookCount(){
        if(this.books == null)
        {
            return 0;
        }

        return this.books.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reader reader = (Reader) o;

        if (getCardNumber() != reader.getCardNumber()) return false;
        if (getName() != null ? !getName().equals(reader.getName()) : reader.getName() != null) return false;
        return getPhone() != null ? getPhone().equals(reader.getPhone()) : reader.getPhone() == null;
    }

    @Override
    public int hashCode() {
        int result = getCardNumber();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        // [Name] + [Card Num] + "has check " + [Book1, Book2]
        // ex: Bob Barker (#1223) has checked out {Book1, Book2}
        String temp = this.name + " (#" + String.valueOf(this.cardNumber) + ") has checked out " ;

        for(Book b : this.books)
        {
            temp += b.getTitle() + ", ";
        }

        return temp;
    }
}
