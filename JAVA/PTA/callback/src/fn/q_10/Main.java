package fn.q_10;

/**
 * author 2107090411 刘敬超
 * version 1.0.0
 **/
import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        int[] a = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        /* 请在这里填写答案 */

        Scanner sc = new Scanner(System.in);
        try {
            int n = sc.nextInt();
            for(int i = 0; i < n; i++) {
                try {
                    System.out.println(1 / a[sc.nextInt()]);
                } catch (InputMismatchException e) {
                    System.out.println("catch a InputMismatchException");
                    sc.nextLine();
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("catch a ArrayIndexOutOfBoundsException");
                } catch (ArithmeticException e) {
                    System.out.println("catch a ArithmeticException");
                }
            }

        } catch (InputMismatchException e) {
            System.out.println("catch a InputMismatchException");
            sc.nextLine();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("catch a ArrayIndexOutOfBoundsException");
        } catch (ArithmeticException e) {
            System.out.println("catch a ArithmeticException");
        }
    }
}

