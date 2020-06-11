import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Reading implements Initializable {

    static Stage stage;

    @FXML
    private ListView<String> readingListView;

    @FXML
    private Label noBookLabel;

    static void initializeReadingScreen() throws IOException {
        stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Reading");
        stage.setResizable(false);

        Parent parent = FXMLLoader.load(Reading.class.getResource("ReadingScreen.fxml"));
        Scene readingScene = new Scene(parent, 830, 655);

        stage.setScene(readingScene);
        stage.showAndWait();
    }

    public void viewBookAction(MouseEvent mouseEvent) throws IOException {
        DataHandler.title = readingListView.getSelectionModel().getSelectedItem();
        ViewBookInfo.initializeBookInfoScreen();
    }

    public void closeAction(MouseEvent mouseEvent) {
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        readingListView.setCellFactory(param -> new CustomCell());

        if (DataHandler.getBooks().values().stream().anyMatch(book -> book.getStatus().equals("reading"))) {
            DataHandler.getBooks().values().stream()         //  Getting books with value of "reading" and adding them to the listview
                    .filter(book -> book.getStatus().equals("reading"))
                    .collect(Collectors.toList()).forEach(book -> {
                readingListView.getItems().add(book.getTitle());
            });
        } else {
            readingListView.setVisible(false);
            noBookLabel.setVisible(true);
        }
    }
}
