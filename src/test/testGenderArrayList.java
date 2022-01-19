package test;

import user.Gender;

import java.util.Arrays;

import static user.Gender.*;


public class testGenderArrayList
{
    public static void main(String[] args) {
        Gender[] genderOptions = {Male, Female, Other};
        System.out.println(Arrays.asList(genderOptions).indexOf(Other));


    }

}
