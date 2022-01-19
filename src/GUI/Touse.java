package GUI;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import register.Register;
import user.UncheckedUser;

public class Touse
{
    private Stage stage;
    private TextField tfUserName = new TextField();
    private TextField tfPassword = new TextField();
    private Button btLogin = new Button("登录");
    private Button btRegister = new Button("注册");
    private Label lblMessage = new Label("新用户请先注册后登录");

    public static void display()
    {
        Stage primaryStage = new Stage();
        // 布局
        Button button = new Button("title");
        Label label = new Label("msg");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(button,label);
        Scene scene = new Scene(vBox, 400, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private BorderPane initPane()
    {
        // Create UI
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.add(new Label("账号:"), 0, 0);
        gridPane.add(tfUserName, 1, 0);
        gridPane.add(new Label("密码:"), 0, 1);
        gridPane.add(tfPassword, 1, 1);
        gridPane.add(btLogin, 0, 2);
        gridPane.add(btRegister, 1, 2);


        // Set properties for UI
        gridPane.setAlignment(Pos.CENTER);
        tfUserName.setAlignment(Pos.BOTTOM_LEFT);
        tfPassword.setAlignment(Pos.BOTTOM_LEFT);
        GridPane.setHalignment(btLogin, HPos.LEFT);
        GridPane.setHalignment(btRegister,HPos.RIGHT);


        BorderPane borderPane = new BorderPane();
        //borderPane.setTop(new Label("All codes written by ddx"));
        borderPane.setBottom(lblMessage);
        borderPane.setCenter(gridPane);
        // Process events
        btRegister.setOnAction(e -> register());

        return borderPane;
    }

    private void register()
    {
        // Get values from text fields
        String username = tfUserName.getText();
        String password = tfPassword.getText();
        try
        {
            //用户名被注册过显示注册过
            if(Register.containsUser(username))
            {
                lblMessage.setText("该用户名已被注册过！请更换用户名");
            }
            //没有注册过则将newUser序列化，并加入userlist
            else
            {
                //创建未检查用户并序列化存储
                UncheckedUser newUser = new UncheckedUser(username, password);
                newUser.compressSerialize();
                Register.addUser(username);
                lblMessage.setText("已成功注册");
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }


    private void login()
    {
        // Get values from text fields
        String username = tfUserName.getText();
        String password = tfPassword.getText();
        try
        {
            //用户名被注册过显示注册过
            if(Register.containsUser(username))
            {
                lblMessage.setText("该用户名已被注册过！请更换用户名");
            }
            //没有注册过则将newUser序列化，并加入userlist
            else
            {
                //创建未检查用户并序列化存储
                UncheckedUser newUser = new UncheckedUser(username, password);
                newUser.compressSerialize();
                Register.addUser(username);
                lblMessage.setText("已成功登录");
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
