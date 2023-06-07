import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int c = sc.nextInt();
        Vehicle[] vs = new Vehicle[c];
        for (int i=0;i<c;i++) {
            int type = sc.nextInt();
            Vehicle v = null;
            if (type == 1) {//货车
                vs[i] = new Truck (sc.nextDouble());
            } else if (type == 2) {
                vs[i] = new Keche(sc.nextInt());
            } else if (type == 3) {
                vs[i] = new Car(sc.nextInt(), sc.nextInt());
            }
        }

        System.out.printf("%.2f",CarRentCompany.rentVehicles(vs));

    }
}

/* 你的代码被嵌在这里 */
class Vehicle {

}

class Truck extends Vehicle{
    public double load;
    public Truck(double load) {
        this.load = load;
    }
}

class Keche extends Vehicle{
    public int seats;
    public Keche(int seats) {
        this.seats = seats;
    }
}

class Car extends Vehicle{
    public int level;
    public int year;
    public Car(int level, int year) {
        this.level = level;
        this.year = year;
    }
}

class CarRentCompany {
    public static double rentVehicles(Vehicle[] vs) {
        double total = 0;
        for(Vehicle v : vs) {
            if(v instanceof Truck) {
                total += ((Truck) v).load * 1000;
            } else if(v instanceof Keche) {
                total += ((Keche) v).seats * 50;
            } else if(v instanceof Car) {
                total += 200 * ((Car) v).level / Math.sqrt(((Car) v).year);
            }
        }
        return total;
    }
}
