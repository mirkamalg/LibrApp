import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AddNewBook {

    @FXML
    private VBox detailsVBox;

    @FXML
    private ImageView coverImageView;

    @FXML
    private TextField bookNameTextField;

    @FXML
    private TextField authorNameTextField1;

    @FXML
    private TextField authorNameTextField11;

    @FXML
    private TextField pageCountTextField;

    @FXML
    private CheckBox detailsCheckBox;

    @FXML
    private TextField publisherTextField;

    @FXML
    private TextField publishDateTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField languageTextField;

    @FXML
    private TextField categoriesTextField;

    @FXML
    private CheckBox matureContentCheckBox;

    @FXML
    private Button addButton;

    static Stage stage;

    public static void initializeAddNewNoteScreen() throws IOException {
        stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Add book");
        stage.setResizable(false);

        Parent parent = FXMLLoader.load(AddNewBook.class.getResource("AddNewBooksScreen.fxml"));
        Scene addBookScene = new Scene(parent, 900, 567);

        stage.setScene(addBookScene);
        stage.showAndWait();
    }

    public void addAction(ActionEvent actionEvent) {

    }

    public void closeAction(MouseEvent mouseEvent) {
        stage.close();
    }

    public void detailsAction(ActionEvent actionEvent) {
        detailsVBox.setDisable(!detailsCheckBox.isSelected());
    }
}
