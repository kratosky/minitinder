package gui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import match_operation.Match;
import match_operation.Selection;
import match_operation.Condition;
import user.Gender;

import java.util.HashSet;
import java.util.Set;


import static user.Gender.*;


public class SelectionSetting
{
    private static TextField tfMinAge = new TextField();
    private static TextField tfMaxAge = new TextField();
    private static TextField tfChances = new TextField();
    private static CheckBox chkMale = new CheckBox("Male");
    private static CheckBox chkFemale = new CheckBox("Female");
    private static CheckBox chkOther = new CheckBox("Other");
    private static Button btStart = new Button("开始匹配");
    private static Button btBack = new Button("取消");
    private static Label lblMessage = new Label("请分别输入匹配筛选条件与本次浏览用户数！");
    private static Stage stage = new Stage();
    private static boolean startSelection;

    public static boolean display(String username, int maxChances, int maxLikes)
    {
        startSelection = false;

        clear();
        //stage.initModality(Modality.APPLICATION_MODAL);
        // Create UI
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.add(new Label("可接受最小年龄:"), 0, 0);
        gridPane.add(tfMinAge, 1, 0);
        gridPane.add(new Label("可接受最大年龄:"), 0, 1);
        gridPane.add(tfMaxAge, 1, 1);
        gridPane.add(new Label("可接受性别:"), 0, 2);
        gridPane.add(setGenderBox(), 1, 2);
        gridPane.add(new Label("本次期望浏览用户数:"), 0, 4);
        gridPane.add(tfChances, 1, 4);
        gridPane.add(btStart, 0, 8);
        gridPane.add(btBack, 1, 8);

        // Set properties for UI
        gridPane.setAlignment(Pos.CENTER);
        tfMinAge.setAlignment(Pos.BOTTOM_LEFT);
        tfMaxAge.setAlignment(Pos.BOTTOM_LEFT);
        tfChances.setAlignment(Pos.BOTTOM_LEFT);
        GridPane.setHalignment(btBack, HPos.RIGHT);

        // Process events
        btStart.setOnAction(e ->
        {
            if(tryStartSelection(username,maxChances,maxLikes))
            {
                startSelection = true;
                stage.close();
            }
        });
        btBack.setOnAction(e -> stage.close());

        BorderPane borderPane = new BorderPane();
        //borderPane.setTop(new Label("All codes written by ddx"));
        borderPane.setBottom(lblMessage);
        borderPane.setCenter(gridPane);

        // Create a scene and place it in the stage
        Scene scene = new Scene(borderPane, 550, 220);
        stage.setTitle("用户"+username+"的匹配条件设置"); // Set title
        stage.setScene(scene); // Place the scene in the stage
        stage.showAndWait();  // 等待窗体关闭才继续

        // 窗体返回值
        return startSelection;
    }

    private static HBox setGenderBox()
    {
        HBox genderSelection = new HBox();
        genderSelection.setPadding(new Insets(5, 5, 5, 5));
        genderSelection.getChildren().addAll(chkMale, chkFemale,chkOther);

        return genderSelection;
    }

    /**
     * 从genderbox中获得当前所选的性别集合
     * @return
     */
    private static Set<Gender> getGenderSelection()
    {
        Set<Gender> genderSelection= new HashSet<>();
        if(chkMale.isSelected()){genderSelection.add(Male);}
        if(chkFemale.isSelected()){genderSelection.add(Female);}
        if(chkOther.isSelected()){genderSelection.add(Other);}
        return genderSelection;
    }

    /**
     * 从genderbox中获得当前所选的性别集合,判断是否合法即不为空
     * @return
     */
    private static boolean isValidGenderSelection()
    {
        boolean result = (chkMale.isSelected()||chkFemale.isSelected()||chkOther.isSelected());
        if(!result){lblMessage.setText("请至少选择一种性别！");}
        return result;

    }

    /**
     * 重复点击时，清空并关掉上一个
     */
    public static void clearAndClose()
    {
        clear();
        stage.close();
    }

