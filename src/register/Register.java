package register;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public abstract class Register
{
    private static final File userFile = new File( "data" + File.separator + "userlist" );

    /**
     * 初始化match区
     */
    public static void initRegister()
    {
        try
        {
            //初始化生成序列化的userSet，文件名叫“userlist”
            Set<String> userSet = new HashSet<String>();
            ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(Register.userFile));
            oo.writeObject(userSet);
            oo.flush();
            oo.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * d将当前userSet反序列化取出x
     * @return
     * @throws Exception
     */
    public static Set<String> getUserSet() throws Exception
    {
        //获得usesr记录
        ObjectInputStream oi = new ObjectInputStream(new FileInputStream(Register.userFile));
        try(oi)
        {
            return (Set<String>)oi.readObject();
        }
    }

    /**
     * 将传入的userSet更新
     * @param userSet
     * @throws Exception
     */
    public static void setUserSet(Set<String> userSet) throws Exception
    {
        ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(Register.userFile));
        oo.writeObject(userSet);
        oo.flush();
        oo.close();
    }

    /**
     * 看是否存在某用户
     * @param User
     * @return
     * @throws Exception
     */
    public static boolean containsUser(String User)throws Exception
    {
        if(getUserSet().contains(User))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 加入用户
     * @param User
     * @return
     * @throws Exception
     */
    public static boolean addUser(String User)throws Exception
    {
        if(containsUser(User))
        {
            return false;
        }
        else
        {
            Set<String> newSet = getUserSet();
            newSet.add(User);
            setUserSet(newSet);
            return true;
        }
    }


    /**
     * 删除用户
     * @param User
     * @return
     * @throws Exception
     */
    public static boolean delUser(String User)throws Exception
    {
        if(containsUser(User))
        {
            Set<String> newSet = getUserSet();
            newSet.remove(User);
            setUserSet(newSet);
            return true;
        }
        else
        {
            return false;
        }
    }


}
