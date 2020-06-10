import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class CustomCell extends ListCell<String> {
    HBox hBox = new HBox(50);
    Button button = new Button("Remove");
    Label title = new Label("");
    ImageView img = new ImageView();

    public CustomCell() {
        super();

        hBox.getChildren().addAll(img, button, title);
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
