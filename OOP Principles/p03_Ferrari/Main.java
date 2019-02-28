package p03_Ferrari;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try(Scanner sc = new Scanner(System.in)) {
            String driverName = sc.nextLine();
            Ferrari ferrari = new Ferrari(driverName);
            System.out.printf("%s/%s/%s/%s",
                    ferrari.getModel(),
                    ferrari.pushBrakePedal(),
                    ferrari.pushGasPedal(),
                    ferrari.getDriverName());
        }
    }
}
