package code.q_6;

import java.util.Scanner;

/**
 * author 2107090411 刘敬超
 * version 1.0.0
 **/
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int total = 0;

        for(int i = 0; i < 10; i++) {
            total += sc.nextInt();
        }

        System.out.printf("%.2f", total * 1.0 / 10);
    }

}
