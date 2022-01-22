package gui;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import user_management.UserList;
import user.UncheckedUser;

public class ChangePassWord
{

    private Stage stage = new Stage();
    private TextField tfUserName = new TextField();
    private PasswordField pfOriginalPassword = new PasswordField();
    private PasswordField pfNewPassword = new PasswordField();
    private Button btModify = new Button("修改密码");
    private Button btReturn = new Button("返回登录界面");
    private Label lblMessage = new Label("请在正确输入原密码后修改新密码！");

    //当返回登录界面时调用此方法
    public void display()
    {
        // Create a scene and place it in the stage
        Scene scene = new Scene(initPane(), 500, 250);
        stage.setTitle("修改密码"); // Set title
        stage.setScene(scene); // Place the scene in the stage
        stage.show(); // Display the stage
    }


    private BorderPane initPane()
    {
        // Create UI
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.add(new Label("账号:"), 0, 0);
        gridPane.add(tfUserName, 1, 0);
        gridPane.add(new Label("原密码:"), 0, 1);
        gridPane.add(pfOriginalPassword, 1, 1);
        gridPane.add(new Label("新密码:"), 0, 2);
        gridPane.add(pfNewPassword, 1, 2);
        gridPane.add(btModify, 0, 3);
        gridPane.add(btReturn, 1, 3);


        // Set properties for UI
        gridPane.setAlignment(Pos.CENTER);
        tfUserName.setAlignment(Pos.BOTTOM_LEFT);
        pfOriginalPassword.setAlignment(Pos.BOTTOM_LEFT);
        GridPane.setHalignment(btModify, HPos.LEFT);
        GridPane.setHalignment(btReturn,HPos.RIGHT);


        BorderPane borderPane = new BorderPane();
        //borderPane.setTop(new Label("All codes written by ddx"));
        borderPane.setBottom(lblMessage);
        borderPane.setCenter(gridPane);
        // Process events
        btReturn.setOnAction(e -> returnRegister());
        btModify.setOnAction(e -> modify());
        return borderPane;
    }


    private void modify()
    {
        // Get values from text fields
        String username = tfUserName.getText();
        String password = pfOriginalPassword.getText();
        try
        {
            //用户名被注册过显示注册过
            if(UserList.containsUser(username))
            {
                //无需判断用户是否是审查过的用户，利用多态性序列化取出原本的密码
                UncheckedUser user = UncheckedUser.deserialize(username);
                String originalPassword = user.getPassword();
                //密码正确则修改成功
                if(originalPassword.equals(password))
                {
                    lblMessage.setText("修改成功!");
                    user.setPassword(pfNewPassword.getText());
                    user.compressSerialize();
                }
                else
                {
                    lblMessage.setText("原密码错误，请重新输入!");
                }
            }
            //没有注册过通知用户不存在
            else
            {
                lblMessage.setText("该用户不存在，请先注册再修改密码!");
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void returnRegister()
    {
        new Registration().display();
        stage.close();
    }


}
