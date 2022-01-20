package match_operation;

import java.util.*;

import user.CheckedUser;
import user.Gender;


public class Selection
{
    private String userName;
    private int chances;
    private int maxLikes;
    private Condition conditon;

    public Selection(String userName,int chances, int maxLikes, Condition conditon )
    {
        this.userName = userName;
        this.chances = chances;
        this.maxLikes = maxLikes;
        this.conditon = conditon;
    }


    public String getUserName() {
        return userName;
    }

    public int getChances() {
        return chances;
    }

    public int getMaxLikes() {
        return maxLikes;
    }

    public Condition getConditon() {
        return conditon;
    }

    /**
     * 从给定0-totalSize之中随机抽取randomNumbers个不同的下标
     * @param totalSize
     * @param randomNumbers
     * @return
     * @throws IllegalArgumentException
     */
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

