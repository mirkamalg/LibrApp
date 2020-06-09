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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class HaveRead implements Initializable {

    static Stage stage;
    @FXML
    private ListView<String> haveReadListView;

    static void initializeHaveReadScreen() throws IOException {
        stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Have read");
        stage.setResizable(false);

        Parent parent = FXMLLoader.load(HaveRead.class.getResource("HaveReadScreen.fxml"));
        Scene haveReadScene = new Scene(parent, 830, 655);

        stage.setScene(haveReadScene);
        stage.showAndWait();
    }

    public void viewBookAction(MouseEvent mouseEvent) throws IOException {
        DataHandler.title = haveReadListView.getSelectionModel().getSelectedItem();
        ViewBookInfo.initializeBookInfoScreen();
    }

    public void closeAction(MouseEvent mouseEvent) {
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        haveReadListView.setCellFactory(param -> new CustomCell());

        DataHandler.getBooks().values().stream()         //  Getting books with value of "haveRead" and adding them to the listview
                .filter(book -> book.getStatus().equals("haveRead"))
                .collect(Collectors.toList()).forEach(book -> {
            haveReadListView.getItems().add(book.getTitle());
        });
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
            button.setStyle("-fx-background-radius: 25 25 25 25; -fx-background-color:  #ffe0b2");

            button.setOnAction(event -> {
                String item = getItem();
                getListView().getItems().remove(item);
                DataHandler.getBooks().remove(item);
                try {
                    DataBase.deleteBook(item);
                } catch (SQLException | ClassNotFoundException | FileNotFoundException throwables) {
                    throwables.printStackTrace();
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

                img.setImage(DataHandler.getBooks().get(name).getCoverImage());

                setGraphic(hBox);
            }
        }
    }
}
