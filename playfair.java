import java.util.*;

class playfair {

    private char[][] keyMatrix;

    public playfair(String key) {
        keyMatrix = generateKeyMatrix(key);
    }

    private char[][] generateKeyMatrix(String key) {
        key = key.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
        Set<Character> seen = new LinkedHashSet<>();

        for (char c : key.toCharArray()) {
            seen.add(c);
        }
        
        for (char c = 'A'; c <= 'Z'; c++) {
            if (c != 'J') {
                seen.add(c);
            }
        }

        char[][] matrix = new char[5][5];
        Iterator<Character> iterator = seen.iterator();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = iterator.next();
            }
        }
        return matrix;
    }

    private String preprocessText(String text, boolean forEncryption) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");

        if (forEncryption) {
            StringBuilder processedText = new StringBuilder();
            for (int i = 0; i < text.length(); i++) {
                char current = text.charAt(i);
                processedText.append(current);
                if (i < text.length() - 1 && text.charAt(i) == text.charAt(i + 1)) {
                    processedText.append('X');
                }
            }
            if (processedText.length() % 2 != 0) {
                processedText.append('X');
            }
            return processedText.toString();
        }
        return text;
    }

    private int[] findPosition(char c) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (keyMatrix[i][j] == c) {
                    return new int[] { i, j };
                }
            }
        }
        return null;
    }

    private String processDigraphs(String text, boolean encrypt) {
        StringBuilder result = new StringBuilder();
        int shift = encrypt ? 1 : -1;

        for (int i = 0; i < text.length(); i += 2) {
            char a = text.charAt(i);
            char b = text.charAt(i + 1);

            int[] posA = findPosition(a);
            int[] posB = findPosition(b);

            if (posA[0] == posB[0]) { 
                result.append(keyMatrix[posA[0]][(posA[1] + shift + 5) % 5]);
                result.append(keyMatrix[posB[0]][(posB[1] + shift + 5) % 5]);
            } else if (posA[1] == posB[1]) { 
                result.append(keyMatrix[(posA[0] + shift + 5) % 5][posA[1]]);
                result.append(keyMatrix[(posB[0] + shift + 5) % 5][posB[1]]);
            } else { 
                result.append(keyMatrix[posA[0]][posB[1]]);
                result.append(keyMatrix[posB[0]][posA[1]]);
            }
        }
        return result.toString();
    }

    public String encrypt(String plaintext) {
        plaintext = preprocessText(plaintext, true);
        return processDigraphs(plaintext, true);
    }

    public String decrypt(String ciphertext) {
        ciphertext = preprocessText(ciphertext, false);
        return processDigraphs(ciphertext, false);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the key: ");
        String key = scanner.nextLine();
        playfair cipher = new playfair(key);
        System.out.print("Enter the plaintext: ");
        String plaintext = scanner.nextLine();
        String encrypted = cipher.encrypt(plaintext);
        System.out.println("Encrypted text: " + encrypted);
        String decrypted = cipher.decrypt(encrypted);
        System.out.println("Decrypted text: " + decrypted);

        scanner.close();
    }
}
