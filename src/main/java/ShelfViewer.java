import javafx.event.ActionEvent;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShelfViewer implements Initializable {

    static Stage stage;
    @FXML
    private ListView<String> booksListView;
    @FXML
    private Label bookShelfName;

    static void initializeViewShelfScreen() throws IOException {
        stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle(DataHandler.shelfName);
        stage.setResizable(false);

        Parent parent = FXMLLoader.load(AllBooks.class.getResource("ViewShelfScreen.fxml"));
        Scene viewShelfScene = new Scene(parent, 830, 655);

        stage.setScene(viewShelfScene);
        stage.show();
    }

    public void viewBookAction(MouseEvent mouseEvent) throws IOException {
        DataHandler.title = booksListView.getSelectionModel().getSelectedItem();
        ViewBookInfo.initializeBookInfoScreen();

        booksListView.getItems().clear();
        Bookshelves.bookshelves.get(DataHandler.shelfName).forEach((bookTitle) -> {
            booksListView.getItems().add(bookTitle);
        });
    }

    public void closeAction(MouseEvent mouseEvent) {
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bookShelfName.setText(DataHandler.shelfName);

        ArrayList<String> removedList = new ArrayList<>();  //  Collect removed books here, and then delete them from Bookshelves.json
        Bookshelves.bookshelves.get(DataHandler.shelfName).forEach((bookTitle) -> {

            if (DataHandler.getBooks().containsKey(bookTitle)) {        //  Add book only if it actually exists (not removed after being added to this shelf)
                booksListView.getItems().add(bookTitle);
            } else {
                removedList.add(bookTitle);
            }
        });

        //  Remove junk books from json here
        removedList.forEach((bookTitle) -> {
            if (Bookshelves.bookshelves.get(DataHandler.shelfName).contains(bookTitle)) {
                Bookshelves.bookshelves.get(DataHandler.shelfName).remove(bookTitle);
            }
        });

        try {
            Bookshelves.saveBookShelves();
        } catch (IOException e) {
            e.printStackTrace();
        }

        booksListView.setCellFactory(param -> new CustomCell());
    }

    public void addBookAction(ActionEvent actionEvent) throws IOException {
        AddBookToShelf.initializeAddToShelfScreen();

        booksListView.getItems().clear();
        Bookshelves.bookshelves.get(DataHandler.shelfName).forEach((bookTitle) -> {
            booksListView.getItems().add(bookTitle);
        });

        Bookshelves.saveBookShelves();
    }

    class CustomCell extends ListCell<String> {
        HBox hBox = new HBox(50);
        Button button = new Button("Remove");
        Label title = new Label("");

        public CustomCell() {
            super();

            hBox.getChildren().addAll(button, title);
            hBox.setAlignment(Pos.CENTER_LEFT);
            button.setStyle("-fx-background-radius: 25 25 25 25; -fx-background-color:  #26c6da");

            button.setOnAction(event -> {
                String item = getItem();
                getListView().getItems().remove(item);
                Bookshelves.bookshelves.get(DataHandler.shelfName).remove(item);

                try {
                    Bookshelves.saveBookShelves();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
