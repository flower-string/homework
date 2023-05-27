package code.q_8;

import java.util.Scanner;

/**
 * author 2107090411 刘敬超
 * version 1.0.0
 **/
public class Main {
    public static int a = 0;
    public static int b = 0;
    private static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        int n = sc.nextInt();
        for(int i = 0; i < n; i++) {
            try {
                int num = sc.nextInt();
                ji(num);
            } catch (ScoreError e) {
                e.print();
            }
        }

        System.out.println(b);
        System.out.println(a);
    }

    public static void ji(int num) {
        if(num < 0 || num > 100) {
            ji(sc.nextInt());
            throw new ScoreError(num);
        } else if (num > 60) {
            a++;
        } else {
            b++;
        }
    }
}

class ScoreError extends RuntimeException {
    int score;

    public ScoreError(int score) {
        this.score = score;
    }

    public void print() {
        System.out.println(this.score + "invalid!");
    }
}
