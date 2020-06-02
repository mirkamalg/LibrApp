import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AddNewBook {

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

    public void addAction(ActionEvent actionEvent) {

    }

    public void closeAction(MouseEvent mouseEvent) {
        stage.close();
    }

    public void detailsAction(ActionEvent actionEvent) {

    }
}
