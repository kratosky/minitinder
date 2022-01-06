package test;

import commander.CLI;

public class TestMerge
{
    public static void main(String[] args)throws Exception
    {
        CLI.main("git init".split(" "));
        CLI.main("git add what/farm".split(" "));
        CLI.main("git commit".split(" "));
        CLI.main("git add what".split(" "));
        CLI.main("git commit".split(" "));
        CLI.main("git add tiger.txt".split(" "));
        CLI.main("git commit".split(" "));
        CLI.main("git add what/farm".split(" "));
        CLI.main("git commit".split(" "));
        try
        {
            Thread.currentThread().sleep(3000);

        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        CLI.main("git reset -mixed 2".split(" "));


        try
        {
            Thread.currentThread().sleep(6000);

        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        CLI.main("git reset -hard 3".split(" "));


        try
        {
            Thread.currentThread().sleep(6000);

        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        CLI.main("git reset -super 1".split(" "));


    }
}