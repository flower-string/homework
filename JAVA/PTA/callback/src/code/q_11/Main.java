package code.q_11;

import java.util.Collections;
import java.util.Scanner;
import java.util.HashSet;

/**
 * author 2107090411 刘敬超
 * version 1.0.0
 **/
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        String[] strs = str.split(" ");
        HashSet<String> set = new HashSet<>();
        Collections.addAll(set, strs);

        if(set.size() == strs.length) {
            System.out.println("no");
        } else {
            System.out.println("yes");
        }
    }
}
