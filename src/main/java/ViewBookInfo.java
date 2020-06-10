import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.net.URL;
import java.util.ResourceBundle;

public class ViewBookInfo implements Initializable {

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

    @FXML
    private Label statusLabel;

    @FXML
    private Label matureContentLabel;

    @FXML
    private FontAwesomeIconView matureContentIcon;

    static Stage stage;
    String googleID;
    String googleURL;

    public static void initializeBookInfoScreen() throws IOException {
        stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Book info");
        stage.setResizable(false);

        Parent parent = FXMLLoader.load(ViewBookInfo.class.getResource("ViewBookInfoScreen.fxml"));
        Scene viewBookScene = new Scene(parent, 1094, 655);

        stage.setScene(viewBookScene);
        stage.showAndWait();
    }

    public void googleIDAction(ActionEvent actionEvent) {
        copy(googleID);
    }

    public void googleLinkAction(ActionEvent actionEvent) {
        HostServicesFactory.getInstance(new Application() {
            @Override
            public void start(Stage primaryStage) throws Exception {
                // keep empty
            }
        }).showDocument(googleURL);
    }

    public void closeAction(MouseEvent mouseEvent) {
        stage.close();
    }

    private void copy(String copiedText) {
        StringSelection stringSelection = new StringSelection(copiedText);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Book book = DataHandler.getBooks().get(DataHandler.title);

        try {
            googleID = book.getGoogleID();
            googleURL = book.getGoogleBooksInfoURL();
        }catch (Exception ignored){
            googleID = "none";
            googleURL = "none";
        }

        if (googleURL.equals("none")) googleLinkButton.setDisable(true);  // Disable if has no actual google link
        if (googleID.equals("none")) googleIDButton.setDisable(true);     // Disable if has no actual google ID

        bookTitleLabel.setText(book.getTitle());
        bookTitleTopLabel.setText(book.getTitle());
        authorNameLabel1.setText(book.getAuthors()[0]);
        try{
            authorNameLabel2.setText(book.getAuthors()[1]);
        }catch (Exception ignored) {
            authorNameLabel2.setText("?");
        }

        publisherNameLabel.setText(book.getPublisher());
        publishDateLabel.setText(book.getPublishDate());
        averageRatingLabel.setText(String.valueOf(book.getAverageRating()));
        languageLabel.setText(book.getLanguage());
        descriptionLabel.setText(book.getDescription());
        pageCountLabel.setText(String.valueOf(book.getPageCount()));
        categoriesLabel.setText(book.getCategories());
        coverImageView.setImage(book.getCoverImage());

        if (book.hasMatureContent() == 1) {     //  Default is no, only this case should be considered
            matureContentLabel.setText("Yes");
            matureContentIcon.setGlyphName("WARNING");
            matureContentIcon.setStyle("-fx-fill: orange");
        }

        switch (book.getStatus()) {
            case "wantToRead":
                statusLabel.setText("Want to read");
                break;
            case "reading":
                statusLabel.setText("Reading");
                break;
            case "haveRead":
                statusLabel.setText("Have read");
                break;
        }
    }
}
