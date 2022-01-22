package test;

import initiation.Initiation;
import match_operation.Match;
import user.CheckedUser;
import user.Gender;
import user.UncheckedUser;
import user_management.UserList;

import java.io.IOException;
import java.util.ArrayList;

public class createUserData
{
    private static Object[] user1 = {"杜德翔","123",1999,Gender.Male,"ddx.jpg","181cm 85kg\n柴犬狂热爱好者，扎根于现实的理想主义者。\n喜欢学习，运动，自我提升。爱好拳击，电影、健身。\n" +
    "性格勤奋、坚毅、勇敢、稳重务实，对爱人温柔可爱，总体而言是温柔优雅的硬核狠人\n希望寻找美丽温柔、乐观积极有梦想的另一半一起努力奋斗！","QQ:123456789\n手机：13312344321\n微信：皓父"};



    private static String contact = "QQ:123456789\n手机：13312344321\n微信：皓父";

    private static String manIntro ="本人姓XX，男，家住XX。身高xxCM，体重xx公斤。年龄24岁，月收入xx左右，有房。\n" +
            "\n" +
            "　　我是一个平凡的男生，想找一帅哥的就别来了，虽然我也不差。想找一钱袋的就别见了，虽然我有点小钱。我只想找个看得上，能过日子的女生就好，就像我经常开玩笑说的一样，只要是女的。活的就好。我个人比较顾家，会做饭，家务我也经常做的，平时在家也养些花草，喜爱旅游，每年都会旅游一次，以前总是一个人出去旅游，虽然很自由很放松，可也少了不少乐趣。想找个人陪我一齐游览！\n" +
            "\n" +
            "　　要求：\n" +
            "\n" +
            "　　年龄：22—26\n" +
            "\n" +
            "　　学历：不限\n" +
            "\n" +
            "　　经济：有工作就好\n" +
            "\n" +
            "　　身高：160—165\n" +
            "\n" +
            "　　我的要求很简单，样貌看得过去就好。";


    private static String womanIntro = "个人资料:姓名:xxx，性别:女，年龄:xx，居住地:xxx，工作:xx，月薪:xx，婚姻状况:XX，\n个人说:我是一个平凡的女生，有一点点的漂亮，温柔善良冶金矿产，会一些简单的家务活，期望能够找到一个能为我挡风遮雨，\n当我悲哀时能为我抹去心里的伤痛，在我高兴是能与我一齐分享喜悦的.好男生。\n对方条件:居住地及户口:。年龄:xx，婚姻状况:xx，学历:xx，经济条件:有住房，月收入xx元以上。有稳定的工作环境;身高:原材料商情xx以上，\n对方描述:期望对方是一个成熟稳重，工作虽然重要，但也要顾家的男生。不知道你满意吗。";

    private static String shibaIntro = "我是一只超级可爱的小柴犬！喜欢和主人卖萌，贴贴，吃肉肉！";

    private static Object[] user2 ={"狄龙","123",1960,Gender.Male,"dl.png",manIntro,contact};
    private static Object[] user3 ={"媛媛","123",2000,Gender.Female,"dly.jpg",womanIntro,contact};
    private static Object[] user4 ={"苦涩柴柴","123",2000,Gender.Other,"dog.jpg",shibaIntro,contact};
    private static Object[] user5 ={"郭老师","123",1987,Gender.Female,"gls.png",womanIntro,contact};
    private static Object[] user6 ={"黑社会老大哥","123",1987,Gender.Male,"hsh.jpg",manIntro,contact};
    private static Object[] user7 ={"战神","123",1953,Gender.Male,"kratos.png",manIntro,contact};
    private static Object[] user8 ={"刘德华","123",1962,Gender.Male,"ldh.png",manIntro,contact};
    private static Object[] user9 ={"刘涛","123",1987,Gender.Female,"lt.jpg",womanIntro,contact};
    private static Object[] user10 ={"杜荷洋","123",1999,Gender.Female,"nvzhuang.png",womanIntro,contact};
    private static Object[] user11 ={"做饭柴柴","123",2000,Gender.Other,"shibaCook.png",shibaIntro,contact};
    private static Object[] user12 ={"贪吃柴柴","123",2000,Gender.Other,"shibaEat.jpg",shibaIntro,contact};
    private static Object[] user13 ={"寂寞柴柴","123",2000,Gender.Other,"shibaLike.jpg",shibaIntro,contact};
    private static Object[] user14 ={"酸柴柴","123",2000,Gender.Other,"shibaSuan.jpg",shibaIntro,contact};
    private static Object[] user15 ={"璐璐","123",1998,Gender.Female,"sl.jpg",womanIntro,contact};
    private static Object[] user16 ={"石原里美","123",1985,Gender.Female,"sylm.png",womanIntro,contact};
    private static Object[] user17 ={"死肥宅","123",1987,Gender.Male,"wsn.jpg",manIntro,contact};
    private static Object[] user18 ={"皓儿","123",1999,Gender.Male,"yhc.png",manIntro,contact};
    private static ArrayList<Object[]> allUsers = new ArrayList<>();

    public static void main(String[] args) throws Exception
    {
        new Initiation(".").createData();
        allUsers.add(user1);
        allUsers.add(user2);
        allUsers.add(user3);
        allUsers.add(user4);
        allUsers.add(user5);
        allUsers.add(user6);
        allUsers.add(user7);
        allUsers.add(user8);
        allUsers.add(user9);
        allUsers.add(user10);
        allUsers.add(user11);
        allUsers.add(user12);
        allUsers.add(user13);
        allUsers.add(user14);
        allUsers.add(user15);
        allUsers.add(user16);
        allUsers.add(user17);
        allUsers.add(user18);
        allUsers.forEach(user1 ->createCheckedUser((String)user1[0],(String)user1[1],(int)user1[2],(Gender)user1[3],(String)user1[4],(String)user1[5],(String)user1[6]));
        Match.like("石原里美", "杜德翔");
        Match.like("皓儿", "杜德翔");
        Match.like("做饭柴柴", "杜德翔");
        Match.like("苦涩柴柴", "杜德翔");
        Match.like("刘涛", "杜德翔");
        Match.like("刘德华", "杜德翔");
        Match.like("璐璐", "杜德翔");
    }


    public static void createCheckedUser(String username, String password, int birthYear, Gender gender, String photoFileName, String introduction, String contactInformation)
    {
        //加入userList
        try { UserList.addUser(username);}
        catch (Exception e) { e.printStackTrace();}
        //生成checkedUser并序列化
        UncheckedUser newUser = new UncheckedUser(username, password);
        CheckedUser newlyChecked = new CheckedUser(newUser,birthYear,gender,photoFileName,introduction,contactInformation);
        newlyChecked.compressSerialize();
        //三大map加入该user
        try { Match.addCheckedUser(username);}
        catch (Exception e) { e.printStackTrace();}
    }

}
