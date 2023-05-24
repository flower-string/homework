package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine(); // consume the newline character
        ArrayList<String> strs = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String str = sc.nextLine();
            if (str.length() >= 13) {
                strs.add(str);
            }
        }

        strs.sort((o1, o2) -> {
            int i = o1.substring(6, 13).compareTo(o2.substring(6, 13));
            return Integer.compare(i, 0);
        });

        String s = sc.nextLine();
        while (s.equals("sort1") || s.equals("sort2")) {
            if (s.equals("sort1")) {
                for (String as : strs) {
                    System.out.println(as.substring(6, 10) + "-" + as.substring(10, 12) + "-" + as.substring(12, 14));
                }
            } else {
                for (String as : strs) {
                    System.out.println(as);
                }
            }
            s = sc.nextLine();
        }

        System.out.println("exit");
    }
}
