import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class About {

    public void closeAction(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void sourceCodeAction(ActionEvent actionEvent) {
        HostServicesFactory.getInstance(new Application() {
            @Override
            public void start(Stage primaryStage) throws Exception {
                // keep empty
            }
        }).showDocument("https://github.com/Re1r0/LibrApp");
    }
}
