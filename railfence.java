import java.util.Scanner;

public class railfence {
    public static String encrypt(String text, int numRails) {
        if (numRails == 1) {
            return text;
        }

        StringBuilder[] rails = new StringBuilder[numRails];
        for (int i = 0; i < numRails; i++) {
            rails[i] = new StringBuilder();
        }

        int rail = 0;
        boolean directionDown = true;

        for (char ch : text.toCharArray()) {
            rails[rail].append(ch);

            if (rail == 0) {
                directionDown = true;
            } else if (rail == numRails - 1) {
                directionDown = false;
            }

            rail += directionDown ? 1 : -1;
        }

        StringBuilder result = new StringBuilder();
        for (StringBuilder sb : rails) {
            result.append(sb);
        }

        return result.toString();
    }

    public static String decrypt(String cipher, int numRails) {
        if (numRails == 1) {
            return cipher;
        }

        int length = cipher.length();
        char[] decrypted = new char[length];

        boolean[] marker = new boolean[length];
        int rail = 0;
        boolean directionDown = true;
        
        for (int i = 0; i < length; i++) {
            marker[i] = false;

            if (rail == 0) {
                directionDown = true;
            } else if (rail == numRails - 1) {
                directionDown = false;
            }

            marker[i] = (rail == 0 || rail == numRails - 1 || directionDown);
            rail += directionDown ? 1 : -1;
        }

        int index = 0;
        for (int r = 0; r < numRails; r++) {
            rail = 0;
            directionDown = true;

            for (int i = 0; i < length; i++) {
                if (rail == r) {
                    decrypted[i] = cipher.charAt(index++);
                }

                if (rail == 0) {
                    directionDown = true;
                } else if (rail == numRails - 1) {
                    directionDown = false;
                }

                rail += directionDown ? 1 : -1;
            }
        }

        return new String(decrypted);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter: ");
        String plaintext = sc.nextLine();
        int numRails = 3;
        String ciphertext = encrypt(plaintext, numRails);
        System.out.println("Ciphertext: " + ciphertext);
        String decryptedText = decrypt(ciphertext, numRails);
        System.out.println("Decrypted Text: " + decryptedText);
        sc.close();
    }
}
