package gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class Driver extends Application
{
    private Button btLeft = new Button("进入测试程序");


    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {


        HBox paneForButtons = new HBox(20);
        paneForButtons.getChildren().addAll(btLeft);
        paneForButtons.setAlignment(Pos.CENTER);
        btLeft.setOnAction(e->{
            SelectionSetting.clearAndClose();
            //SelectionSetting.display("haha");
        });

        // Create a scene and place it in the stage
        Scene scene = new Scene(paneForButtons, 500, 300);
        primaryStage.setTitle("Driver"); // Set title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }





    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
