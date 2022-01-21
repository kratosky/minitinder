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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static user.Gender.*;
import static user.Gender.Male;

public class Matching
{
    private final String[] genderDescr = {"男", "女", "其他"};
    private final Gender[] genderOptions = {Male, Female, Other};
    private Gender chosenGender = Male;

    private Label lblcurrentSelectee = new Label();
    private Label lblGender = new Label();
    private Label lblAge = new Label();
    private String currentSelecteePhoto;
    private Button btShowPhoto = new Button("展示照片");

    /** Label for displaying an image and a title */
    private Label lblImageTitle = new Label();
    private TextArea taIntro = new TextArea();
    private Label lblMessage = new Label("请对每一位选择喜欢，不喜欢，或不确定，完成匹配后点击提交！");
    private Button btPrevious = new Button("<-");
    private Button btNext = new Button("->");
    private Button btConfirm = new Button("完成选择");


    private Stage stage = new Stage();

    private String selector;
    private int leftLikes;
    private int currentIndex;
    private CheckBox chkLike = new CheckBox("喜欢");
    private CheckBox chkDislike = new CheckBox("不喜欢");
    private CheckBox chkNotSure = new CheckBox("不确定");
    private ArrayList<String> selecteeList;
    private Map<String,Integer> selectionRecord;

    private ComboBox<String> cboSelectees = new ComboBox<>();
    private Label lblLeftLikes = new Label();

    private Map<String,Integer> initSelectionRecord(ArrayList<String> userToSelect)
    {
        Map<String,Integer> selectionRecord = new HashMap<>();
        userToSelect.forEach(userName ->
        {
            selectionRecord.put(userName, 0);
        });
        return selectionRecord;
    }





    public void display(String userName, int maxLikes, ArrayList<String> userToSelect)
    {
        //对传入信息初始化：把传入的userList赋给属性,初始化记录选择结果
        this.selector = userName;
        this.selecteeList = userToSelect;
        this.currentIndex = 0;
        this.leftLikes = maxLikes;
        this.lblLeftLikes.setText(String.valueOf(maxLikes));
        this.selectionRecord = initSelectionRecord(userToSelect);


        //将用户资料完全展现
        setUserProperty(userName);

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




        // Create a scene and place it in the stage
        Scene scene = new Scene(totalGridPane, 1500, 400);
        stage.setTitle("个人主页"); // Set title
        stage.setScene(scene); // Place the scene in the stage
        stage.show(); // Display the stage
    }

    private HBox setupTopHBox()
    {
        HBox topHBox = new HBox();
        ObservableList<String> items = FXCollections.observableArrayList(selecteeList);
        cboSelectees.getItems().addAll(items);
        cboSelectees.setOnAction(e -> updatePage(items.indexOf(cboSelectees.getValue())));
        cboSelectees.setValue(selecteeList.get(0));
        cboSelectees.setPrefWidth(500);
        topHBox.getChildren().addAll(new Label("选择用户："),cboSelectees,new Label("剩余喜欢次数："),lblLeftLikes);
        return topHBox;
    }


    private GridPane setupLeftGridPane()
    {

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
        leftGridPane.setAlignment(Pos.CENTER);
        lblAge.setAlignment(Pos.BOTTOM_LEFT);




        // 输入照片回车，可以预览照片
        btShowPhoto.setOnAction(e -> {lblImageTitle.setGraphic(new ImageView("image/"+ currentSelecteePhoto));});
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
        paneForButtons.getChildren().addAll(chkLike,chkDislike,chkNotSure,btPrevious, btNext,btConfirm);
        paneForButtons.setAlignment(Pos.CENTER);

        chkLike.setOnAction(e->
                {
                    likesChangeBy(-1);
                    setCheckBox(1);
                    selectionRecord.put(lblcurrentSelectee.getText(),1);

                });
        chkDislike.setOnAction(e->
                {
                    if(chkLike.isSelected()){likesChangeBy(1);}//改变了原本的Like会增加剩余like数量
                    setCheckBox(-2);
                    selectionRecord.put(lblcurrentSelectee.getText(),-2);
                });
        chkNotSure.setOnAction(e->
                {
                    if(chkLike.isSelected()){likesChangeBy(1);}//改变了原本的Like会增加剩余like数量
                    setCheckBox(-1);
                    selectionRecord.put(lblcurrentSelectee.getText(),-1);
                });

        btNext.setOnAction(e->
        {
            lblMessage.setText("请对每一位选择喜欢，不喜欢，或不确定，完成匹配后点击提交！");
            if(currentIndex==(selecteeList.size()-1))
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
            lblMessage.setText("请对每一位选择喜欢，不喜欢，或不确定，完成匹配后点击提交！");
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

        btConfirm.setOnAction(e->
        {
            /*
            if(SelectionSetting.display(username,Integer.parseInt(lblOpportunities.getText()),Integer.parseInt(lblLikeOnes.getText())))
            {
                stage.close();
            }*/
        });


        VBox vBox =new VBox(30);
        vBox.getChildren().addAll(paneForButtons, lblMessage);
        vBox.setAlignment(Pos.CENTER); // 布局居中显示

        return vBox;
    }

    /** Set display information on the description pane
    public void setDisplay(int index) {
        descriptionPane.setTitle(flagTitles[index]);
        descriptionPane.setImageView(flagImage[index]);
        descriptionPane.setDescription(flagDescription[index]);
    }
     */

    /**
     * 对剩余的喜欢数进行改变
     * @param i
     */
    private void likesChangeBy(int i)
    {
        leftLikes = leftLikes + i;
        lblLeftLikes.setText(String.valueOf(leftLikes));
    }


    private void setUserProperty(String name)
    {
        CheckedUser user = CheckedUser.deserialize(name);
        lblcurrentSelectee.setText(name);
        lblAge.setText(String.valueOf(user.getAge()));
        currentSelecteePhoto = user.getPhotoFileName();
        taIntro.setText(user.getIntroduction());
        lblGender.setText(genderDescr[Arrays.asList(genderOptions).indexOf(user.getGender())]);
    }


    /**
     * 通过传入index值更新当前页面
     */
    private void updatePage(int index)
    {
        String selectee = selecteeList.get(index);
        //更新中间显示selectee信息
        setUserProperty(selectee);
        //更新checkBox
        setCheckBox(selectionRecord.get(selectee));
        //更新comboBox
        cboSelectees.setValue(selectee);
    }

    /**
     * 通过传入signal值更新checkBox情况
     */
    private void setCheckBox(int signal)
    {
        if(signal==0)
        {
            chkLike.setSelected(false);
            chkDislike.setSelected(false);
            chkNotSure.setSelected(false);
        }
        else if(signal == 1)
        {
            chkLike.setSelected(true);
            chkDislike.setSelected(false);
            chkNotSure.setSelected(false);
        }
        else if(signal== -1)
        {
            chkLike.setSelected(false);
            chkDislike.setSelected(false);
            chkNotSure.setSelected(true);
        }
        else
        {
            chkLike.setSelected(false);
            chkDislike.setSelected(true);
            chkNotSure.setSelected(false);
        }

    }




}