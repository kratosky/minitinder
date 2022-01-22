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
import user.CheckedUser;
import user.Gender;
import user.UncheckedUser;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;

import user_management.CheckedUserManage;

import static user.Gender.*;

public class UploadInformation
{
    private final String[] genderDescr = {"男", "女", "其他"};
    private final Gender[] genderOptions = {Male, Female, Other};
    private Gender chosenGender = Male;
    private ComboBox<String> cbo = new ComboBox<>();
    private TextField tfYears = new TextField();
    private TextField tfPhotoFileName = new TextField();
    private Button btShowPhoto = new Button("预览");
    private TextArea taContact = new TextArea();
    /** Label for displaying an image and a title */
    private Label lblImageTitle = new Label();
    private TextArea taIntro = new TextArea();
    private Label lblMessage = new Label("上传资料，审核通过后，即可进入个人主页开始匹配！");
    private Button btUpdate = new Button("上传");
    private Button btHomePage = new Button("进入个人主页");
    private Button btQuit = new Button("退出");
    private Stage stage = new Stage();


    public void display(String username)
    {
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

        //如果是修改用户资料，则将各个条目设为原始值，方便修改
        if(!UncheckedUser.isUnchecked(username))
        {
            originalPropertySet(username);
        }

        // Create a scene and place it in the stage
        Scene scene = new Scene(totalGridPane, 1500, 700);
        stage.setTitle("用户"+username+"的资料上传界面"); // Set title
        stage.setScene(scene); // Place the scene in the stage
        stage.setMaximized(true);
        stage.show(); // Display the stage
    }


    private GridPane setupLeftGridPane()
    {
        cbo.setValue("男");
        ObservableList<String> items = FXCollections.observableArrayList(genderDescr);
        cbo.getItems().addAll(items);
        // 处理选中的性别
        cbo.setOnAction(e -> setGender(items.indexOf(cbo.getValue())));

        //设置联系方式字体
        taContact.setFont(new Font("Times", 14));
        taContact.setWrapText(true);
        taContact.setPrefRowCount(2);

        // 填充左边信息
        GridPane leftGridPane = new GridPane();
        leftGridPane.setHgap(5);
        leftGridPane.setVgap(5);
        leftGridPane.add(new Label("性别:"), 0, 0);
        leftGridPane.add(cbo, 1, 0);
        leftGridPane.add(new Label("出生年份:"), 0, 1);
        leftGridPane.add(tfYears, 1, 1);
        leftGridPane.add(new Label("上传照片:"), 0, 2);
        leftGridPane.add(tfPhotoFileName, 1, 2);
        leftGridPane.add(btShowPhoto, 2, 2);
        leftGridPane.add(new Label("联系方式:"), 0, 3);
        leftGridPane.add(taContact, 1, 3);
        leftGridPane.setAlignment(Pos.CENTER);
        tfYears.setAlignment(Pos.BOTTOM_LEFT);
        tfPhotoFileName.setAlignment(Pos.BOTTOM_LEFT);

        // 输入照片回车，可以预览照片
        tfPhotoFileName.setOnAction(e -> lblImageTitle.setGraphic(new ImageView("image/"+ tfPhotoFileName.getText())));
        // 点击对钩，可以预览照片
        btShowPhoto.setOnAction(e -> {lblImageTitle.setGraphic(new ImageView("image/"+ tfPhotoFileName.getText()));});
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
        ScrollPane scrollPane = new ScrollPane(taIntro);


        // C
        GridPane rightGridPane = new GridPane();
        rightGridPane.setHgap(5);
        rightGridPane.setVgap(5);
        rightGridPane.add(lblImageTitle, 0, 0);
        rightGridPane.add(new Label("个人介绍:"), 0, 1);
        rightGridPane.add(scrollPane, 1, 1);
        rightGridPane.setAlignment(Pos.CENTER);
        return rightGridPane;
    }

    private VBox setupBottomVBox(String username)
    {
        HBox paneForButtons = new HBox(20);
        paneForButtons.getChildren().addAll(btUpdate, btHomePage,btQuit);
        paneForButtons.setAlignment(Pos.CENTER);

        btUpdate.setOnAction(e->handleUpdate(username));
        btHomePage.setOnAction(e->
        {
            if(UncheckedUser.isUnchecked(username)){lblMessage.setText("上传信息通过审核后才能进入个人主页！");}
            else{new HomePage().display(username);stage.close();}
        });
        btQuit.setOnAction(e->{new Registration().display();stage.close();});

        VBox vBox =new VBox(30);
        vBox.getChildren().addAll(paneForButtons, lblMessage);
        vBox.setAlignment(Pos.CENTER); // 布局居中显示

        return vBox;
    }


