package ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.controllers;

import ca.mcgill.ecse.fashionstoremanagement.javafx.fxml.FashionStoreFxmlView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewUtils {

    public static void showError(String message) {
        makePopupWindow("Error", message);
    }

    public static void showSuccess(String message) {
        makePopupWindow("Success", message);
    }

    public static void makePopupWindow(String title, String message) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox dialogPane = new VBox();

        Text text = new Text(message);
        Button okButton = new Button("OK");
        okButton.setOnAction(a -> dialog.close());

        int innerPadding = 10;
        int outerPadding = 100;
        dialogPane.setSpacing(innerPadding);
        dialogPane.setAlignment(Pos.CENTER);
        dialogPane.setPadding(new Insets(innerPadding, innerPadding, innerPadding, innerPadding));
        dialogPane.getChildren().addAll(text, okButton);
        Scene dialogScene = new Scene(dialogPane, outerPadding + 5 * message.length(), outerPadding);
        dialog.setScene(dialogScene);
        dialog.setTitle(title);
        dialog.show();
    }
}
