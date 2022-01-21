package gui;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import match_operation.Match;

public class DeleteMatchAlert
{
    private boolean isDeleted;
    /**
     * 展示提醒窗口
     */
    public boolean display(String selector,String selectee)
    {

        isDeleted = false;
        // 创建舞台
        Stage stage = new Stage();
        // 设置显示模式
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("解除匹配警告");
        Label label = new Label("解除与该用户的匹配后，你们将无法查看彼此资料及联系方式，之后也不会再出现在彼此的匹配推送中，是否确认解除匹配？");
        // 创建控件
        Button buttonYes = new Button("确认解除");
        buttonYes.setOnMouseClicked(event -> {
            try { Match.removeMatchedRelationship(selector,selectee);}
            catch (Exception e) { e.printStackTrace();
                System.out.println("解除匹配关系读取MatchedMap时出现问题！");}
            new MatchedOnes().display(selector);
            isDeleted = true;
            stage.close();
        });

        Button buttonNo = new Button("再想想");
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
        Scene scene = new Scene(gridPane, 900, 150);

        // 显示舞台
        stage.setScene(scene);
        //stage.show();
        stage.showAndWait();  // 等待窗体关闭才继续
        return isDeleted;
    }
}
