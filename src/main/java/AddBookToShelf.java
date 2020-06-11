import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddBookToShelf implements Initializable {

    static Stage stage;
    @FXML
    private ListView<String> allBooksListView;

    static void initializeAddToShelfScreen() throws IOException {
        stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Add to shelf");
        stage.setResizable(false);

        Parent parent = FXMLLoader.load(AllBooks.class.getResource("AddBookToShelfScreen.fxml"));
        Scene addToShelfScene = new Scene(parent, 830, 655);

        stage.setScene(addToShelfScene);
        stage.showAndWait();
    }

    public void closeAction(MouseEvent mouseEvent) {
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //  Add book to listview (Not those which are already in this shelf)
        DataHandler.getBooks().forEach((bookTitle, book) -> {
            if (!Bookshelves.bookshelves.get(DataHandler.shelfName).contains(bookTitle)) {
                allBooksListView.getItems().add(bookTitle);
            }
        });

        allBooksListView.setCellFactory(param -> new CustomCell());
    }

    class CustomCell extends ListCell<String> {
        HBox hBox = new HBox(50);
        Button button = new Button("Add");
        Label title = new Label("");

        public CustomCell() {
            super();

            hBox.getChildren().addAll(button, title);
            hBox.setAlignment(Pos.CENTER_LEFT);
            button.setStyle("-fx-background-radius: 25 25 25 25; -fx-background-color:  #26c6da");

            button.setOnAction(event -> {
                String item = getItem();
                getListView().getItems().remove(item);
                Bookshelves.bookshelves.get(DataHandler.shelfName).add(item);
            });
        }

        public void updateItem(String name, boolean empty) {
            super.updateItem(name, empty);

            setText(null);
            setGraphic(null);

            if (name != null && ! empty) {
                title.setText(name);
                title.setFont(new Font(36));

                setGraphic(hBox);
            }
        }
    }
}
