import java.util.HashMap;
import java.util.Map;

public class DataHandler {

    private static Map<String, Book> books = new HashMap<>();
    public static String title;  // This variable is the title of the chosen book (to view) from all books screen (or want to read reading etc.) or from a shelf
    public static String titleSearched;  // This variable is the title of the chosen book from search result listview
    public static String shelfName;  //  This variable is the name of the chosen shelf to view

    public static Map<String, Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        books.put(book.getTitle(), book);
    }

    public void removeBook(String bookTitle) {
        books.remove(books.get(bookTitle));
    }
}
