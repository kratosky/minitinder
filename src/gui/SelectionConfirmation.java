package gui;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import match_operation.Selection;

import java.util.ArrayList;

public class SelectionConfirmation
{
    private boolean selectionConfirmed;
    private  Label label = new Label();
    private Stage stage = new Stage();
    private ArrayList<String> userToSelect = new ArrayList<>();

    /**
     * 展示提醒窗口
     */
    public boolean display(Selection selection)
    {

        selectionConfirmed = false;
        // 创建舞台

        // 设置显示模式
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("匹配开始确认");
        try
        {
            ArrayList<String> userToSelect = selection.getUserToSelect();
            if(!userToSelect.isEmpty())
            {
                label.setText("根据筛选条件，本次匹配实际可浏览" +
                        String.valueOf(userToSelect.size()) + "位用户，当前剩余可喜欢用户次数为：" +
                        String.valueOf(selection.getMaxLikes()) + "次，年龄范围为 " +
                        String.valueOf(selection.getConditon().getMinAge()) + " 岁到 " +
                        String.valueOf(selection.getConditon().getMaxAge()) + " 岁, 性别为：" +
                        selection.getConditon().getGenderDescription() + "。请确定是否开始匹配？"
                );
            }
            else{return failReport();}
        }
        catch(Exception e)
        {
            System.out.println("读取map时出现问题，请维修系统！");
            stage.close();
            return false;
        }

        // 创建控件
        Button buttonYes = new Button("开始匹配");
        buttonYes.setOnMouseClicked(event ->
        {
            Stub.display();
            selectionConfirmed = true;
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
        stage.showAndWait();  // 等待窗体关闭才继续
        return selectionConfirmed;
    }

    private boolean failReport()
    {
        label.setText("目前找不到符合您筛选条件的用户，请重新选择筛选条件");
        Button buttonYes = new Button("确定");
        buttonYes.setOnMouseClicked(event ->
        {
            stage.close();
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
        Scene scene = new Scene(gridPane, 300, 150);

        // 显示舞台
        stage.setScene(scene);
        stage.showAndWait();  // 等待窗体关闭才继续
        return false;
    }


}
