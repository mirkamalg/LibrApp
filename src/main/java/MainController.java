import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private FontAwesomeIconView myBooksIcon;

    @FXML
    private Label myBooksLabel;

    @FXML
    private FontAwesomeIconView bookshelvesIcon;

    @FXML
    private Label bookshelvesLabel;

    @FXML
    private FontAwesomeIconView searchIcon;

    @FXML
    private Label searchLabel;

    @FXML
    private FontAwesomeIconView aboutIcon;

    @FXML
    private Label aboutLabel;

    @FXML
    private Pane contentPane;

    @FXML
    private HBox myBooksHBox;

    @FXML
    private HBox bookShelvesHBox;

    @FXML
    private HBox searchHBox;

    @FXML
    private HBox aboutHBox;

    private final TransitionHandler handler = new TransitionHandler();

    public void myBooksAction(MouseEvent mouseEvent) {
        Pane loadedPane = handler.changePane("MyBooksScreen");
        contentPane.getChildren().clear();
        contentPane.getChildren().add(loadedPane);

        myBooksLabel.setStyle("-fx-text-fill:  white");  //  Colorize chosen one, reset others
        myBooksIcon.setStyle("-fx-fill:  white");
        myBooksHBox.setStyle("-fx-background-color: #26c6da");

        bookshelvesLabel.setStyle("");
        bookshelvesIcon.setStyle("");
        bookShelvesHBox.setStyle("");

        searchLabel.setStyle("");
        searchIcon.setStyle("");
        searchHBox.setStyle("");

        aboutLabel.setStyle("");
        aboutIcon.setStyle("");
        aboutHBox.setStyle("");
    }

    public void bookshelvesAction(MouseEvent mouseEvent) {
        Pane loadedPane = handler.changePane("BookshelvesScreen");
        contentPane.getChildren().clear();
        contentPane.getChildren().add(loadedPane);

        bookshelvesLabel.setStyle("-fx-text-fill: white");  //  Colorize chosen one, reset others
        bookshelvesIcon.setStyle("-fx-fill:  white");
        bookShelvesHBox.setStyle("-fx-background-color: #26c6da");

        myBooksLabel.setStyle("");
        myBooksIcon.setStyle("");
        myBooksHBox.setStyle("");

        searchLabel.setStyle("");
        searchIcon.setStyle("");
        searchHBox.setStyle("");

        aboutLabel.setStyle("");
        aboutIcon.setStyle("");
        aboutHBox.setStyle("");
    }

    public void searchAction(MouseEvent mouseEvent) {
        Pane loadedPane = handler.changePane("SearchScreen");
        contentPane.getChildren().clear();
        contentPane.getChildren().add(loadedPane);

        searchLabel.setStyle("-fx-text-fill:  white");  //  Colorize chosen one, reset others
        searchIcon.setStyle("-fx-fill:  white");
        searchHBox.setStyle("-fx-background-color: #26c6da");

        bookshelvesLabel.setStyle("");
        bookshelvesIcon.setStyle("");
        bookShelvesHBox.setStyle("");

        myBooksLabel.setStyle("");
        myBooksIcon.setStyle("");
        myBooksHBox.setStyle("");

        aboutLabel.setStyle("");
        aboutIcon.setStyle("");
        aboutHBox.setStyle("");
    }

    public void aboutAction(MouseEvent mouseEvent) {
        Pane loadedPane = handler.changePane("AboutScreen");
        contentPane.getChildren().clear();
        contentPane.getChildren().add(loadedPane);

        aboutLabel.setStyle("-fx-text-fill:  white");  //  Colorize chosen one, reset others
        aboutIcon.setStyle("-fx-fill:  white");
        aboutHBox.setStyle("-fx-background-color: #26c6da");

        bookshelvesLabel.setStyle("");
        bookshelvesIcon.setStyle("");
        bookShelvesHBox.setStyle("");

        myBooksLabel.setStyle("");
        myBooksIcon.setStyle("");
        myBooksHBox.setStyle("");

        searchLabel.setStyle("");
        searchIcon.setStyle("");
        searchHBox.setStyle("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {  //  Clicks automatically on my books when app runs
        Event.fireEvent(myBooksLabel, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
                0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                true, true, true, true, true, true, null));

        DataBase dataBase = new DataBase();  //Load book data from SQLite database
        ResultSet rs;

        try {
            rs = dataBase.getBooksResultSet();
            while (rs.next()) {

                String mapString = rs.getString("images");
                String[] pairs = mapString.split(" ");

                Map<String, String> map = new HashMap<>();
                for (String pair:pairs) {
                    String[] keyValue = pair.split("=");
                    map.put(keyValue[0], keyValue[1]);
                }

                byte[] buffer = rs.getBytes("image");
                Image image = new Image(new ByteArrayInputStream(buffer));

                DataHandler.getBooks().put(rs.getString("title"), new Book(rs.getString("googleID"), rs.getString("title"),
                        rs.getString("publisher"), rs.getString("publishDate"), rs.getString("description"),
                        rs.getString("language"), rs.getString("googleBooksInfoURL"), rs.getInt("pageCount"),
                        rs.getDouble("averageRating"), rs.getInt("hasMatureContent"), rs.getString("authors").split("~~~"),
                        rs.getString("categories"), map, rs.getString("status"), image));
            }
        } catch (SQLException | ClassNotFoundException | IOException | NullPointerException throwables) {
            throwables.printStackTrace();
        }

        //  Clear "Thumbnails" folder

        try {
            clearDirectory(new File("Thumbnails\\"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //  Create "Thumbnails" folder if it doesn't exist

        if (!Files.exists(Paths.get("Thumbnails\\"))) {
            new File("Thumbnails\\").mkdir();
        }
    }

    private void clearDirectory(File f) throws FileNotFoundException {
        if (f.isDirectory()) {
            for (File c : f.listFiles())
                clearDirectory(c);
        }
        if (!f.delete())
            throw new FileNotFoundException("Failed to delete file: " + f);
    }
}
