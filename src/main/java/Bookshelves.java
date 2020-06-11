import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Bookshelves implements Initializable {

    public static Map<String, ArrayList<String>> bookshelves = new HashMap<>();
    @FXML
    private ListView<String> bookShelvesListView;
    @FXML
    private TextField newBookShelfTextField;

    public static void saveBookShelves() throws IOException {
        Writer writer = new FileWriter("Bookshelves.json");

        new Gson().toJson(bookshelves, writer);

        writer.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bookShelvesListView.setCellFactory(param -> new CustomCell());

        try {
            loadBookShelves();

            bookshelves.forEach((bookShelveName, bookTitle) -> {
                bookShelvesListView.getItems().add(bookShelveName);
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void closeAction(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void viewBookShelfAction(MouseEvent mouseEvent) throws IOException {
        DataHandler.shelfName = bookShelvesListView.getSelectionModel().getSelectedItem();
        ShelfViewer.initializeViewShelfScreen();

        saveBookShelves();
    }

    private void loadBookShelves() throws FileNotFoundException {
        if (Files.exists(Paths.get("Bookshelves.json"))) {

            Reader reader = new FileReader("Bookshelves.json");

            bookshelves = new Gson().fromJson(reader, Map.class);
        }
    }

    public void addNewShelfAction(ActionEvent actionEvent) throws IOException {

        if (!newBookShelfTextField.getText().isEmpty()) {
            if (!bookshelves.containsKey(newBookShelfTextField.getText())) {

                bookShelvesListView.getItems().add(newBookShelfTextField.getText());

                bookshelves.put(newBookShelfTextField.getText(), new ArrayList<>());
                saveBookShelves();
            } else {
                newBookShelfTextField.setText("Already exists!");
            }
        }
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
                Bookshelves.bookshelves.remove(item);

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
