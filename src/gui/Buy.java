package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import financial_management.Price;


public class Buy
{
    private static TextField tfNumberOfOpportunities = new TextField();
    private static TextField tfNumberOfLikes = new TextField();
    private static TextField tfTotalPayment = new TextField();
    private static Button btCalculate = new Button("计算总价");
    private static Button btPay = new Button("支付");
    private static Label lblMessage = new Label("请分别输入要购买的查看次数与喜欢次数！");
    private static Stage stage = new Stage();
    private static boolean haveBuy;

    public static boolean display(String username)
    {
        haveBuy = false;

        // Create UI
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.add(new Label("购买浏览用户次数:"), 0, 0);
        gridPane.add(tfNumberOfOpportunities, 1, 0);
        gridPane.add(new Label("购买喜欢用户次数:"), 0, 1);
        gridPane.add(tfNumberOfLikes, 1, 1);
        gridPane.add(new Label("总价:"), 0, 4);
        gridPane.add(tfTotalPayment, 1, 4);
        gridPane.add(btCalculate, 0, 5);
        gridPane.add(btPay, 1, 5);

        // Set properties for UI
        gridPane.setAlignment(Pos.CENTER);
        tfNumberOfOpportunities.setAlignment(Pos.BOTTOM_LEFT);
        tfNumberOfLikes.setAlignment(Pos.BOTTOM_LEFT);
        tfTotalPayment.setAlignment(Pos.BOTTOM_LEFT);
        tfTotalPayment.setEditable(false);
        //GridPane.setHalignment(btCalculate, HPos.RIGHT);

        // Process events
        btCalculate.setOnAction(e -> tryCalculatePayment());
        btPay.setOnAction(e -> pay(username,tfNumberOfOpportunities.getText(),tfNumberOfLikes.getText()));

        BorderPane borderPane = new BorderPane();
        //borderPane.setTop(new Label("All codes written by ddx"));
        borderPane.setBottom(lblMessage);
        borderPane.setCenter(gridPane);

        // Create a scene and place it in the stage
        Scene scene = new Scene(borderPane, 550, 220);
        stage.setTitle("匹配资源购买"); // Set title
        stage.setScene(scene); // Place the scene in the stage
        stage.showAndWait();  // 等待窗体关闭才继续

        // 窗体返回值
        return haveBuy;
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
        tfNumberOfOpportunities.setText("");
        tfNumberOfLikes.setText("");
        tfTotalPayment.setText("");
        lblMessage.setText("请分别输入要购买的查看次数与喜欢次数！");

    }

    /**
     * 支付
     */
    private static void pay(String username, String chances, String likes)
    {
        //计算成功，则进行支付
        if(tryCalculatePayment())
        {
            boolean completePurchase = PaymentConfirmation.display(username,calculatePayment(),Integer.parseInt(chances),Integer.parseInt(likes));
            if(completePurchase)
            {
                Buy.clear();
                lblMessage.setText("支付成功，购买的资源已增加到账户！");
                haveBuy = true;
            }

        }

    }

    /**
     * 尝试计算并显示结果，返回是否成功
     */
    private static boolean tryCalculatePayment()
    {
        //先判断
        if(isValidInput())
        {
            tfTotalPayment.setText(String.format("¥%.2f", calculatePayment()));
            return true;
        }
        else
        {
            tfTotalPayment.setText("");
            return false;
        }
    }

    /**
     * 只在判定输入合法后使用使用，获得payment
     * @return
     */
    private static double calculatePayment()
    {
        // Get values from text fields
        int numberOfOpportunities = Integer.parseInt(tfNumberOfOpportunities.getText());
        int numberOfLikes = Integer.parseInt(tfNumberOfLikes.getText());
        //Create a Price object. Price defined in financial_management
        Price payment = new Price(numberOfOpportunities, numberOfLikes);
        return payment.getPayment();
    }





    private static boolean isValidInput()
    {
        boolean result = true;
        lblMessage.setText("");
        if(!isInt(tfNumberOfOpportunities.getText())){ lblMessage.setText("购买浏览用户次数输入不合法，请输入一个非负整数!"); result = false;}
        if(!isInt(tfNumberOfLikes.getText())){ lblMessage.setText(lblMessage.getText()+"  购买喜欢次数输入不合法，请输入一个非负整数!"); result = false;}
        return result;
    }

    //用正则表达式判断字符串为非负整数
    private static boolean isInt(String toCheck)
    {
        return toCheck.matches("[\\d]+");
    }

}
