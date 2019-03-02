package p05_BorderControl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try(Scanner sc = new Scanner(System.in)) {
            String currentLine = null;

            List<Inhabitant> entryList = new ArrayList<>();
            String[] inTokens;
            while(!(currentLine = sc.nextLine()).equals("End")){
                inTokens = currentLine.split("\\s");
                if (inTokens.length == 3) {
                    entryList.add(new Citizen(
                            inTokens[0],
                            Integer.parseInt(inTokens[1]),
                            inTokens[2]));
                } else if (inTokens.length == 2) {
                    entryList.add(new Robot(inTokens[0], inTokens[1]));
                } else {
                    throw new IllegalArgumentException();
                }
            }
            String fakeIdEnding = sc.nextLine();

            for (Inhabitant inhabitant : entryList) {
                if(inhabitant.getId().endsWith(fakeIdEnding)) {
                    System.out.println(inhabitant.getId());
                }
            }
        }
    }
}
