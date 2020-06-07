import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Search {

    @FXML
    private AnchorPane searchPane;

    @FXML
    private TextField bookTitleTextField;

    @FXML
    private Button searchButton;

    @FXML
    private RadioButton authorNameToggle;

    @FXML
    private RadioButton publisherNameToggle;

    @FXML
    private TextField authorNameTextField;

    @FXML
    private TextField publisherNameTextField;

    @FXML
    private ListView<?> resultListView;

    public void searchAction(ActionEvent actionEvent) {

    }

    public void closeAction(MouseEvent mouseEvent) {

    }
}
