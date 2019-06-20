package p04_Telephony;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            Smartphone smartphone = new Smartphone("p04_Telephony.Smartphone");
            String[] numbersToCall = sc.nextLine().split("\\s+");
            String[] sitesToVisit = sc.nextLine().split("\\s+");

            for (String number : numbersToCall) {
                System.out.println(smartphone.call(number));
            }

            for (String site : sitesToVisit) {
                System.out.println(smartphone.browse(site));
            }
        }
    }
}
