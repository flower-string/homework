package fn.q_8;

/**
 * author 2107090411 刘敬超
 * version 1.0.0
 **/
import java.util.Scanner;
public class Main{

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        Car c = new Car();
        for (int i=0;i<n;i++) {
            int a = s.nextInt();
            switch (a) {
                case 1: c.start(); break;
                case 2: c.stop(); break;
                case 3: c.speedUp(); break;
                case 4: c.slowDown(); break;
            }
        }
        System.out.print(c.status + " ");
        System.out.println(c.speed);
    }

}

/* 你的代码被嵌在这里 */
class Car {
    public String status = "off";
    public int speed = 0;

    public void start() {
        this.status = "on";
    }

    public void stop() {
        if(this.speed == 0) {
            this.status = "off";
        }
    }

    public void speedUp() {
        if(this.status == "on" && this.speed < 160) {
            this.speed += 10;
        }
    }

    public void slowDown() {
        if(this.status == "on" && this.speed > 0) {
            this.speed -= 10;
        }
    }
}