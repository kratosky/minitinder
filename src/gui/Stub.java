package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Stub
{
    private static Button btLeft = new Button("进入测试程序");

    public static void display()
    {

        Stage primaryStage = new Stage();
        HBox paneForButtons = new HBox(20);
        paneForButtons.getChildren().addAll(btLeft);
        paneForButtons.setAlignment(Pos.CENTER);
        btLeft.setOnAction(e->{Buy.clearAndClose();Buy.display("haha");});

        // Create a scene and place it in the stage
        Scene scene = new Scene(paneForButtons, 500, 300);
        primaryStage.setTitle("Driver"); // Set title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }
}
