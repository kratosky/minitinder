package user;

import serialization.ZipSerial;


import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class UncheckedUser implements Serializable
{
    protected String userName;
    private String password;
    public String checked;
    private static final long serialVersionUID = 4125096758372084309L;

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }


    public UncheckedUser(String userName, String password)
    {
        this.userName =userName;
        this.password = password;
        this.checked = "unchecked";
    }


    /**
     * 将该用户对象压缩存入user库中
     * @throws Exception
     */
    public void compressSerialize()
    {
        try
        {
            String storePath = "data" + File.separator+  "users"+ File.separator+this.userName+"."+this.checked;
            ZipSerial.compressWriteObj(storePath, this);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    /**
     * Deserialize a 用户 from an existed hash file in data/users.
     * 无论传入用户名是审验过还是没有审验过，都可以返回名义类型为未审验的用户，供读取密码

     * @throws IOException
     */
    public static UncheckedUser deserialize(String userName)
    {
        try
        {
            if(isUnchecked(userName))
            {
                String storePath = "data" + File.separator+  "users" + File.separator+userName+".unchecked";
                UncheckedUser deserial = (UncheckedUser) ZipSerial.readCompressFile(storePath, UncheckedUser.class);
                return deserial;
            }
            else
            {
               return CheckedUser.deserialize(userName);
            }
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }

    }

    public static boolean isUnchecked(String userName)
    {
        File serial = new File( "data" + File.separator+  "users" + File.separator+userName+".unchecked");
        if(serial.exists()){return true;}
        else {return false;}
    }

}
