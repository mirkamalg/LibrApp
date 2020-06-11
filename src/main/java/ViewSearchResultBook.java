import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewSearchResultBook implements Initializable {

    static Stage stage;
    String googleURL;
    Book book;
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
    private Label matureContentLabel;

    @FXML
    private FontAwesomeIconView matureContentIcon;

    @FXML
    private Label googleIDLabel;


    public static void initializeViewSearchResultBookScreen() throws IOException {
        stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Book info");
        stage.setResizable(false);

        Parent parent = FXMLLoader.load(ViewSearchResultBook.class.getResource("ViewSearchResultBookScreen.fxml"));
        Scene viewSearchResultBookScene = new Scene(parent, 1094, 655);

        stage.setScene(viewSearchResultBookScene);
        stage.show();
    }

    public void googleLinkAction(ActionEvent actionEvent) {
        copy(googleURL);
    }

    public void closeAction(MouseEvent mouseEvent) {
        stage.close();
    }

    public void addWantToReadAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {

        if (!DataHandler.getBooks().containsKey(book.getTitle())) {
            book.setStatus("wantToRead");
            DataHandler.getBooks().put(book.getTitle(), book);

            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(book.getCoverImage(), null);     //  Convert image to file

            File file = new File("Thumbnails\\" + book.getTitle() + ".png");
            if (file.createNewFile()) {
                ImageIO.write(bufferedImage, "png", file);
            }

            DataBase.addBook(book.getGoogleID(), book.getTitle(), book.getPublisher(), book.getPublishDate(), book.getDescription(),
                    book.getLanguage(), book.getGoogleBooksInfoURL(), book.getPageCount(), book.getAverageRating(), book.hasMatureContent(),
                    book.getAuthors(), book.getCategories(), book.getImages(), book.getStatus(), file);

            stage.close();
        }
    }

    public void addReadingAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {

        if (!DataHandler.getBooks().containsKey(book.getTitle())) {
            book.setStatus("reading");
            DataHandler.getBooks().put(book.getTitle(), book);

            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(book.getCoverImage(), null);     //  Convert image to file

            File file = new File("Thumbnails\\" + book.getTitle() + ".png");
            if (file.createNewFile()) {
                ImageIO.write(bufferedImage, "png", file);
            }

            DataBase.addBook(book.getGoogleID(), book.getTitle(), book.getPublisher(), book.getPublishDate(), book.getDescription(),
                    book.getLanguage(), book.getGoogleBooksInfoURL(), book.getPageCount(), book.getAverageRating(), book.hasMatureContent(),
                    book.getAuthors(), book.getCategories(), book.getImages(), book.getStatus(), file);

            stage.close();
        }
    }

    public void addHaveReadAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {

        if (!DataHandler.getBooks().containsKey(book.getTitle())) {
            book.setStatus("haveRead");
            DataHandler.getBooks().put(book.getTitle(), book);

            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(book.getCoverImage(), null);     //  Convert image to file

            File file = new File("Thumbnails\\" + book.getTitle() + ".png");
            if (file.createNewFile()) {
                ImageIO.write(bufferedImage, "png", file);
            }

            DataBase.addBook(book.getGoogleID(), book.getTitle(), book.getPublisher(), book.getPublishDate(), book.getDescription(),
                    book.getLanguage(), book.getGoogleBooksInfoURL(), book.getPageCount(), book.getAverageRating(), book.hasMatureContent(),
                    book.getAuthors(), book.getCategories(), book.getImages(), book.getStatus(), file);

            stage.close();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        book = Search.books.get(DataHandler.titleSearched);

        googleIDLabel.setText(book.getGoogleID());
        googleURL = book.getGoogleBooksInfoURL();

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
    }

    private void copy(String copiedText) {
        StringSelection stringSelection = new StringSelection(copiedText);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
}
