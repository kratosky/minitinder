package initiation;

import java.io.*;
import file_operation.*;
import financial_management.Revenue;
import match_operation.Match;
import user_management.Register;


public class Initiation
{
    private static String parentPath;	//data的父路径
    private static String dataPath;	//data路径

    public static void main(String[] args) throws Exception
    {
        new Initiation(".").createData();
    }

    /**
     * workTree属性设置为传入路径，通常为“.”，也就是当前路径
     * gitDir则是当前路径中的.jit文件，也就是repository
     * @param path
     * @throws IOException
     */
    public Initiation(String path) throws IOException
    {
        this.parentPath = path;
        this.dataPath = path + File.separator + "data";
    }

    public Initiation() throws IOException
    {
        this(".");
    }



    public static String getDataPath() {
        return dataPath;
    }

    public static String getParentPath() {
        return parentPath;
    }
    
    /**
     * 一下三个函数是在判定data的路径是否存在以及是否为文件与文件夹
     * @return
     */
    public boolean exist(){ return new File(dataPath).exists(); }// 判定给定文件夹中是否已经存在data文件或文件夹是否存在

    public boolean isFile(){ return new File(dataPath).isFile(); }// 判定给定路径是否已经存在data文件

    public boolean isDirectory(){ return new File(dataPath).isDirectory(); }// 判定给定路径是否已经存在data文件夹


    /**
     * 创建data和里面的子文件和子目录
     * @return boolean
     * @throws IOException
     */
    
    
    public boolean createData() throws IOException
    {
      //如果传入的parentPath不是一个有效的目录则报错
        if(!new File(parentPath).isDirectory())
        {
            throw new IOException("The path doesn't exist!");
        }
        //然后创建data文件夹dx
        String path = dataPath;
        new File(path).mkdirs();
        // 以上完成了data文件夹的生成
        //生成描述文件
        FileCreation.createFile(dataPath , "Description.txt", "All code written by DDX");
        //生成data文件夹下的其余子文件
        Register.initRegister();
        FileCreation.createDirectory(dataPath, "users");
        FileCreation.createDirectory(dataPath, "matchRecord");
        FileCreation.createDirectory(dataPath, "Revenue");
        Revenue.initRevenue();
        Match.initMatch();
        return true;//全部创建成功，则返回true
    }


}
