package fn.q_6;

/**
 * author 2107090411 刘敬超
 * version 1.0.0
 **/

/* 请在这里填写答案 */

class Car{
    public void move()    {
        System.out.println("running");
    }
}
class Plane{
    public void move()    {
        System.out.println("flying");
    }
}

interface Weapon {
    void shoot();
}

class Tank extends Car implements Weapon {
    @Override
    public void shoot() {
        System.out.println("发射大炮");
    }
}

class Fighter extends Plane implements Weapon {
    @Override
    public void shoot() {
        System.out.println("发射火箭");
    }
}

public class Main{
    public static void main(String argv[]){
        Tank tank = new Tank();
        Fighter fighter = new Fighter();
        tank.move();
        tank.shoot();
        fighter.move();
        fighter.shoot();
        Weapon tank2 = new Tank();
        Weapon fighter2= new Fighter();
        tank2.shoot();
        fighter2.shoot();
    }
}

