package match_operation;

import java.util.*;

import user.CheckedUser;
import user.Gender;


public class Selection
{
    private int chances;
    private Condition conditon;

    public Selection(int chances, Condition conditon )
    {
        this.chances = chances;
        this.conditon = conditon;
    }




    private static Set<Integer> getRandomIndices(int totalSize, int randomNumbers) throws IllegalArgumentException
    {
        if(totalSize < randomNumbers)
        {
            throw new IllegalArgumentException("本次可匹配次数超过剩余用户数");
        }
        //创建Set集合对象
        Set<Integer> set = new HashSet<Integer>();
        //创建随机数对象
        Random r = new Random(System.currentTimeMillis());

        //判断集合的长度是不是小于10
        while (set.size()<randomNumbers)
        {
            //产生一个随机数，添加到集合
            int number = r.nextInt(totalSize);
            set.add(number);
        }
        return set;

    }


    private  ArrayList<String> getRandomSelections(Set<String> selectFromSet) throws Exception
    {
        if(selectFromSet.size() < this.chances)
        {
            throw new IllegalArgumentException("本次可匹配次数超过剩余用户数");

        }

        //创建随机数对象
        Random r = new Random(System.currentTimeMillis());
        ArrayList<String> selectFromList = (ArrayList<String>) Arrays.asList(selectFromSet.toArray(new String[0]));
        ArrayList<String> selectedNames = new ArrayList<>();
        //判断集合的长度是不是小于10
        while (selectedNames.size()<this.chances)
        {
            //产生一个随机数，从selectFromList中取出对应的userName
            int number = r.nextInt(selectFromList.size());
            String name = selectFromList.remove(number);
            //如果该userName对应的用户符合要求则成功加入selectedNames，机会减少
            if(this.conditon.meetCondition(name))
            {
                selectedNames.add(name);
                this.chances--;
            }
            if(selectFromList.size()==0) { break;}
        }
        return selectedNames;

    }




}

class Condition
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