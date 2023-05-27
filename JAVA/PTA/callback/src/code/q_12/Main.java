package code.q_12;

import java.util.Scanner;

/**
 * author 2107090411 刘敬超
 * version 1.0.0
 **/
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        String[] words = input.split(" ");
        int l = words[0].length();
        int cnt = 0;
        for(int i = 1; i < words.length; i++) {
            if(words[i].substring(0, l).equals(words[0])) {
                cnt += 1;
            }
        }

        System.out.println(cnt);
    }
}
