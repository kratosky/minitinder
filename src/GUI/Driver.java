package GUI;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import user.Gender;

public class Driver extends Application
{
    private final String[] genderDescr = {"男", "女", "其他"};
    private final Gender[] gender = {Gender.Male, Gender.Female, Gender.Other};
    private ComboBox<String> cbo = new ComboBox<>();
    private TextField tfYears = new TextField();
    private TextField tfPhotoAddress = new TextField();
    private TextArea taContact = new TextArea();
    /** Label for displaying an image and a title */
    private Label lblImageTitle = new Label();
    private TextArea taIntro = new TextArea();
    private Label lblMessage = new Label("上传资料，审核通过后，即可进入个人主页开始匹配！");
    private Button btLeft = new Button("上传");
    private Button btRight = new Button("进入个人主页");


    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {


        cbo.setValue("男");
        ObservableList<String> items =
                FXCollections.observableArrayList(genderDescr);
        cbo.getItems().addAll(items);
        // Display the selected country
        cbo.setOnAction(e -> setGender(items.indexOf(cbo.getValue())));

        taContact.setFont(new Font("Times", 14));
        taContact.setWrapText(true);
        taContact.setPrefRowCount(2);

        // Create UI
        GridPane leftGridPane = new GridPane();
        leftGridPane.setHgap(5);
        leftGridPane.setVgap(5);
        leftGridPane.add(new Label("性别:"), 0, 0);
        leftGridPane.add(cbo, 1, 0);
        leftGridPane.add(new Label("出生年份:"), 0, 1);
        leftGridPane.add(tfYears, 1, 1);
        leftGridPane.add(new Label("上传照片:"), 0, 2);
        leftGridPane.add(tfPhotoAddress, 1, 2);
        leftGridPane.add(new Label("联系方式:"), 0, 3);
        leftGridPane.add(taContact, 1, 3);

        // Set properties for UI
        leftGridPane.setAlignment(Pos.CENTER);
        tfYears.setAlignment(Pos.BOTTOM_LEFT);
        tfPhotoAddress.setAlignment(Pos.BOTTOM_LEFT);
        //GridPane.setHalignment(btCalculate, HPos.RIGHT);

        // 输入照片回车，可以预览照片
        tfPhotoAddress.setOnAction(e -> lblImageTitle.setGraphic(new ImageView("image/"+tfPhotoAddress.getText())));

        lblImageTitle.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        lblImageTitle.setPrefSize(20,  10);
        lblImageTitle.setFont(new Font("Times", 16));
        lblImageTitle.setOnMouseClicked(e-> lblImageTitle.setGraphic(null));
    /*
    lblImageTitle.setMaxSize(30, 30);
    lblImageTitle.setLayoutX(30);
    lblImageTitle.setLayoutY(30);*/

        taIntro.setFont(new Font("Times", 14));
        taIntro.setWrapText(true);
        taIntro.setPrefRowCount(5);


        // Create UI
        GridPane rightGridPane = new GridPane();
        rightGridPane.setHgap(5);
        rightGridPane.setVgap(5);
        rightGridPane.add(new Label("照片预览:"), 0, 0);
        rightGridPane.add(lblImageTitle, 1, 0);
        rightGridPane.add(new Label("个人介绍:"), 0, 1);
        rightGridPane.add(taIntro, 1, 1);
        // Set properties for UI
        rightGridPane.setAlignment(Pos.CENTER);


        GridPane midGridPane = new GridPane();
        midGridPane.setHgap(10);
        midGridPane.setVgap(30);
        midGridPane.add(leftGridPane,0,0);
        midGridPane.add(rightGridPane,1,0);
        midGridPane.setAlignment(Pos.CENTER);

        HBox paneForButtons = new HBox(20);
        paneForButtons.getChildren().addAll(btLeft, btRight);
        paneForButtons.setAlignment(Pos.CENTER);
        //lblMessage.setStyle("-fx-border-color: black");
        //lblMessage.setAlignment(Pos.CENTER);

        btLeft.setOnAction(e->{new UpdateInformation().display("1");});

        VBox vBox = new VBox(30);
        vBox.getChildren().addAll(paneForButtons, lblMessage);
        vBox.setAlignment(Pos.CENTER); // 布局居中显示



        GridPane totalGridPane = new GridPane();
        totalGridPane.setHgap(10);
        totalGridPane.setVgap(70);
        totalGridPane.add( midGridPane,0,0);
        totalGridPane.add(vBox,0,1);
        //totalGridPane.add(lblMessage,0,2);
        totalGridPane.setAlignment(Pos.CENTER);

        // Create a scene and place it in the stage
        Scene scene = new Scene(totalGridPane, 1500, 400);
        primaryStage.setTitle("LoanCalculator"); // Set title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    public void setGender(int index)
    {
        System.out.println(index);
    /*
    descriptionPane.setTitle(flagTitles[index]);
    descriptionPane.setImageView(flagImage[index]);
    descriptionPane.setDescription(flagDescription[index]);*/
    }

    private void calculateLoanPayment()
    {
        // Get values from text fields
        int year = Integer.parseInt(tfYears.getText());
        double loanAmount = Double.parseDouble(tfPhotoAddress.getText());

        // Create a loan object. Loan defined in Listing 10.2
        //Loan loan = new Loan(interest, year, loanAmount);

        // Display monthly payment and total payment
        //tfMonthlyPayment.setText(String.format("$%.2f",loan.getMonthlyPayment()));
        //tfTotalPayment.setText(String.format("$%.2f", loan.getTotalPayment()));
    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
