package file_operation;

import java.io.*;
import java.util.ArrayList;

public class FileReader
{


    /**
     * d静态方法，获得文件里的内容x
     * @param file
     * @return String
     * @throws IOException
     */

    public static String getContent(File file) throws IOException
    {
        BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String s = new String();
        String filevalue = new String();
        while((s = bufferedreader.readLine()) != null)
        {
            filevalue += s;
        }
        return filevalue;
    }

    /*
     * @description, 输入一个file对象，返回String类型的文件内容
     */
    public static String getValue(File file) throws Exception
    {
        String value = "";
        FileInputStream is = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int tempReadLength;
        while((tempReadLength=is.read(buffer))>0)
        {
            value += new String(buffer,0,tempReadLength);
        }
        is.close();
        return value;
    }

}
