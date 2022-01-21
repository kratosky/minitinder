package gui;

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
import match_operation.Match;
import user.CheckedUser;
import user.Gender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static user.Gender.*;
import static user.Gender.Male;

public class MatchedOnes
{
    private final String[] genderDescr = {"男", "女", "其他"};
    private final Gender[] genderOptions = {Male, Female, Other};


    private Label lblcurrentSelectee = new Label();
    private Label lblGender = new Label();
    private Label lblAge = new Label();
    private String currentMatchPhoto;
    private Button btShowPhoto = new Button("展示照片");
    private TextArea taContact = new TextArea();

    /** Label for displaying an image and a title */
    private Label lblImageTitle = new Label();
    private TextArea taIntro = new TextArea();
    private Label lblMessage = new Label("这里展示的用户都是与您匹配成功的用户，您可以在这里查看他们的联系方式进行后续了解！");
    private Button btPrevious = new Button("<-");
    private Button btNext = new Button("->");
    private Button btDelete = new Button("解除匹配");
    private Button btBackHome = new Button("返回主页");


    private Stage stage = new Stage();

    private String owner;
    private int currentIndex;
    private ArrayList<String> matchesList;

    private ComboBox<String> cboMatches = new ComboBox<>();





    public void display(String userName)
    {
        //对传入信息初始化：把传入的userList赋给属性,初始化记录选择结果
        this.owner = userName;
        try {this.matchesList = new ArrayList<String>(Match.getMatchedMap().get(userName));}
        catch(Exception e){e.printStackTrace(); System.out.println("读取matchedMap出错！");}
        this.currentIndex = 0;






        GridPane leftGridPane= setupLeftGridPane();
        GridPane rightGridPane= setupRightGridPane();


        GridPane midGridPane = new GridPane();
        midGridPane.setHgap(10);
        midGridPane.setVgap(30);
        midGridPane.add(leftGridPane,0,0);
        midGridPane.add(rightGridPane,1,0);
        midGridPane.setAlignment(Pos.CENTER);

        VBox vBox = setupBottomVBox();

        GridPane totalGridPane = new GridPane();
        totalGridPane.setHgap(10);
        totalGridPane.setVgap(70);
        totalGridPane.add( setupTopHBox(),0,0);
        totalGridPane.add( midGridPane,0,1);
        totalGridPane.add(vBox,0,2);
        totalGridPane.setAlignment(Pos.CENTER);

        updatePage(0);//展示第一个用户


        // Create a scene and place it in the stage
        Scene scene = new Scene(totalGridPane, 1500, 700);
        stage.setTitle("用户"+ owner +"的匹配界面"); // Set title
        stage.setScene(scene); // Place the scene in the stage
        stage.setMaximized(true);
        stage.show(); // Display the stage
    }

    private HBox setupTopHBox()
    {
        HBox topHBox = new HBox();
        ObservableList<String> items = FXCollections.observableArrayList(matchesList);
        cboMatches.getItems().addAll(items);
        cboMatches.setOnAction(e -> updatePage(items.indexOf(cboMatches.getValue())));
        cboMatches.setValue(matchesList.get(0));
        cboMatches.setPrefWidth(500);
        topHBox.getChildren().addAll(new Label("选择用户："), cboMatches);
        topHBox.setSpacing(10.0);
        topHBox.setAlignment(Pos.CENTER);
        return topHBox;
    }


    private GridPane setupLeftGridPane()
    {
        //设置联系方式字体
        taContact.setFont(new Font("Times", 14));
        taContact.setWrapText(true);
        taContact.setPrefRowCount(5);

        // 填充左边信息
        GridPane leftGridPane = new GridPane();
        leftGridPane.setHgap(5);
        leftGridPane.setVgap(5);
        leftGridPane.add(new Label("用户名:"), 0, 0);
        leftGridPane.add(lblcurrentSelectee, 1, 0);
        leftGridPane.add(new Label("性别:"), 0, 1);
        leftGridPane.add(lblGender, 1, 1);
        leftGridPane.add(new Label("年龄:"), 0, 2);
        leftGridPane.add(lblAge, 1, 2);
        leftGridPane.add(new Label("照片:"), 0, 3);
        leftGridPane.add(btShowPhoto, 1, 3);
        leftGridPane.add(new Label("联系方式:"), 0, 4);
        leftGridPane.add(taContact, 1, 4);
        taContact.setEditable(false);
        leftGridPane.setAlignment(Pos.CENTER);
        lblAge.setAlignment(Pos.BOTTOM_LEFT);




        // 点击按键，可以预览照片
        btShowPhoto.setOnAction(e -> {lblImageTitle.setGraphic(new ImageView("image/"+ currentMatchPhoto));});
        return leftGridPane;
    }


    private GridPane setupRightGridPane()
    {
        lblImageTitle.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        lblImageTitle.setPrefSize(20,  10);
        lblImageTitle.setFont(new Font("Times", 16));
        lblImageTitle.setOnMouseClicked(e-> lblImageTitle.setGraphic(null));

        taIntro.setFont(new Font("Times", 14));
        taIntro.setWrapText(true);
        taIntro.setPrefRowCount(7);
        taIntro.setEditable(false);


        // C
        GridPane rightGridPane = new GridPane();
        rightGridPane.setHgap(5);
        rightGridPane.setVgap(5);
        rightGridPane.add(lblImageTitle, 0, 0);
        rightGridPane.add(new Label("个人介绍:"), 0, 1);
        rightGridPane.add(taIntro, 0, 2);
        rightGridPane.setAlignment(Pos.CENTER);
        return rightGridPane;
    }

    private VBox setupBottomVBox()
    {
        HBox paneForButtons = new HBox(20);
        paneForButtons.getChildren().addAll(btPrevious, btNext, btBackHome,btDelete);
        paneForButtons.setAlignment(Pos.CENTER);

        btNext.setOnAction(e->
        {
            lblMessage.setText("这里展示的用户都是与您匹配成功的用户，您可以在这里查看他们的联系方式进行后续了解！");
            if(currentIndex==(matchesList.size()-1))
            {
                lblMessage.setText("当前已经为最后一个用户！");
            }
            else
            {
                currentIndex++;
                updatePage(currentIndex);
            }

        });

        btPrevious.setOnAction(e->
        {
            lblMessage.setText("这里展示的用户都是与您匹配成功的用户，您可以在这里查看他们的联系方式进行后续了解！");
            if(currentIndex==0)
            {
                lblMessage.setText("当前已经为第一个用户！");
            }
            else
            {
                currentIndex--;
                updatePage(currentIndex);
            }
        });

        btBackHome.setOnAction(e->
        {
            new HomePage().display(owner);
            stage.close();
        });


        VBox vBox =new VBox(30);
        vBox.getChildren().addAll(paneForButtons, lblMessage);
        vBox.setAlignment(Pos.CENTER); // 布局居中显示

        return vBox;
    }




    private void setUserProperty(String name)
    {
        CheckedUser user = CheckedUser.deserialize(name);
        lblcurrentSelectee.setText(name);
        lblAge.setText(String.valueOf(user.getAge()));
        currentMatchPhoto = user.getPhotoFileName();
        taContact.setText(user.getContactInformation());
        taIntro.setText(user.getIntroduction());
        lblGender.setText(genderDescr[Arrays.asList(genderOptions).indexOf(user.getGender())]);
    }


    /**
     * 通过传入index值更新当前页面
     */
    private void updatePage(int index)
    {
        String matchedUser = matchesList.get(index);
        //更新中间显示selectee信息
        setUserProperty(matchedUser);
        //更新comboBox
        cboMatches.setValue(matchedUser);
    }





}