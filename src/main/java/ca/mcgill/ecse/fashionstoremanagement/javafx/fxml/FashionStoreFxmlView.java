package ca.mcgill.ecse.fashionstoremanagement.javafx.fxml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class FashionStoreFxmlView extends Application{
    public static final EventType<Event> REFRESH_EVENT = new EventType<>("REFRESH");
    private static FashionStoreFxmlView instance;
    private List<Node> refreshableNodes = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        instance = this;
        try {
            var root = (Pane) FXMLLoader.load(getClass().getResource("/ca/mcgill/ecse/fashionstoremanagement/javafx/fxml/MainPage.fxml"));
            var scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(600);
            primaryStage.setTitle("Fashion Store Management");
            primaryStage.show();
            refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerRefreshEvent(Node node) {
        refreshableNodes.add(node);
    }

    public void registerRefreshEvent(Node... nodes) {
        for (var node : nodes) {
            refreshableNodes.add(node);
        }
    }

    public void removeRefreshableNode(Node node) {
        refreshableNodes.remove(node);
    }

    public void refresh() {
        for (Node node : refreshableNodes) {
            node.fireEvent(new Event(REFRESH_EVENT));
        }
    }

    public static FashionStoreFxmlView getInstance() {
        return instance;
    }
}
