package gui;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class UploadReminder
{
    private boolean closeRegistration;
    /**
     * 展示提醒窗口
     * @param userName
     */
    public boolean display(String userName)
    {

        closeRegistration = false;
        // 创建舞台
        Stage stage = new Stage();
        // 设置显示模式
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("上传提醒");
        Label label = new Label("您还未上传匹配信息，是否现在上传？");
        // 创建控件
        Button buttonYes = new Button("上传");
        buttonYes.setOnMouseClicked(event -> {
            new UploadInformation().display(userName);
            closeRegistration = true;
            stage.close();
        });

        Button buttonNo = new Button("取消");
        buttonNo.setOnMouseClicked(event -> {
            stage.close();
        });

        // Create UI
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.add(label, 0, 0);
        gridPane.add(buttonYes, 0, 2);
        gridPane.add(buttonNo, 1, 2);


        // Set properties for UI
        gridPane.setAlignment(Pos.CENTER);
        GridPane.setHalignment(label, HPos.CENTER);
        GridPane.setHalignment(buttonYes, HPos.LEFT);
        GridPane.setHalignment(buttonNo,HPos.RIGHT);


        // 创建场景
        Scene scene = new Scene(gridPane, 300, 150);

        // 显示舞台
        stage.setScene(scene);
        //stage.show();
        stage.showAndWait();  // 等待窗体关闭才继续
        return closeRegistration;
    }
}


