package org.example;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * author 2107090411 刘敬超
 * version 1.0.0
 **/
public class Q_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();

//        if(str.length() != 18) {
//            System.out.println("Invalid data,input again!");
//            return;
//        }
//        ArrayList<String> strs = new ArrayList<>();
        while (true) {
            if(str.length() != 18) {
                System.out.println("Invalid data,input again!");
                continue;
            }
            System.out.println(str.substring(6,10) + "," + str.substring(10,12));
            break;
        }
    }

}
