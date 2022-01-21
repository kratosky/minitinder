package gui;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import match_operation.Match;

import java.util.Map;

public class MatchConfirmation
{
    private boolean submissionConfirmed;
    private Label label = new Label();
    private Stage stageConfirm = new Stage();
    private Stage stageReport = new Stage();
    private Button buttonYes = new Button("确认提交");
    private Button buttonNo = new Button("取消");

    private int likes = 0;
    private int dislikes = 0;
    private int notSure = 0;
    private int notDecide = 0;

    /**
     * 展示提醒窗口
     */
    public boolean display(String selector,Map<String,Integer> selectionRecord)
    {

        submissionConfirmed = false;
        getStatistic(selectionRecord);

        stageConfirm.setTitle("选择提交确认");


        label.setText("本次匹配总共浏览了 " +
                String.valueOf(selectionRecord.size()) + " 位用户，其中，对 " +
                String.valueOf(likes) + " 位用户选择了“喜欢”，对 " +
                String.valueOf(dislikes) + " 位用户选择了“不喜欢”，对 " +
                String.valueOf(notSure) + " 位用户选择了“不确定”，还有 " +
                String.valueOf(notDecide) + " 位用户您并未给出选择。请确定是否提交当前选择结果？"
        );



        buttonYes.setOnMouseClicked(event ->
        {
            int successfulMatches = Match.recordReturnMatches(selector,selectionRecord);
            matchReport(selector,successfulMatches);
            submissionConfirmed = true;
            stageConfirm.close();
        });




        buttonNo.setOnMouseClicked(event -> {
            stageConfirm.close();
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
        Scene scene = new Scene(gridPane, 1200, 150);

        // 显示舞台
        stageConfirm.setScene(scene);
        stageConfirm.showAndWait();  // 等待窗体关闭才继续
        return submissionConfirmed;
    }

    /**
     * 统计本次选择各项数据
     * @param selectionRecord
     */
    private void getStatistic(Map<String,Integer> selectionRecord)
    {
        selectionRecord.forEach((selectee,decision)->
            {
                if(decision == 1){likes++;}
                else if(decision == 0){notDecide++;}
                else if(decision == -1){notSure++;}
                else{dislikes++;}
            });
    }


    /**
     * 弹出匹配结果页面
     * @param selector
     * @param matches
     */
    private void matchReport(String selector,int matches)
    {
        label.setText("本次选择成功匹配 "+String.valueOf(matches)+" 位用户，你可以在个人主页的“已匹配用户”模块查看他们的联系方式！");
        Button buttonYes = new Button("确定");
        buttonYes.setOnMouseClicked(event ->
        {
            new HomePage().display(selector);
            stageReport.close();
        });
        // 窗口点击叉号关闭询问
        stageConfirm.setOnCloseRequest(event -> {
            event.consume();  // 消除默认事件
            new HomePage().display(selector);
            stageReport.close();
        });


        // Create UI
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.add(label, 0, 0);
        gridPane.add(buttonYes, 0, 1);
        // 创建控件


        // Set properties for UI
        gridPane.setAlignment(Pos.CENTER);
        GridPane.setHalignment(label, HPos.CENTER);
        GridPane.setHalignment(buttonYes, HPos.CENTER);

        // 创建场景
        Scene scene = new Scene(gridPane, 800, 150);


        // 显示舞台
        stageReport.setScene(scene);
        stageReport.show();
    }

}
