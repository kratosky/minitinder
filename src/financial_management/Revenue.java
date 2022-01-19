package financial_management;

import file_operation.FileCreation;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import file_operation.FileReader;



public class Revenue
{

    private static final File detailFile = new File( "data" + File.separator + "Revenue" + File.separator +"Detailed_Revenue" );
    private static final File totalFile = new File( "data" + File.separator + "Revenue" + File.separator +"Total_Revenue.txt" );


    /**
     * 初始化match区
     */
    public static void initRevenue()
    {
        try
        {
            //初始化生成Revenue文件夹内序列化的revenueMap，文件名叫“Detailed_Revenue”
            Map<String, Double> revenueMap = new HashMap<String,Double>();
            ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(Revenue.detailFile));
            oo.writeObject(revenueMap);
            oo.flush();
            oo.close();

            //创建总账单
            FileCreation.createFile("data" + File.separator + "Revenue" , "Total_Revenue.txt", "0.0");

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * d将当前Revenue文件夹内序列化的revenueMap反序列化取出x
     * @return
     * @throws Exception
     */
    public static Map<String, Double> getRevenueMap() throws Exception
    {
        //将liked记录读入Match中
        ObjectInputStream oi = new ObjectInputStream(new FileInputStream(Revenue.detailFile));
        try(oi)
        {
            return (Map<String, Double>)oi.readObject();
        }
    }



    /**
     * 将传入的revenueMap更新到Revenue中
     * @param likedMap
     * @throws Exception
     */
    public static void setRevenueMap(Map<String,Double> likedMap) throws Exception
    {
        ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(Revenue.detailFile));
        oo.writeObject(likedMap);
        oo.flush();
        oo.close();
    }

    /**
     * 看是否存在某付费用户
     * @param user
     * @return
     * @throws Exception
     */
    public static boolean containsPayUser(String user)throws Exception
    {
        if(getRevenueMap().containsKey(user))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 用户充值
     * @param user
     * @return
     * @throws Exception
     */
    public static void addPayment(String user, double payment)throws Exception
    {
        Map<String,Double> revenueMap = getRevenueMap();
        //计入revenueMap中
        if(containsPayUser(user))
        {
            double previousPay = revenueMap.get(user);
            revenueMap.put(user,previousPay+payment);
        }
        else
        {
            revenueMap.put(user, payment);
        }
        //计入Total_Revenue.txt文件中
        Double currentIncome = Double.parseDouble(FileReader.getContent(totalFile));
        FileWriter fileWriter = new FileWriter(totalFile);
        fileWriter.write(String.valueOf(currentIncome+payment));
        fileWriter.close();

    }


}
