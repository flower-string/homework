package code.q_9;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.Scanner;

/**
 * author 2107090411 刘敬超
 * version 1.0.0
 **/
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        ArrayList<String> ids = new ArrayList<>();
        for(int i = 0; i <= n; i++) {
            String s = sc.nextLine();
            if(s.length() > 14) {
                ids.add(s);
            }
        }

        String input = sc.nextLine();
//        System.out.println(input);
        while (Objects.equals(input, "sort1") || Objects.equals(input, "sort2")) {
            ids.sort(Comparator.comparing(o -> o.substring(6, 14)));
            if(input.equals("sort1")) {
                for(String id: ids) {
                    System.out.println(id.substring(6,10) + "-" + id.substring(10,12) + "-" + id.substring(12,14));
                }
            } else {
                for(String id: ids) {
                    System.out.println(id);
                }
            }

            input = sc.nextLine();
        }

        System.out.println("exit");
    }
}
