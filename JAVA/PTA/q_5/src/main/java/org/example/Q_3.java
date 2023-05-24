package org.example;

import java.util.HashSet;
import java.util.Scanner;

/**
 * author 2107090411 刘敬超
 * version 1.0.0
 **/
public class Q_3 {
    public static void main(String[] args) {
        HashSet<String> nums = new HashSet<>();
        String[] strs;
        Scanner sc = new Scanner(System.in);
        strs = sc.nextLine().split(" ");
        for(String s : strs) {
            nums.add(s);
        }

        if(nums.size() == strs.length) {
            System.out.println("no");
        } else {
            System.out.println("yes");
        }
    }

}
