package test;

public class testYear
{
    public static void main(String[] args) {
        System.out.println("1999".matches("(19|20)[\\d]{2}"));
        System.out.println("2199".matches("(19|20)[\\d]{2}"));
        System.out.println("19399".matches("(19|20)[\\d]{2}"));
        System.out.println("19x9".matches("(19|20)[\\d]{2}"));
        System.out.println("1799".matches("(19|20)[\\d]{2}"));
        System.out.println("399".matches("(19|20)[\\d]{2}"));
        System.out.println("2099".matches("(19|20)[\\d]{2}"));


    }
}
