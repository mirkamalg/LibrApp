import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class WantToRead implements Initializable {

    static Stage stage;
    @FXML
    private ListView<String> wantToReadListView;

    static void initializeWantToReadScreen() throws IOException {
        stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Want to read");
        stage.setResizable(false);

        Parent parent = FXMLLoader.load(WantToRead.class.getResource("WantToReadScreen.fxml"));
        Scene wantToReadScene = new Scene(parent, 830, 655);

        stage.setScene(wantToReadScene);
        stage.showAndWait();
    }

    public void viewBookAction(MouseEvent mouseEvent) throws IOException {
        DataHandler.title = wantToReadListView.getSelectionModel().getSelectedItem();
        ViewBookInfo.initializeBookInfoScreen();
    }

    public void closeAction(MouseEvent mouseEvent) {
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wantToReadListView.setCellFactory(param -> new CustomCell());

        DataHandler.getBooks().values().stream()         //  Getting books with value of "wantToRead" and adding them to the listview
                .filter(book -> book.getStatus().equals("wantToRead"))
                .collect(Collectors.toList()).forEach(book -> {
                    wantToReadListView.getItems().add(book.getTitle());
        });
    }
}
