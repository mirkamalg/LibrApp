import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Search implements Initializable {

    @FXML
    private AnchorPane searchPane;

    @FXML
    private TextField bookTitleTextField;

    @FXML
    private Button searchButton;

    @FXML
    private RadioButton authorNameToggle;

    @FXML
    private RadioButton publisherNameToggle;

    @FXML
    private TextField authorNameTextField;

    @FXML
    private TextField publisherNameTextField;

    static Map<String, Book> books = new HashMap<>();
    @FXML
    private ListView<String> resultListView;

    public void searchAction(ActionEvent actionEvent) throws IOException {

        String title;
        String author;
        String publisher;

        if (!bookTitleTextField.getText().isEmpty()) {
            title = bookTitleTextField.getText();

            if (authorNameToggle.isSelected() && !authorNameTextField.getText().isEmpty()) {
                author = authorNameTextField.getText();
            }

            if (publisherNameToggle.isSelected() && !publisherNameTextField.getText().isEmpty()) {
                publisher = publisherNameTextField.getText();
            }

            ArrayList<Book> booksList = BookFinder.jsonToBookList(BookFinder.searchBooksJson(title));

            if (!books.isEmpty()) {
                books.clear();
                resultListView.getItems().clear();
            }

            booksList.forEach(book -> {
                books.put(book.getTitle(), book);
            });

            books.forEach((bookTitle, book) -> {
                resultListView.getItems().add(bookTitle);
            });
        }
    }

    public void closeAction(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void authorNameToggleAction(ActionEvent actionEvent) {
        authorNameTextField.setDisable(!authorNameToggle.isSelected());
    }

    public void publisherNameToggleAction(ActionEvent actionEvent) {
        publisherNameTextField.setDisable(!publisherNameToggle.isSelected());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resultListView.setCellFactory(param -> new Search.CustomCell());
    }

    public void viewBookAction(MouseEvent mouseEvent) throws IOException {
        DataHandler.titleSearched = resultListView.getSelectionModel().getSelectedItem();
        ViewSearchResultBook.initializeViewSearchResultBookScreen();
    }

    static class CustomCell extends ListCell<String> {

        HBox hBox = new HBox(50);
        Label title = new Label("");
        ImageView img = new ImageView();

        public CustomCell() {
            super();

            hBox.getChildren().addAll(img, title);
            hBox.setAlignment(Pos.CENTER_LEFT);

            img.setFitHeight(100);
            img.setFitWidth(100);
        }

        public void updateItem(String name, boolean empty) {
            super.updateItem(name, empty);

            setText(null);
            setGraphic(null);

            if (name != null && ! empty) {
                title.setText(name);
                title.setFont(new Font(24));

                img.setImage(books.get(name).getCoverImage());

                setGraphic(hBox);
            }
        }
    }
}
