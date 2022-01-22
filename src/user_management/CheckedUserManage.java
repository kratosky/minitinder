package user_management;

import file_operation.FileDeletion;
import match_operation.Match;
import user_management.Register;
import user.CheckedUser;
import user.Gender;
import user.UncheckedUser;

import java.io.File;

public abstract class CheckedUserManage
{
    public static void createCheckedUser(String username, int birthYear, Gender gender, String photoFileName, String introduction, String contactInformation)
    {
        //将未审查对象取出，并删除序列化文件
        UncheckedUser unchecked = UncheckedUser.deserialize(username);
        FileDeletion.deleteFile(new File("data" + File.separator+  "users"+ File.separator+username+"."+unchecked.checked));
        //创建已审查对象，序列化写入
        CheckedUser newlyChecked = new CheckedUser(unchecked,birthYear,gender,photoFileName,introduction,contactInformation);
        newlyChecked.compressSerialize();
        //三大map加入该user
        try { Match.addCheckedUser(username);}
        catch (Exception e) { e.printStackTrace();}


    }

    public static void updateCheckedUser(String username,int birthYear,Gender gender,String photoFileName, String introduction, String contactInformation)
    {
        //将未审查对象取出，并删除序列化文件
        CheckedUser checkedBefore = CheckedUser.deserialize(username);
        FileDeletion.deleteFile(new File("data" + File.separator+  "users"+ File.separator+username+"."+checkedBefore.checked));
        //更新已审查对象，序列化写入（利用构造函数第一个参数的多态性）
        CheckedUser newlyChecked = new CheckedUser(checkedBefore,birthYear,gender,photoFileName,introduction,contactInformation);
        newlyChecked.compressSerialize();
    }

    public static void deleteCheckedUser(String username)
    {
        FileDeletion.deleteFile(new File("data" + File.separator+  "users"+ File.separator+username+".checked"));
        try
        {
            Match.delCheckedUser(username);
            Register.delUser(username);
        }
        catch (Exception e) {e.printStackTrace();}
    }
}
