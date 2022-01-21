package match_operation;





import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public abstract class Match
{

    private static final File likedFile = new File( "data" + File.separator + "matchRecord" + File.separator +"liked" );
    private static final File passedFile = new File( "data" + File.separator + "matchRecord" + File.separator +"passed" );
    private static final File matchedFile = new File( "data" + File.separator + "matchRecord" + File.separator +"matched" );


    /**
     * 初始化match区
     */
    public static void initMatch()
    {
        try
        {
            //初始化生成matchRecord内序列化的likedMap，文件名叫“liked”
            Map<String,Set<String>> likedMap = new HashMap<String,Set<String>>();
            ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(Match.likedFile));
            oo.writeObject(likedMap);
            oo.flush();
            oo.close();

            //初始化生成matchRecord内序列化的passedMap，文件名叫“passed”
            Map<String,Set<String>> passedMap = new HashMap<String,Set<String>>();
            ObjectOutputStream oo1 = new ObjectOutputStream(new FileOutputStream(Match.passedFile));
            oo1.writeObject(passedMap);
            oo1.flush();
            oo1.close();

            //初始化生成matchRecord内序列化的matchedMap，文件名叫“matched”
            Map<String,Set<String>> matchedMap = new HashMap<String,Set<String>>();
            ObjectOutputStream oo2 = new ObjectOutputStream(new FileOutputStream(Match.matchedFile));
            oo2.writeObject(matchedMap);
            oo2.flush();
            oo2.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * d将当前matchRecord中的likedMap反序列化取出x
     * @return
     * @throws Exception
     */
    public static Map<String, Set<String>> getLikedMap() throws Exception
    {
        //将liked记录读入Match中
        ObjectInputStream oi = new ObjectInputStream(new FileInputStream(Match.likedFile));
        try(oi)
        {
            return (Map<String, Set<String>>)oi.readObject();
        }
    }


    /**
     * d将当前matchRecord中的passedMap反序列化取出x
     * @return
     * @throws Exception
     */
    public static Map<String, Set<String>> getPassedMap() throws Exception
    {
        //将passed记录读入Match中
        ObjectInputStream oi = new ObjectInputStream(new FileInputStream(Match.passedFile));
        try(oi)
        {
            return (Map<String, Set<String>>)oi.readObject();
        }
    }

    /**
     * d将当前matchRecord中的matchedMap反序列化取出x
     * @return
     * @throws Exception
     */
    public static Map<String, Set<String>> getMatchedMap() throws Exception
    {
        //将matched记录读入Match中
        ObjectInputStream oi = new ObjectInputStream(new FileInputStream(Match.matchedFile));
        try(oi)
        {
            return (Map<String, Set<String>>)oi.readObject();
        }
    }

    /**
     * 将传入的likedMap更新到matchRecord中
     * @param likedMap
     * @throws Exception
     */
    public static void setLikedMap(Map<String,Set<String>> likedMap) throws Exception
    {
        ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(Match.likedFile));
        oo.writeObject(likedMap);
        oo.flush();
        oo.close();
    }

    /**
     * 将传入的passedMap更新到matchRecord中
     * @param passedMap
     * @throws Exception
     */
    public static void setPassedMap(Map<String, Set<String>> passedMap) throws Exception
    {
        ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(Match.passedFile));
        oo.writeObject(passedMap);
        oo.flush();
        oo.close();
    }


    /**
     * 将传入的matchedMap更新到matchRecord中
     * @param matchedMap
     * @throws Exception
     */
    public static void setMatchedMap(Map<String, Set<String>> matchedMap) throws Exception
    {
        ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(Match.matchedFile));
        oo.writeObject(matchedMap);
        oo.flush();
        oo.close();
    }


    /**
     * 看是否存在某审核后用户
     * @param user
     * @return
     * @throws Exception
     */
    public static boolean containsCheckedUser(String user)throws Exception
    {
        if(getLikedMap().containsKey(user)||getPassedMap().containsKey(user)||getMatchedMap().containsKey(user))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 加入审核后用户
     * @param user
     * @return
     * @throws Exception
     */
    public static boolean addCheckedUser(String user)throws Exception
    {
        Map<String,Set<String>> likedMap = getLikedMap();
        Map<String,Set<String>> passedMap = getPassedMap();
        Map<String,Set<String>> matchedMap = getMatchedMap();
        if(containsCheckedUser(user))
        {
            return false;
        }
        else
        {
            likedMap.put(user,new HashSet<String>());
            setLikedMap(likedMap);
            passedMap.put(user,new HashSet<String>());
            passedMap.get(user).add(user);//passedMap把自己写入，避免匹配到自己
            setPassedMap(passedMap);
            matchedMap.put(user,new HashSet<String>());
            setMatchedMap(matchedMap);
            return true;
        }
    }


    /**
     * 删除审核后用户
     * @param user
     * @return
     * @throws Exception
     */
    public static boolean delCheckedUser(String user)throws Exception
    {
        Map<String,Set<String>> likedMap = getLikedMap();
        Map<String,Set<String>> passedMap = getPassedMap();
        Map<String,Set<String>> matchedMap = getMatchedMap();
        if(containsCheckedUser(user))
        {
            likedMap.remove(user);
            eliminateCheckedUser(user,likedMap);
            setLikedMap(likedMap);

            passedMap.remove(user);
            eliminateCheckedUser(user,likedMap);
            setPassedMap(passedMap);

            matchedMap.remove(user);
            eliminateCheckedUser(user,likedMap);
            setMatchedMap(matchedMap);
            return true;
        }
        else
        {
            return false;
        }
    }


    //用于彻底对三大map清除某个用户，即从所有用户的set中将其remove，该函数作为delCheckedUser函数的辅助函数调用
    private static void eliminateCheckedUser(String delUser, Map<String,Set<String>> map)
    {
        map.forEach((user,userSet) ->
        {
            userSet.remove(delUser);
        });
    }

    /**
     * 用户a喜欢了b
     * 若b喜欢了a（a的liked库去掉b，a加入b的passed，a,b加入彼此的matched）
     * 若b没态度（b加入到a的passed，a加入到b的liked）
     * 当匹配成功时返回true
     * @param selector
     * @param selectee
     * @return
     * @throws Exception
     */
    public static boolean like(String selector, String selectee)throws Exception
    {
        boolean isSuccess = false;
        Map<String,Set<String>> likedMap = getLikedMap();
        Map<String,Set<String>> passedMap = getPassedMap();
        if (likedMap.get(selector).contains(selectee))
        {

            likedMap.get(selector).remove(selectee);
            isSuccess = true;
            Map<String,Set<String>> matchedMap = getMatchedMap();
            matchedMap.get(selector).add(selectee);
            matchedMap.get(selectee).add(selector);
            setMatchedMap(matchedMap);
        }
        else
        {
            likedMap.get(selectee).add(selector);
        }
        passedMap.get(selector).add(selectee);
        setLikedMap(likedMap);
        setPassedMap(passedMap);
        return isSuccess;
    }


    /**
     * 用户a不喜欢了b
     * b喜欢了a（a的liked库去掉b，b加入a的passed）
     * b没态度（双方加入到彼此的passed）
     * @param selector
     * @param selectee
     * @return
     * @throws Exception
     */
    public static void dislike(String selector, String selectee)throws Exception
    {
        Map<String,Set<String>> likedMap = getLikedMap();
        Map<String,Set<String>> passedMap = getPassedMap();
        if (likedMap.get(selector).contains(selectee))
        {
            likedMap.get(selector).remove(selectee);
        }
        else
        {
            passedMap.get(selectee).add(selector);
        }
        passedMap.get(selector).add(selectee);
        setLikedMap(likedMap);
        setPassedMap(passedMap);
    }

    /**
     * 解除已经匹配的关系
     * @param selector
     * @param selectee
     * @throws Exception
     */
    public static void removeMatchedRelationship(String selector, String selectee)throws Exception
    {
        Map<String,Set<String>> matchedMap = getMatchedMap();
        matchedMap.get(selector).remove(selectee);
        matchedMap.get(selectee).remove(selector);
        setMatchedMap(matchedMap);
    }


    /**
     * 获取给定用户当前的最大可浏览系统资源
     * @param user
     * @return
     * @throws Exception
     */
    public static int getMaxChances(String user)throws Exception
    {
        Map<String,Set<String>> passedMap = getPassedMap();
        int maxChances = passedMap.size() - passedMap.get(user).size();
        return maxChances;
    }


    private static int matches = 0;
    /**
     * 对用户的选择进行记录并返回配对成功数量
     */
    public static int recordReturnMatches(String selector,Map<String,Integer> selectionRecord)
    {
        matches =0;
        selectionRecord.forEach((selectee,decision)->
        {
            try
            {
                if(decision == 1)
                {
                    if(like(selector,selectee)) {matches++;}
                }
                else if(decision == -2){dislike(selector,selectee);}
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        });
        return matches;
    }


}
