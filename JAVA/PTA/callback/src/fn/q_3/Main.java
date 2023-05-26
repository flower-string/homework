package fn.q_3;

/**
 * author 2107090411 刘敬超
 * version 1.0.0
 **/
import  java.util.Scanner;

//Main测试类
public class Main{
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Worker w1 = new Worker("Joe",15);
        w1.pay(35);
        SalariedWorker w2 = new SalariedWorker("Sue",14.5);
        w2.pay();
        w2.pay(60);
        HourlyWorker w3 = new HourlyWorker("Dana", 20);
        w3.pay(25);
        w3.setRate(35);
        int h = input.nextInt();     // 输入小时工的工作时长
        w3.pay(h);
    }
}

/* 请在这里填写答案 */
class Worker {
    public String name;
    public double rate;

    public Worker(String name, double money) {
        this.name = name;
        this.rate = money;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void pay(int money) {
        System.out.println("Not Implemented");
    }
}

class SalariedWorker extends Worker {
    public SalariedWorker(String name, double time) {
        super(name, time);
    }

    @Override
    public void pay(int time) {
        System.out.println(rate * 40);
    }

    public void pay() {
        System.out.println(rate * 40);
    }
}

class HourlyWorker extends Worker {
    public HourlyWorker(String name, double time) {
        super(name, time);
    }

    @Override
    public void pay(int time) {
        if(time < 40) {
            System.out.println(time * rate);
        } else {
            System.out.println(40 * rate + (time-40) * rate * 2);
        }
    }
}