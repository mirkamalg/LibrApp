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

public class HaveRead implements Initializable {

    static Stage stage;

    @FXML
    private ListView<String> haveReadListView;

    @FXML
    private Label noBookLabel;

    static void initializeHaveReadScreen() throws IOException {
        stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Have read");
        stage.setResizable(false);

        Parent parent = FXMLLoader.load(HaveRead.class.getResource("HaveReadScreen.fxml"));
        Scene haveReadScene = new Scene(parent, 830, 655);

        stage.setScene(haveReadScene);
        stage.showAndWait();
    }

    public void viewBookAction(MouseEvent mouseEvent) throws IOException {
        DataHandler.title = haveReadListView.getSelectionModel().getSelectedItem();
        ViewBookInfo.initializeBookInfoScreen();
    }

    public void closeAction(MouseEvent mouseEvent) {
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        haveReadListView.setCellFactory(param -> new CustomCell());

        if (DataHandler.getBooks().values().stream().anyMatch(book -> book.getStatus().equals("haveRead"))) {
            DataHandler.getBooks().values().stream()         //  Getting books with value of "haveRead" and adding them to the listview
                    .filter(book -> book.getStatus().equals("haveRead"))
                    .collect(Collectors.toList()).forEach(book -> {
                haveReadListView.getItems().add(book.getTitle());
            });
        } else {
            haveReadListView.setVisible(false);
            noBookLabel.setVisible(true);
        }
    }
}
