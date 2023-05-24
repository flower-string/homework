package org.example;

import java.util.Scanner;

/**
 * author 2107090411 刘敬超
 * version 1.0.0
 **/
public class Q_4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String[] strs = s.split(" ");
        int cnt = 0;
        for(int i = 1; i < strs.length; i++) {
            if(strs[i].substring(0,1).equals(strs[0])) {
                cnt += 1;
            }
        }
        System.out.println(cnt);
    }

}
