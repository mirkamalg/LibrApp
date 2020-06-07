import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AddNewBook implements Initializable {

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

    @FXML
    private RadioButton wantToReadToggle;

    @FXML
    private RadioButton readingToggle;

    @FXML
    private RadioButton haveReadToggle;

    static Stage stage;

    public static File selectedFile;

    private DataBase dataBase = new DataBase();

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

    public void addAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        if (!bookTitleTextField.getText().isEmpty() && !authorNameTextField.getText().isEmpty() && !pageCountTextField.getText().isEmpty()) {

            String bookTitle;
            if (DataHandler.getBooks().containsKey(bookTitleTextField.getText())) {
                bookTitleTextField.setText("This book already exists!");
                return;
            }else {
                bookTitle = bookTitleTextField.getText();
            }

            String authorOne = authorNameTextField.getText();
            String authorTwo;
            if (authorNameTextField1.getText().isEmpty()) {
                authorTwo = "?";
            } else {
                authorTwo = authorNameTextField1.getText();
            }

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
            String status = "wantToRead";
            int hasMatureContent;  // 0 is false and 1 is true

            if (detailsCheckBox.isSelected()) {
                if (publisherTextField.getText().isEmpty()) {
                    publisher = "?";
                } else {
                    publisher = publisherTextField.getText();
                }

                if (publishDateTextField.getText().isEmpty()) {
                    publishDate = "?";
                } else{
                    publishDate = publishDateTextField.getText();
                }

                if (descriptionTextField.getText().isEmpty()) {
                    description = "?";
                } else {
                    description = descriptionTextField.getText();
                }

                if (languageTextField.getText().isEmpty()) {
                    language = "?";
                } else {
                    language = languageTextField.getText();
                }

                if (categoriesTextField.getText().isEmpty()) {
                    categories = "?";
                } else {
                    categories = categoriesTextField.getText();
                }

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

            if (wantToReadToggle.isSelected()) status = "wantToRead";   //  Setting type
            else if (readingToggle.isSelected()) status = "reading";
            else if (haveReadToggle.isSelected()) status = "haveRead";

            Book addedBook = new Book("none", bookTitle, publisher,
                    publishDate, description, language,
                    "none", pageCount, 0,
                    hasMatureContent, authors, categories, images, status);

            DataHandler.getBooks().put(bookTitle, addedBook);

            DataBase.addBook("none", bookTitle, publisher,
                    publishDate, description, language,
                    "none", pageCount, 0,
                    hasMatureContent, authors, categories, images, status);


            stage.close();
        }
    }

    public void closeAction(MouseEvent mouseEvent) {
        stage.close();
    }

    public void detailsAction(ActionEvent actionEvent) {
        detailsVBox.setDisable(!detailsCheckBox.isSelected());
    }

    public void addCoverPhotoAction(MouseEvent mouseEvent) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg");
        fileChooser.getExtensionFilters().add(imageFilter);

        selectedFile = fileChooser.showOpenDialog(stage);
        Image image = new Image(new FileInputStream(selectedFile.getAbsolutePath()));
        coverImageView.setImage(image);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup toggleGroup = new ToggleGroup();
        wantToReadToggle.setToggleGroup(toggleGroup);
        readingToggle.setToggleGroup(toggleGroup);
        haveReadToggle.setToggleGroup(toggleGroup);
        wantToReadToggle.setSelected(true);
    }
}
