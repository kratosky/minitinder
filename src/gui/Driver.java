package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;


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
            String[] userlist ={"12","123"};
            ArrayList<String> userToSelect = new ArrayList<String>(Arrays.asList(userlist));
            new Matching().display("1",3,userToSelect);
        });

        // Create a scene and place it in the stage
        Scene scene = new Scene(paneForButtons, 500, 300);
        primaryStage.setTitle("Driver"); // Set title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    public VBox setRadioButton()
    {
        VBox paneForRadioButtons = new VBox(20);
        paneForRadioButtons.setPadding(new Insets(5, 5, 5, 5));
        paneForRadioButtons.setStyle("-fx-border-color: green");
        paneForRadioButtons.setStyle
                ("-fx-border-width: 2px; -fx-border-color: green");
        RadioButton rbRed = new RadioButton("Red");
        RadioButton rbGreen = new RadioButton("Green");
        RadioButton rbBlue = new RadioButton("Blue");
        paneForRadioButtons.getChildren().addAll(rbRed, rbGreen, rbBlue);


        rbRed.setOnAction(e -> {
            if (rbRed.isSelected()) {
                //text.setFill(Color.RED);
            }
        });

        rbGreen.setOnAction(e -> {
            if (rbGreen.isSelected()) {
                //text.setFill(Color.GREEN);
            }
        });

        rbBlue.setOnAction(e -> {
            if (rbBlue.isSelected()) {
                //text.setFill(Color.BLUE);
            }
        });

        return paneForRadioButtons;
    }




    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
