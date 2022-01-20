package match_operation;

import user.CheckedUser;
import user.Gender;

import java.util.Calendar;
import java.util.Set;

public class Condition
{
    private Set<Gender> genderPreference;
    private int minAge;
    private int maxAge;

    public Condition(Set<Gender> genderPreference, int minAge, int maxAge)
    {
        this.genderPreference = genderPreference;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public Set<Gender> getGenderPreference() {
        return genderPreference;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    /**
     * 返回对条件中性别的描述，用于进行确认
     * @return
     */
    public String getGenderDescription()
    {
        String description = "";
        if(this.genderPreference.contains(Gender.Male)){description += "男性 ";}
        if(this.genderPreference.contains(Gender.Female)){description += "女性 ";}
        if(this.genderPreference.contains(Gender.Female)){description += "其他 ";}
        return description;
    }

    /**
     * 判断传入的性别与出生年份符合筛选要求
     * @param gender
     * @param birthYear
     * @return
     */
    public boolean meetCondition(Gender gender,int birthYear)
    {
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        int age = currentYear - birthYear +1;
        if(this.genderPreference.contains(gender)&&(age>=minAge)&&(age<=maxAge))
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    /**
     * 重载meetCondition，判断传入的username判断是否符合条件
     * @return
     */
    public boolean meetCondition(String username)throws Exception
    {
        CheckedUser user = CheckedUser.deserialize(username+".checked");
        return(meetCondition(user.getGender(),user.getBirthYear()));
    }


}