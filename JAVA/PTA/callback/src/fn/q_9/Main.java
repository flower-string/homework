package fn.q_9;

/**
 * author 2107090411 刘敬超
 * version 1.0.0
 **/
import java.util.Scanner;
class TDVector {
    private double x;
    private double y;
    public String toString() {
        return "("+this.x+","+this.y+")";
    }

    /** 你所提交的代码将被嵌在这里（替换此行） **/
    public TDVector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public TDVector() {
    }

    public TDVector(TDVector other) {
        this.x = other.x;
        this.y = other.y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public TDVector add(TDVector other) {
        return new TDVector(this.x + other.x, this.y + other.y);
    }
}
public class Main {
    public static void main(String[] args) {
        TDVector a = new TDVector();  /* 无参构造，向量的x和y默认为0 */
        Scanner sc = new Scanner(System.in);
        double x,y,z;
        x = sc.nextDouble();
        y = sc.nextDouble();
        z = sc.nextDouble();
        TDVector b = new TDVector(x, y);  /* 按照参数构造向量的x和y */
        TDVector c = new TDVector(b);  /* 按照向量b构造向量 */
        a.setY(z);
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        c.setX(z);
        a = b.add(c);
        System.out.println(a);
        System.out.println("b.x="+b.getX()+" b.y="+b.getY());
        sc.close();
    }
}
