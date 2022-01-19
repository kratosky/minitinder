package gui;

import financial_management.Revenue;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import user.CheckedUser;

public class PaymentConfirmation
{
    private static boolean paymentComplete;
    /**
     * 展示提醒窗口
     * @param userName
     */
    public static boolean display(String userName,double payment,int chances, int likes)
    {

        paymentComplete = false;
        // 创建舞台
        Stage stage = new Stage();
        // 设置显示模式
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("付款确认");
        String payString = String.format("¥%.2f", payment);
        Label label = new Label("本次购买总共需要支付"+payString+"，请确认支付！");
        // 创建控件
        Button buttonYes = new Button("支付");
        buttonYes.setOnMouseClicked(event ->
        {
            completePurchase(userName,payment,chances, likes);
            paymentComplete = true;
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
        return paymentComplete;
    }

    /**
     * 确认购买，一方面系统记账，一方面用户资源数增加
     */
    private static void completePurchase(String userName,double payment,int chances, int likes)
    {
        try
        {
            //记账
            Revenue.addPayment(userName, payment);
            //资源数增加
            CheckedUser user = CheckedUser.deserialize(userName);
            user.changeLikeOnes(likes);
            user.changeOpportunities(chances);
            user.compressSerialize();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

}
