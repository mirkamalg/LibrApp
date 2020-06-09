import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyBooksController implements Initializable {

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

    public void wantToReadAction(MouseEvent mouseEvent) throws IOException {
        WantToRead.initializeWantToReadScreen();
    }

    public void readingAction(MouseEvent mouseEvent) {

    }

    public void readAction(MouseEvent mouseEvent) {

    }

    public void addNewBookAction(ActionEvent actionEvent) throws IOException {
        AddNewBook.initializeAddNewNoteScreen();
    }

    private void bindToTime() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new EventHandler<ActionEvent>() {
                            @Override public void handle(ActionEvent actionEvent) {
                                totalAllBooksCounter.setText("Total: " + DataHandler.getBooks().size());  // Total counter updater

                                wantToReadCounter.setText("Total: " + (int) DataHandler.getBooks().values().stream()
                                        .filter(book -> book.getStatus().equals("wantToRead")).count());

                                readingCounter.setText("Total: " + (int) DataHandler.getBooks().values().stream()
                                        .filter(book -> book.getStatus().equals("reading")).count());

                                readCounter.setText("Total: " + (int) DataHandler.getBooks().values().stream()
                                        .filter(book -> book.getStatus().equals("haveRead")).count());
                            }
                        }
                ),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindToTime();
    }
}
