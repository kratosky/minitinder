package gui;

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

import java.util.Arrays;

import static user.Gender.*;

public class HomePage
{
    private final String[] genderDescr = {"男", "女", "其他"};
    private final Gender[] genderOptions = {Male, Female, Other};
    private Gender chosenGender = Male;
    private Label lblGender = new Label();
    private Label lblYears = new Label();
    private Label lblPhotoFileName = new Label();
    private Button btShowPhoto = new Button("展示");
    private TextArea taContact = new TextArea();
    /** Label for displaying an image and a title */
    private Label lblImageTitle = new Label();
    private Label lblOpportunities = new Label();
    private Label lblLikeOnes = new Label();
    private TextArea taIntro = new TextArea();
    private Label lblMessage = new Label("成功进入个人主页！");
    private Button btUpdate = new Button("更新资料");
    private Button btRecharge = new Button("充值");
    private Button btMatchedOnes = new Button("已匹配用户");
    private Button btStart = new Button("开始匹配");
    private Button btQuit = new Button("退出账号");
    private Button btDeregistration = new Button("注销账户");

    private Stage stage = new Stage();




    public void display(String username)
    {
        //将用户资料完全展现
        setUserProperty(username);

        GridPane leftGridPane= setupLeftGridPane();
        GridPane rightGridPane= setupRightGridPane();


        GridPane midGridPane = new GridPane();
        midGridPane.setHgap(10);
        midGridPane.setVgap(30);
        midGridPane.add(leftGridPane,0,0);
        midGridPane.add(rightGridPane,1,0);
        midGridPane.setAlignment(Pos.CENTER);

        VBox vBox = setupBottomVBox(username);

        GridPane totalGridPane = new GridPane();
        totalGridPane.setHgap(10);
        totalGridPane.setVgap(70);
        totalGridPane.add( midGridPane,0,0);
        totalGridPane.add(vBox,0,1);
        totalGridPane.setAlignment(Pos.CENTER);




        // Create a scene and place it in the stage
        Scene scene = new Scene(totalGridPane, 1500, 700);
        stage.setTitle("个人主页"); // Set title
        stage.setScene(scene); // Place the scene in the stage
        stage.setMaximized(true);
        stage.show(); // Display the stage
    }


    private GridPane setupLeftGridPane()
    {
        //设置联系方式字体
        taContact.setFont(new Font("Times", 14));
        taContact.setWrapText(true);
        taContact.setPrefRowCount(4);


        // 填充左边信息
        GridPane leftGridPane = new GridPane();
        leftGridPane.setHgap(5);
        leftGridPane.setVgap(5);
        leftGridPane.add(new Label("性别:"), 0, 0);
        leftGridPane.add(lblGender, 1, 0);
        leftGridPane.add(new Label("出生年份:"), 0, 1);
        leftGridPane.add(lblYears, 1, 1);
        leftGridPane.add(new Label("照片:"), 0, 2);
        HBox photoHBox = new HBox();
        photoHBox.getChildren().addAll(lblPhotoFileName,btShowPhoto);
        photoHBox.setSpacing(10.0);
        leftGridPane.add(photoHBox, 1, 2);
        leftGridPane.add(new Label("联系方式:"), 0, 3);
        leftGridPane.add(taContact, 1, 3);
        taContact.setEditable(false);
        leftGridPane.setAlignment(Pos.CENTER);
        lblYears.setAlignment(Pos.BOTTOM_LEFT);




        // 输入照片回车，可以预览照片
        btShowPhoto.setOnAction(e -> {lblImageTitle.setGraphic(new ImageView("image/"+ lblPhotoFileName.getText()));});
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
        taIntro.setPrefRowCount(5);
        taIntro.setEditable(false);


        // C
        GridPane rightGridPane = new GridPane();
        rightGridPane.setHgap(5);
        rightGridPane.setVgap(5);
        rightGridPane.add(lblImageTitle, 0, 0);
        rightGridPane.add(new Label("剩余浏览用户个数:"), 0, 1);
        rightGridPane.add(lblOpportunities, 1, 1);
        rightGridPane.add(new Label("剩余喜欢次数:"), 0, 2);
        rightGridPane.add(lblLikeOnes, 1, 2);
        rightGridPane.add(new Label("个人介绍:"), 0, 3);
        rightGridPane.add(taIntro, 1, 3);
        rightGridPane.setAlignment(Pos.CENTER);
        return rightGridPane;
    }

    private VBox setupBottomVBox(String username)
    {
        HBox paneForButtons = new HBox(20);
        paneForButtons.getChildren().addAll(btUpdate,btRecharge, btMatchedOnes,btStart,btQuit,btDeregistration);
        paneForButtons.setAlignment(Pos.CENTER);

        btRecharge.setOnAction(e->{if(new Buy().display(username)){updateResource(username);}});
        btUpdate.setOnAction(e->{new UploadInformation().display(username);stage.close();});
        btQuit.setOnAction(e->{new Registration().display();stage.close();});
        btDeregistration.setOnAction(e->
        {
            boolean close = new DeregistrationAlert().display(username);
            if(close){stage.close();}
        });
        btStart.setOnAction(e->
        {
            //如果成功开始匹配选择，则关闭个人页面
            if(SelectionSetting.display(username,Integer.parseInt(lblOpportunities.getText()),Integer.parseInt(lblLikeOnes.getText())))
            {
                stage.close();
            }
        });
        btMatchedOnes.setOnAction(e->
        {
            try
            {
                //如果用户数大于等于1，则展示所有匹配用户
                if(Match.getMatchedNumber(username)==0)
                {
                    lblMessage.setText("目前没有已匹配用户！");
                }
                else
                {
                    new MatchedOnes().display(username);
                    stage.close();
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                System.out.println("读取matchedMap时出现问题！");
            }


        });



        VBox vBox =new VBox(30);
        vBox.getChildren().addAll(paneForButtons, lblMessage);
        vBox.setAlignment(Pos.CENTER); // 布局居中显示

        return vBox;
    }




    private void setUserProperty(String name)
    {
        CheckedUser user = CheckedUser.deserialize(name);
        lblYears.setText(String.valueOf(user.getBirthYear()));
        lblPhotoFileName.setText(user.getPhotoFileName());
        lblOpportunities.setText(String.valueOf(user.getOpportunities()));
        lblLikeOnes.setText(String.valueOf(user.getLikeOnes()));
        taIntro.setText(user.getIntroduction());
        taContact.setText(user.getContactInformation());
        lblGender.setText(genderDescr[Arrays.asList(genderOptions).indexOf(user.getGender())]);
    }


    /**
     * 在充值结束后，如果充值了的话，更新资源值
     */
    public void updateResource(String name)
    {
        CheckedUser user = CheckedUser.deserialize(name);
        lblOpportunities.setText(String.valueOf(user.getOpportunities()));
        lblLikeOnes.setText(String.valueOf(user.getLikeOnes()));
    }

}

