package fn.q_5;

/**
 * author 2107090411 刘敬超
 * version 1.0.0
 **/
import java.util.*;
public class Main {

    public static void main(String[] args) {
        double s=0;
        Scanner sc=new Scanner(System.in);
        double r1,r2;
        r1=sc.nextDouble();
        r2=sc.nextDouble();
        Circle c1=new Circle(r1);
        Circle c2=new Circle(r2);
        try{
            s = c1.area();
            System.out.println(s);
            s = c2.area();
            System.out.println(s);
        }
        catch (CircleException e){
            e.print();
        }
    }
}


/* 请在这里填写答案 编写Circle 和CircleException类*/
class Circle {
    public double r;

    public Circle(double r) {
        this.r = r;
    }

    public double area() {
        if(this.r < 0) {
            throw new CircleException(this.r);
        }
        return this.r * this.r * 3.14;
    }
}

class CircleException extends RuntimeException {
    public double r;

    public CircleException(double r) {
        this.r = r;
    }

    public void print() {
        System.out.println("圆半径为" + this.r + "不合理");
    }
}