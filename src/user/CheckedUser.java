package user;

import serialization.ZipSerial;

import java.io.File;
import java.util.Calendar;

public class CheckedUser extends UncheckedUser
{
    private String photoFileName;
    private String introduction;
    private String contactInformation;
    private Gender gender;
    private int birthYear;
    private int opportunities = 100;
    private int likeOnes = 10;

    public String getPhotoFileName()
    {
        return photoFileName;
    }

    public String getIntroduction()
    {
        return introduction;
    }

    public String getContactInformation()
    {
        return contactInformation;
    }

    public Gender getGender()
    {
        return gender;
    }

    public int getBirthYear()
    {
        return birthYear;
    }

    public int getOpportunities()
    {
        return opportunities;
    }

    public int getLikeOnes() { return likeOnes;}

    public int getAge()
    {
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        int age = currentYear - birthYear +1;
        return age;

    }

    //对likeones资源进行变化，当为负数时，变化失败，返回false；否则成功变化，返回true
    public boolean changeLikeOnes(int change)
    {
        if((this.likeOnes+change)<0){ return false;}
        else{ this.likeOnes = this.likeOnes + change; return true;}
    }
    //对likeones资源进行变化，当为负数时，变化失败，返回false；否则成功变化，返回true
    public boolean changeOpportunities(int change)
    {
        if((this.opportunities+change)<0){ return false;}
        else{ this.opportunities = this.opportunities + change; return true;}
    }


    public CheckedUser(UncheckedUser user, int birthYear, Gender gender, String photoFileName, String introduction, String contactInformation)
    {
        super(user.userName,user.getPassword());
        this.checked = "checked";
        this.photoFileName = photoFileName;
        this.introduction = introduction;
        this.gender = gender;
        this.birthYear = birthYear;
        this.contactInformation = contactInformation;

    }

    /**
     * Deserialize a CheckedUser with filename and its path.
     * @param userName
     * @return
     */
    public static CheckedUser deserialize(String userName)
    {
        try
        {
            String storePath = "data" + File.separator+  "users" + File.separator+userName+".checked";
            CheckedUser deserial = (CheckedUser) ZipSerial.readCompressFile(storePath, CheckedUser.class);
            return deserial;
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }

    }

}
