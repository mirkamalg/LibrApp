import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddNewBook {

    @FXML
    private VBox detailsVBox;

    @FXML
    private ImageView coverImageView;

    @FXML
    private TextField bookTitleTextField;

    @FXML
    private TextField authorNameTextField;

    @FXML
    private TextField authorNameTextField1;

    @FXML
    private TextField pageCountTextField;

    @FXML
    private CheckBox detailsCheckBox;

    @FXML
    private TextField publisherTextField;

    @FXML
    private TextField publishDateTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField languageTextField;

    @FXML
    private TextField categoriesTextField;

    @FXML
    private CheckBox matureContentCheckBox;

    @FXML
    private Button addButton;

    static Stage stage;

    public static void initializeAddNewNoteScreen() throws IOException {
        stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Add book");
        stage.setResizable(false);

        Parent parent = FXMLLoader.load(AddNewBook.class.getResource("AddNewBooksScreen.fxml"));
        Scene addBookScene = new Scene(parent, 900, 567);

        stage.setScene(addBookScene);
        stage.showAndWait();
    }

    public void addAction(ActionEvent actionEvent) throws IOException {
        if (!bookTitleTextField.getText().isEmpty() && !authorNameTextField.getText().isEmpty() && !authorNameTextField1.getText().isEmpty() && !pageCountTextField.getText().isEmpty()) {
            String bookTitle = bookTitleTextField.getText();

            String authorOne = authorNameTextField.getText();
            String authorTwo = authorNameTextField1.getText();
            String[] authors = new String[] {authorOne, authorTwo};

            int pageCount;
            try {
                pageCount = Integer.parseInt(pageCountTextField.getText());
            }catch (Exception e) {
                pageCountTextField.setText("Must be an integer!");
                return;
            }

            Map<String, String> images = new HashMap<>();
            images.put("none", "none");

            String publisher;
            String publishDate;
            String description;
            String language;
            String categories;
            int hasMatureContent;  // 0 is false and 1 is true

            if (detailsCheckBox.isSelected()) {
                publisher = publisherTextField.getText();
                publishDate = publishDateTextField.getText();
                description = descriptionTextField.getText();
                language = languageTextField.getText();
                categories = categoriesTextField.getText();

                if (matureContentCheckBox.isSelected()) {
                    hasMatureContent = 1;
                } else{
                    hasMatureContent = 0;
                }
            } else {
                publisher = "?";
                publishDate = "?";
                description = "?";
                language = "?";
                categories = "?";
                hasMatureContent = 0;
            }

            Book addedBook = new Book("none", bookTitle, publisher, publishDate, description, language, "none", pageCount, 0, hasMatureContent, authors, categories, images);

            DataHandler.getBooks().put(bookTitle, addedBook);
            stage.close();
        }
    }

    public void closeAction(MouseEvent mouseEvent) {
        stage.close();
    }

    public void detailsAction(ActionEvent actionEvent) {
        detailsVBox.setDisable(!detailsCheckBox.isSelected());
    }
}
