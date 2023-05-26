package fn.q_4;

/**
 * author 2107090411 刘敬超
 * version 1.0.0
 **/

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n=input.nextInt();
        int type;
        ComputeWeight [] goods=new ComputeWeight[n]; //n件货物
        for(int i=0;i<goods.length ;i++){
            type=input.nextInt();                    //读入家电类型
            if (type==1)
                goods[i]=new Television();
            else if (type==2)
                goods[i]=new AirConditioner();
            else if (type==3)
                goods[i]=new WashMachine();
        }
        Truck truck =new Truck(goods);
        System.out.printf("货车装载的货物总量:%8.2f kg",truck.getTotalWeight());
    }
}
/* 请在这里填写答案 */
interface ComputeWeight {
    double computeWeight();
}

class Television implements ComputeWeight {
    public double computeWeight() {
        return 16.6;
    }
}

class AirConditioner implements ComputeWeight {

    @Override
    public double computeWeight() {
        return 40.0;
    }
}

class WashMachine implements ComputeWeight {

    @Override
    public double computeWeight() {
        return 60.0;
    }
}

class Truck {
    private ComputeWeight[] goods;

    public Truck(ComputeWeight[] goods) {
        this.goods = goods;
    }

    public double getTotalWeight() {
        double total = 0;
        for(ComputeWeight good : goods) {
            total += good.computeWeight();
        }
        return total;
    }
}

