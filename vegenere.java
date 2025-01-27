import java.util.Scanner;

public class vegenere {
    static void keyGenerator(String plaintext, int[] key, String keyword){
        for(int i=0; i<plaintext.length(); i++){
            key[i] = keyword.charAt(i%keyword.length())%65;
        }
    }

    static String encoder(String plaintext, int[] key, String ciphertext){
        for(int i=0; i<plaintext.length(); i++){
            ciphertext += (char)(65 + (((int)plaintext.charAt(i))%65 + key[i])%26);
        }
        return ciphertext;
    }

    static String decoder(String ciphertext, int[] key, String plaintext){
        for(int i=0; i<ciphertext.length(); i++){
            plaintext += (char)(65 + (((int)ciphertext.charAt(i))%65 - key[i] + 26)%26);
        }
        return plaintext;
    }

    static void vegenereCipher(String plaintext, String keyword){
        int[] key = new int[plaintext.length()];
        String ciphertext = "";
        keyGenerator(plaintext, key, keyword);
        encoder(plaintext, key, ciphertext);
        System.out.println("Encoded: " + encoder(plaintext, key, ciphertext));
        System.out.println("Decoded: " + decoder(ciphertext, key, plaintext));

    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter plaintext: ");
        String plaintext = sc.nextLine();
        String keyword = "METAPHOR";
        vegenereCipher(plaintext, keyword);
        sc.close();
    }
}