    private void setGender(int index)
    {
        this.chosenGender = genderOptions[index];
        //System.out.println(index);
    }


    private void originalPropertySet(String name)
    {
        CheckedUser user = CheckedUser.deserialize(name);
        tfYears.setText(String.valueOf(user.getBirthYear()));
        tfPhotoFileName.setText(user.getPhotoFileName());
        taIntro.setText(user.getIntroduction());
        taContact.setText(user.getContactInformation());
        cbo.setValue(genderDescr[Arrays.asList(genderOptions).indexOf(user.getGender())]);
    }


    private void handleUpdate(String username)
    {
        // Get values from text fields
        String birthYearString = tfYears.getText();
        String photoFileName = tfPhotoFileName.getText();
        String introduction = taIntro.getText();
        String contactInformation = taContact.getText();
        Gender gender = this.chosenGender;
        InformationCheck check = new InformationCheck(birthYearString,photoFileName, introduction,contactInformation );
        if(check.isValid())
        {
            int birthYear = Integer.parseInt(birthYearString);
            //d当检查通过，当前用户必然已经是注册成功的用户，d进一步判断该用户是审核过用户（更新），还是未审核用户（创建）x
            if(UncheckedUser.isUnchecked(username))
            {
                CheckedUserManage.createCheckedUser(username,birthYear, gender,photoFileName,introduction,contactInformation);
            }
            else
            {
                CheckedUserManage.updateCheckedUser(username,birthYear, gender,photoFileName,introduction,contactInformation);
            }

        }


    }




    //审查内部类
    private class InformationCheck
    {
        private String birthYearString;
        private String photoFileName;
        private String introduction;
        private String contactInformation;

        public InformationCheck(String birthYearString, String photoFileName, String introduction, String contactInformation)
        {
            this.birthYearString = birthYearString;
            this.photoFileName = photoFileName;
            this.introduction = introduction;
            this.contactInformation = contactInformation;
        }

        public boolean isValid()
        {
            lblMessage.setText("");
            boolean isValid = isBirthYearValid()&isPhotoFileNameValid()&isIntroductionValid()&isContactInformationValid();
            if(isValid){lblMessage.setText("资料审核通过！");}
            return isValid;
        }


        //判断年龄是否符合
        private boolean isBirthYearValid()
        {
            if(!birthYearString.matches("(19|20)[\\d]{2}"))
            {
                lblMessage.setText("输入年份不合法，请输入四位数字年份！");
                return false;
            }
            else
            {
                int birthYear = Integer.parseInt(birthYearString);
                Calendar cal = Calendar.getInstance();
                int currentYear = cal.get(Calendar.YEAR);
                int age = currentYear - birthYear +1;
                if(age<18)
                {
                    lblMessage.setText("您年龄太小，请专心学习！");
                    return false;
                }
                else if(age>99)
                {
                    lblMessage.setText("您年岁已高，请保重身体！");
                    return false;
                }
                else {return true;}
            }


        }


        private boolean isPhotoFileNameValid()
        {
            File userPhoto = new File("src/image/"+photoFileName);
            if(!userPhoto.isFile())
            {
                lblMessage.setText(lblMessage.getText()+"  "+"传入照片不合法，请重新传入！");
                return false;
            }
            else
            {
                return true;
            }

        }

        private boolean isIntroductionValid()
        {
            if(introduction.length()<15)
            {
                lblMessage.setText(lblMessage.getText()+"  "+"自我介绍至少为15个字符，请修改！");
                return false;
            }
            else if(introduction.length()>5000)
            {
                lblMessage.setText(lblMessage.getText()+"  "+"自我介绍最多5000个字符，请修改！");
                return false;
            }
            else
            {
                return true;
            }

        }

        private boolean isContactInformationValid()
        {
            if(contactInformation.length()<10)
            {
                lblMessage.setText(lblMessage.getText()+"  "+"联系信息至少为10个字符，请修改！");
                return false;
            }
            else if(contactInformation.length()>150)
            {
                lblMessage.setText(lblMessage.getText()+"  "+"联系信息最多150个字符，请修改！");
                return false;
            }
            else
            {
                return true;
            }

        }


    }

}
