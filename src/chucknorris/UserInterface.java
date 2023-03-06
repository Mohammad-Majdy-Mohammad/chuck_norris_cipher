package chucknorris;

import java.util.Scanner;

public class UserInterface {

    public static void run() {

        final Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("Please input operation (encode/decode/exit): ");
            String operation = scanner.nextLine();

            if (operation.equalsIgnoreCase("exit")) {
                System.out.println("Bye!");
                break;
            }

            ChuckNorrisCipher cipher = new ChuckNorrisCipher(operation);


            if (cipher.isValidOperation()) {
                if (operation.equalsIgnoreCase("encode")) {
                    System.out.println("Input string:");
                    String encoded = cipher.generateResult(scanner.nextLine());
                    System.out.println("Encoded string: \n" + encoded);
                } else {
                    System.out.println("Input encoded string:");
                    String decoded = cipher.generateResult(scanner.nextLine());
                    if (decoded.equals("NotValid!")) {
                        continue;
                    }
                    System.out.println("Decoded string: \n" + decoded);
                }
            }

        }

    }

}
