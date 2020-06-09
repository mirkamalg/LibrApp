import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.net.URL;

public class TransitionHandler {

    Pane pane;

    public Pane changePane(String name) {
        try{
            URL url = TransitionHandler.class.getResource(name + ".fxml");
            if (url == null) {
                System.out.println("FXML NOT FOUND!");
            }

            pane = new FXMLLoader().load(url);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return pane;
    }
}
