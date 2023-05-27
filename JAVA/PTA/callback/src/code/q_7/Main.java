package code.q_7;

import java.util.Scanner;

/**
 * author 2107090411 刘敬超
 * version 1.0.0
 **/
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int speed = sc.nextInt();
        int limit = sc.nextInt();

        long current = Math.round((speed - limit) * 100.0 / limit);
        if(current < 10) {
            System.out.println("OK");
        } else if(current < 50) {
            System.out.println("Exceed " + current + "%. Ticket 200");
        } else {
            System.out.println("Exceed " + current + "%. License Revoked");
        }
    }
}
