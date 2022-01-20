package match_operation;

import java.util.*;


public class Selection
{
    private String userName;
    private int setChances;
    private int maxLikes;
    private Condition conditon;

    private int actualChances;


    public Selection(String userName, int setChances, int maxLikes, Condition conditon )
    {
        this.userName = userName;
        this.setChances = setChances;
        this.maxLikes = maxLikes;
        this.conditon = conditon;
    }


    public String getUserName() {
        return userName;
    }


    public int getSetChances() {
        return setChances;
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


    /**
     * 根据match的条件，选出本次匹配选择对象的ArrayList
     * @return
     * @throws Exception
     */
    public ArrayList<String> getUserToSelect()throws Exception
    {
        Set<String> whoLikedHim = Match.getLikedMap().get(userName);
        //所有checkedUser去掉 passed（他给过态度的人和她自己） 就是他能选的
        Set<String> whoHeCanChose = Match.getPassedMap().keySet();
        whoHeCanChose.removeAll(Match.getPassedMap().get(userName));
        Set<String> userToSelect = new HashSet<String>();
        //加入喜欢他的人数量为min（0.5*设定浏览量-1，喜欢他的人总量）在二者之间取小
        int maxLikedHim =(int) (0.5*this.setChances -1);
        if(whoLikedHim.size() <= maxLikedHim)
        {
            userToSelect.addAll(whoLikedHim);
        }
        else
        {
            userToSelect.addAll(getRandomSelections(whoLikedHim, maxLikedHim));
        }
        //在从剩下没有喜欢过他且他也没给态度的人中尽量凑齐
        int maxNotLikedHim = this.setChances - userToSelect.size();
        whoHeCanChose.removeAll(userToSelect);
        userToSelect.addAll(getRandomSelections(whoHeCanChose, maxNotLikedHim));
        this.actualChances = userToSelect.size();//此时赋给actualChances值，为真正可以选择的用户数
        return new ArrayList<>(Arrays.asList(userToSelect.toArray(new String[0])));
    }



    /**
     * 从给定selectFromSet中尽力选出包含setChanges个符合condition的用户名的set
     * @param selectFromSet
     * @return
     */
    private HashSet<String> getRandomSelections(Set<String> selectFromSet, int expectNumbers)
    {

        //创建随机数对象
        Random r = new Random(System.currentTimeMillis());
        ArrayList<String> selectFromList = new ArrayList<>(Arrays.asList(selectFromSet.toArray(new String[0])));
        ArrayList<String> selectedNames = new ArrayList<>();
        //判断集合的长度是不是小于10
        while (selectedNames.size()<expectNumbers)
        {
            //产生一个随机数，从selectFromList中取出对应的userName
            int number = r.nextInt(selectFromList.size());
            String name = selectFromList.remove(number);
            //如果该userName对应的用户符合要求则成功加入selectedNames，机会减少
            if(this.conditon.meetCondition(name))
            {
                selectedNames.add(name);
            }
            if(selectFromList.size()==0) { break;}
        }
        return new HashSet<String>(selectedNames);

    }




}

