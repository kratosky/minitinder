package test;

import java.util.*;

public class testRandomSetSelection
{
    public static void main(String[] args)
    {
        String[] names = {"Dale", "Autauga", "Baldwin", "Bibb", "Bullock", "Chilton", "Coffee", "Coosa",
                "Etowah", "Franklin", "Lamar", "Macon", "Marion", "Morgan", "Randolph", "Sumter",
                "Wilcox", "Barbour", "Blount", "Butler", "Calhoun", "Chambers", "Cherokee",
                "Choctaw", "Clarke", "Clay", "Cleburne", "Colbert", "Conecuh", "Covington",
                "Crenshaw", "Cullman", "Dallas", "DeKalb", "Elmore", "Escambia", "Fayette",
                "Geneva", "Greene", "Hale", "Henry", "Houston", "Jefferson", "Lauderdale",
                "Lawrence", "Lee", "Lowndes", "Madison", "Marengo", "Marshall", "Mobile",
                "Monroe", "Montgomery", "Jackson", "Perry", "Pickens", "Pike", "Russell",
                "St. Clair", "Shelby", "Talladega", "Tallapoosa", "Tuscaloosa", "Walker",
                "Washington", "Winston", "Limestone","Valdez-Cordova",
                "Wade Hampton", "Wrangell",
                "Yukon-Koyukuk",
                "Ketchikan Gateway",
                "Aleutians East", "Bethel",
                "Hoonah-Angoon", "Sitka",
                "Kenai Peninsula",
                "Kodiak Island",
                "Lake and Peninsula",
                "Nome", "Petersburg",
                "North Slope",
                "Northwest Arctic",
                "Southeast Fairbanks",
                "Yakutat",
                "Aleutians West",
                "Anchorage", "Bristol Bay",
                "Denali", "Dillingham",
                "Fairbanks North Star",
                "Haines", "Juneau",
                "Matanuska-Susitna",
                "Prince of Wales-Hyder",
                "Skagway"};
        List<String> namelist = Arrays.asList(names);
        Set<String> nameset = new HashSet<String>(namelist);
        List<String> chosen = getRandomSelections(nameset, 70);
        System.out.println(String.valueOf(chosen.size()));
        chosen.forEach(name -> System.out.println(name));
    }

    private static ArrayList<String> getRandomSelections(Set<String> selectFromSet, int chances)
    {
        if(selectFromSet.size() < chances)
        {
            System.out.println("本次可匹配次数超过剩余用户数");

        }

        //创建随机数对象
        Random r = new Random(System.currentTimeMillis());


        //将结果作为ArrayList返回
        //List<String> result = Arrays.asList(temp);
        //ArrayList<String> arrayList= new ArrayList<>(result);



        ArrayList<String> selectFromList = new ArrayList<>(Arrays.asList(selectFromSet.toArray(new String[0])));
        ArrayList<String> selectedNames = new ArrayList<>();
        //判断集合的长度是不是小于10
        while (selectedNames.size()<chances)
        {
            //产生一个随机数，从selectFromList中取出对应的userName
            int number = r.nextInt(selectFromList.size());
            String name = selectFromList.remove(number);
            //如果该userName对应的用户符合要求则成功加入selectedNames，机会减少
            if(name.length()>6)
            {
                selectedNames.add(name);
            }
            if(selectFromList.size()==0) { break;}
        }
        return selectedNames;

    }
}