    /**
     * 完成支付时，清空
     */
    public static void clear()
    {
        tfMinAge.setText("");
        tfMaxAge.setText("");
        tfChances.setText("");
        chkMale.setSelected(false);
        chkFemale.setSelected(false);
        chkOther.setSelected(false);
        lblMessage.setText("请分别输入匹配筛选条件与本次浏览用户数！");

    }


    /**
     * 尝试开始选择，返回是否成功
     */
    private static boolean tryStartSelection(String username, int maxChances, int maxLikes)
    {
        //输入年龄范围与浏览数目
        if(isValidAgeRange()&&isValidChances(username,maxChances)&& isValidGenderSelection())
        {
            Condition condition = new Condition(getGenderSelection(),Integer.parseInt(tfMinAge.getText()),Integer.parseInt(tfMaxAge.getText()));
            Selection selection = new Selection(username,Integer.parseInt(tfChances.getText()),maxLikes,condition);
            boolean confirmSelect = new SelectionConfirmation().display(selection);
            if(confirmSelect)
            {
                SelectionSetting.clear();
                return true;
            }
        }
        return false;

    }





    /**
     * 判断传入的浏览数合法，即为一个正整数，且不超过该用户拥有资源数，
     * 且不超过数据库中该用户所有可以匹配的用户数（passedMap键数减去该用户对应集合数）
     * @return
     */
    private static boolean isValidChances(String userName,int maxChances)
    {
        //先判断是正整数
        if(!isPositiveInt(tfChances.getText())){ lblMessage.setText("浏览用户次数输入不合法，请输入一个正整数!"); return false;}
        //再判断不超过该用户最高可浏览资源
        int chances = Integer.parseInt(tfChances.getText());
        if(chances > maxChances){ lblMessage.setText("本次浏览用户次数超过该用户当前拥有资源数，请重新输入!"); return false;}
        //最后判断不超过系统可分配给该用户资源数
        try
        {
            if(chances > Match.getMaxChances(userName)){lblMessage.setText("本次浏览用户次数超过系统当前可分配给该用户资源数，请重新输入!"); return false;}
        }
        catch(Exception e)
        {
            e.printStackTrace();
            lblMessage.setText("判断输入浏览用户数时读取passedMap失败！");
            return false;
        }

        return true;
    }


    /**
     * 判断传入的最大最小年龄是否符合要求，首先得是正整数，其次最小年龄不大于最大年龄，同时输入年龄不超过系统允许的范围
     * @return
     */
    private static boolean isValidAgeRange()
    {
        //先判断是正整数
        if(!isPositiveInt(tfMinAge.getText())){ lblMessage.setText("最小年龄输入不合法，请输入一个正整数!"); return false;}
        if(!isPositiveInt(tfMaxAge.getText())){ lblMessage.setText("最大年龄输入不合法，请输入一个正整数!"); return false;}
        //再判断最小年龄不大于最大年龄
        int minAge = Integer.parseInt(tfMinAge.getText());
        int maxAge = Integer.parseInt(tfMaxAge.getText());
        if(minAge > maxAge){ lblMessage.setText("最小年龄不应大于最大年龄，请重新输入!"); return false;}
        //最后判断输入年龄不超过系统允许的范围
        if(minAge<18)
        {
            lblMessage.setText("最小年龄不应低于18岁！");
            return false;
        }
        if(maxAge>99)
        {
            lblMessage.setText("最大年龄不应高于99岁！");
            return false;
        }
        //都没有问题返回true
        return true;
    }






    /**
     * 用正则表达式判断字符串为正整数
     * @param toCheck
     * @return
     */
    private static boolean isPositiveInt(String toCheck)
    {
        boolean result = true;
        if(toCheck.matches("[\\d]+"))
        {
            if(Integer.parseInt(toCheck)==0){result = false;}
        }
        else
        {
            result = false;
        }
        return result;
    }



}
