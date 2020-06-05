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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
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
        stage.setTitle("Add book");
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
        ViewBookInfo viewBookInfo = new ViewBookInfo();
        viewBookInfo.initializeBookInfoScreen(DataHandler.getBooks().get(allBooksListView.getSelectionModel().getSelectedItem()));
    }

    static class CustomCell extends ListCell<String> {

        HBox hBox = new HBox(50);
        Button button = new Button("Remove");
        Label title = new Label("");
        ImageView img = new ImageView();

        public CustomCell() {
            super();

            hBox.getChildren().addAll(img, title, button);
            hBox.setAlignment(Pos.CENTER_LEFT);

            img.setFitHeight(100);
            img.setFitWidth(100);

            button.setOnAction(event -> {
                String item = getItem();
                getListView().getItems().remove(item);
                DataHandler.getBooks().remove(item);
            });
        }

        public void updateItem(String name, boolean empty) {
            super.updateItem(name, empty);

            setText(null);
            setGraphic(null);

            if (name != null && ! empty) {
                title.setText(name);
                title.setFont(new Font(36));

                img.setImage(DataHandler.getBooks().get(name).getCoverImage());

                setGraphic(hBox);
            }
        }
    }
}


