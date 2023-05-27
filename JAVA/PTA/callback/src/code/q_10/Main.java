package code.q_10;

import java.util.Scanner;

/**
 * author 2107090411 刘敬超
 * version 1.0.0
 **/
public class Main {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String id = sc.nextLine();
        while (id.length() != 18) {
            id = sc.nextLine();
            System.out.println("Invalid data,input again!");
        }

        System.out.println(id.substring(6,10) + "," + id.substring(10,12));
    }
}
