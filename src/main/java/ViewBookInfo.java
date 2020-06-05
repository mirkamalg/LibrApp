import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;

public class ViewBookInfo {

    @FXML
    private Label bookTitleTopLabel;

    @FXML
    private ImageView coverImageView;

    @FXML
    private Label bookTitleLabel;

    @FXML
    private Label authorNameLabel1;

    @FXML
    private Label authorNameLabel2;

    @FXML
    private Label publisherNameLabel;

    @FXML
    private Label publishDateLabel;

    @FXML
    private Label averageRatingLabel;

    @FXML
    private Label languageLabel;

    @FXML
    private Label pageCountLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label categoriesLabel;

    @FXML
    private Button googleIDButton;

    @FXML
    private Button googleLinkButton;

    Stage stage;
    String googleID;
    String googleURL;

    public void initializeBookInfoScreen(Book book) throws IOException {
        stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Book info");
        stage.setResizable(false);

        Parent parent = FXMLLoader.load(ViewBookInfo.class.getResource("ViewBookInfoScreen.fxml"));
        Scene viewBookScene = new Scene(parent, 1094, 655);

        googleID = book.getGoogleID();
        googleURL = book.getGoogleBooksInfoURL();

        bookTitleLabel.setText(book.getTitle());
        bookTitleTopLabel.setText(book.getTitle());
        authorNameLabel1.setText(book.getAuthors()[0]);
        authorNameLabel2.setText(book.getAuthors()[1]);
        publisherNameLabel.setText(book.getPublisher());
        publishDateLabel.setText(book.getPublishDate());
        averageRatingLabel.setText(String.valueOf(book.getAverageRating()));
        languageLabel.setText(book.getLanguage());
        descriptionLabel.setText(book.getDescription());
        pageCountLabel.setText(String.valueOf(book.getPageCount()));
        categoriesLabel.setText(book.getCategories());

        stage.setScene(viewBookScene);
        stage.showAndWait();
    }

    public void googleIDAction(ActionEvent actionEvent) {
        copy(googleID);
    }

    public void googleLinkAction(ActionEvent actionEvent) {
        copy(googleURL);
    }

    public void closeAction(MouseEvent mouseEvent) {
        stage.close();
    }

    private void copy(String copiedText) {
        StringSelection stringSelection = new StringSelection(copiedText);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
}
