import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MyBooksController {

    @FXML
    private AnchorPane allBooksPane;

    @FXML
    private AnchorPane wantToReadPane;

    @FXML
    private AnchorPane readingPane;

    @FXML
    private AnchorPane readPane;

    @FXML
    private Label totalAllBooksCounter;

    @FXML
    private Label wantToReadCounter;

    @FXML
    private Label readingCounter;

    @FXML
    private Label readCounter;

    public void closeAction(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void allBooksAction(MouseEvent mouseEvent) throws IOException {
        AllBooks.initializeAllBooksScreen();
    }

    public void wantToReadAction(MouseEvent mouseEvent) {

    }

    public void readingAction(MouseEvent mouseEvent) {

    }

    public void readAction(MouseEvent mouseEvent) {

    }

    public void addNewBookAction(ActionEvent actionEvent) throws IOException {
        AddNewBook.initializeAddNewNoteScreen();
    }
}
