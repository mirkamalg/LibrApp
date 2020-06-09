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

public class AllBooks implements Initializable {

    @FXML
    private ListView<String> allBooksListView;

    static Stage stage;

    static void initializeAllBooksScreen() throws IOException {
        stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("All books");
        stage.setResizable(false);

        Parent parent = FXMLLoader.load(AllBooks.class.getResource("AllBooksScreen.fxml"));
        Scene allBooksScene = new Scene(parent, 830, 655);

        stage.setScene(allBooksScene);
        stage.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allBooksListView.setCellFactory(param -> new CustomCell());

        DataHandler.getBooks().forEach((title, book) -> {
            allBooksListView.getItems().add(title);
        });
    }

    public void closeAction(MouseEvent mouseEvent) {
        stage.close();
    }

    public void viewBookAction(MouseEvent mouseEvent) throws IOException {
        DataHandler.title = allBooksListView.getSelectionModel().getSelectedItem();
        ViewBookInfo.initializeBookInfoScreen();
    }
}


