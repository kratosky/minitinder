package gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import register.Register;
import user.UncheckedUser;

public class Registration extends Application
{
  private Stage stage = new Stage();
  private TextField tfUserName = new TextField();
  private TextField tfPassword = new TextField();
  private Button btLogin = new Button("登录");
  private Button btRegister = new Button("注册");
  private Button btModify = new Button("修改密码");
  private Label lblMessage = new Label("新用户请先注册后登录");
  
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage)
  {
    // Create a scene and place it in the stage
    this.stage = primaryStage;
    Scene scene = new Scene(initPane(), 400, 250);
    primaryStage.setTitle("登录界面"); // Set title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
  }

  //当返回登录界面时调用此方法
  public void display()
  {
    // Create a scene and place it in the stage
    Scene scene = new Scene(initPane(), 400, 250);
    stage.setTitle("登录界面"); // Set title
    stage.setScene(scene); // Place the scene in the stage
    stage.show(); // Display the stage
  }


  private BorderPane initPane()
  {
    HBox paneForButtons = new HBox(20);
    paneForButtons.getChildren().addAll(btRegister,btModify);
    paneForButtons.setAlignment(Pos.CENTER);

    // Create UI
    GridPane gridPane = new GridPane();
    gridPane.setHgap(5);
    gridPane.setVgap(5);
    gridPane.add(new Label("账号:"), 0, 0);
    gridPane.add(tfUserName, 1, 0);
    gridPane.add(new Label("密码:"), 0, 1);
    gridPane.add(tfPassword, 1, 1);
    gridPane.add(btLogin, 0, 2);
    //gridPane.add(btRegister, 1, 2);
    //gridPane.add(btModify, 2, 2);
    gridPane.add(paneForButtons, 1, 2);



    // Set properties for UI
    gridPane.setAlignment(Pos.CENTER);
    tfUserName.setAlignment(Pos.BOTTOM_LEFT);
    tfPassword.setAlignment(Pos.BOTTOM_LEFT);
    GridPane.setHalignment(btLogin, HPos.LEFT);
    //GridPane.setHalignment(btRegister,HPos.RIGHT);
    //GridPane.setHalignment(btModify,HPos.LEFT);


    BorderPane borderPane = new BorderPane();
    //borderPane.setTop(new Label("All codes written by ddx"));
    borderPane.setBottom(lblMessage);
    borderPane.setCenter(gridPane);
    // Process events
    btRegister.setOnAction(e -> register());
    btLogin.setOnAction(e -> login());
    btModify.setOnAction(e-> {new ChangePassWord().display();stage.close();});
    return borderPane;
  }


  private void login()
  {
    // Get values from text fields
    String username = tfUserName.getText();
    String password = tfPassword.getText();
    if(username.isBlank()||password.isBlank())
    {
      lblMessage.setText("用户名和密码不能为空！");
      return;
    }
    try
    {
      //用户名被注册过显示注册过
      if(Register.containsUser(username))
      {
        //无需判断用户是否是审查过的用户，利用多态性序列化取出原本的密码
        String originalPassword = UncheckedUser.deserialize(username).getPassword();

        //密码正确则登录成功
        if(originalPassword.equals(password))
        {
          lblMessage.setText("登录成功!");
          //此时判断用户是否上传过资料决定是否给出弹窗上传资料，还是进入用户主页
          if(UncheckedUser.isUnchecked(username))
          {
            boolean close = UploadReminder.display(username);
            if(close){stage.close();}
          }
          else
          {
            new HomePage().display(username);
            stage.close();
          }

        }
        else
        {
          lblMessage.setText("密码错误，请重新输入!");
        }
      }
      //没有注册过通知用户不存在
      else
      {
        lblMessage.setText("该用户不存在，请先注册再登录!");
      }
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }
  
  private void register()
  {
    // Get values from text fields
    String username = tfUserName.getText();
    String password = tfPassword.getText();
    if(username.isBlank()||password.isBlank())
    {
      lblMessage.setText("用户名和密码不能为空！");
      return;
    }
    try
    {
      //用户名被注册过显示注册过
      if(Register.containsUser(username))
      {
        lblMessage.setText("该用户名已被注册过！请更换用户名!");
      }
      //没有注册过则将newUser序列化，并加入userlist
      else
      {
        //创建未检查用户并序列化存储
        UncheckedUser newUser = new UncheckedUser(username, password);
        newUser.compressSerialize();
        Register.addUser(username);
        lblMessage.setText("已成功注册!");
      }
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }
  
  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}