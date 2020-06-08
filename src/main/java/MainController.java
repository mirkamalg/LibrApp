import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.ByteArrayInputStream;
import java.io.File;
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
    private AnchorPane mainAnchorPane;

    @FXML
    private AnchorPane navBarAnchorPane;

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

    private final TransitionHandler handler = new TransitionHandler();

    public void myBooksAction(MouseEvent mouseEvent) {
        Pane loadedPane = handler.changePane("MyBooksScreen");
        contentPane.getChildren().clear();
        contentPane.getChildren().add(loadedPane);

        myBooksLabel.setStyle("-fx-text-fill:  #29b6f6");  //  Colorize chosen one, reset others
        myBooksIcon.setStyle("-fx-fill:  #29b6f6");

        bookshelvesLabel.setStyle("");
        bookshelvesIcon.setStyle("");

        searchLabel.setStyle("");
        searchIcon.setStyle("");

        aboutLabel.setStyle("");
        aboutIcon.setStyle("");
    }

    public void bookshelvesAction(MouseEvent mouseEvent) {

    }

    public void searchAction(MouseEvent mouseEvent) {
        Pane loadedPane = handler.changePane("SearchScreen");
        contentPane.getChildren().clear();
        contentPane.getChildren().add(loadedPane);

        searchLabel.setStyle("-fx-text-fill:  #29b6f6");  //  Colorize chosen one, reset others
        searchIcon.setStyle("-fx-fill:  #29b6f6");

        bookshelvesLabel.setStyle("");
        bookshelvesIcon.setStyle("");

        myBooksLabel.setStyle("");
        myBooksIcon.setStyle("");

        aboutLabel.setStyle("");
        aboutIcon.setStyle("");
    }

    public void aboutAction(MouseEvent mouseEvent) {

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

        //  Create "Thumbnails" folder if it doesn't exist

        if (!Files.exists(Paths.get("Thumbnails\\"))) {
            new File("Thumbnails\\").mkdir();
        }
    }
}
