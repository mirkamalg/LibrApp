import java.io.*;
import java.sql.*;
import java.util.Map;

public class DataBase {

    private static Connection con;
    private static boolean hasData = false;

    public ResultSet getBooksResultSet() throws SQLException, ClassNotFoundException, FileNotFoundException {
        if (con == null) {
            getConnection();
        }

        assert con != null;
        Statement state = con.createStatement();
        state.setFetchSize(15);
        return state.executeQuery("SELECT * FROM books");
    }

    private static void getConnection() throws ClassNotFoundException, SQLException, FileNotFoundException {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:Books.db");
        initialise();
    }

    private static void initialise() throws SQLException, FileNotFoundException {
        if (!hasData) {
            hasData = true;

            Statement state = con.createStatement();
            ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name = 'books'");

            if (!res.next()) {
                System.out.println("Creating and prepopulating");
                Statement state2 = con.createStatement();
                state2.execute("CREATE TABLE books(id integer,"
                        + "googleID varchar(15)," + "title text," + "publisher text,"
                        + "publishDate text," + "description text," + "language text,"
                        + "googleBooksInfoURL text," + "pageCount integer," + "averageRating double,"
                        + "hasMatureContent integer," + "authors text," + "categories text,"
                        + "images text," + "status text," + "image blob," + "primary key(id));");

                PreparedStatement prep = con.prepareStatement("INSERT INTO books values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
                prep.setString(2, "none");
                prep.setString(3, "Book");
                prep.setString(4, "Publisher");
                prep.setString(5, "2020");
                prep.setString(6, "Desc");
                prep.setString(7, "EN");
                prep.setString(8, "URL");
                prep.setString(9, "150");
                prep.setString(10, "4");
                prep.setString(11, "0");
                prep.setString(12, "author1");
                prep.setString(13, "adventure");
                prep.setString(14, "user=nicePic");
                prep.setString(15, "wantToRead");

                FileInputStream stream = new FileInputStream(new File("Pics/question-mark.png"));
                prep.setBlob(16, stream);

                prep.execute();
            }
        }
    }

    public static void addBook(String googleID, String title, String publisher, String publishDate, String description, String language, String googleBooksInfoURL, int pageCount, double averageRating, int hasMatureContent, String[] authors, String categories, Map<String, String> images, String status, File coverImage) throws SQLException, ClassNotFoundException, FileNotFoundException {
        if (con == null) {
            getConnection();
        }
        PreparedStatement prep = con.prepareStatement("INSERT INTO books values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        prep.setString(2, googleID);
        prep.setString(3, title);
        prep.setString(4, publisher);
        prep.setString(5, publishDate);
        prep.setString(6, description);
        prep.setString(7, language);
        prep.setString(8, googleBooksInfoURL);
        prep.setString(9, String.valueOf(pageCount));
        prep.setString(10, String.valueOf(averageRating));
        prep.setString(11, String.valueOf(hasMatureContent));
        prep.setString(12, String.join("~~~", authors));
        prep.setString(13, String.join("~~~", categories));

        StringBuilder builder = new StringBuilder();
        images.forEach((key, value) -> {
            builder.append(key).append("=").append(value).append(" ");
        });

        prep.setString(14, builder.toString());
        prep.setString(15, status);
        prep.setBytes(16, toByteArray(coverImage));
        prep.execute();
    }

    public static void deleteBook(String deletedBookName) throws SQLException, ClassNotFoundException, FileNotFoundException {
        if (con == null) {
            getConnection();
        }
        PreparedStatement prep = con.prepareStatement("DELETE FROM books WHERE title=?");

        prep.setString(1, deletedBookName);
        prep.executeUpdate();
    }

    private static byte[] toByteArray(File file) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            byteArrayOutputStream = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1;) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return byteArrayOutputStream != null ? byteArrayOutputStream.toByteArray() : null;
    }

    public static void closeConnection() throws SQLException {
        con.close();
    }
}
