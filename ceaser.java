import java.util.Scanner;

class Ceaser{
    int shift;
    char start = 'a', end = 'z';
    int starting = start, ending = end;

    // Contructor for encoding class

    Ceaser(int shift){
        this.shift = shift;
    }

    // Logic to encode a character

    private char encode(int current){
        if (current < starting || current > ending){
            return (char)current;
        } else {
            if(current + shift > ending){
                current = starting + ((current + shift) - ending) - 1;
            } else {
                current = current + shift;
            }
            return (char)current;
        }
        
    }

    // Using encode logic to return ciphertext as a String

    String encoder(String plaintext){
        int length = plaintext.length();
        int current;
        char encoded;
        StringBuilder ciphertext = new StringBuilder("");
        for (int i=0; i<length; i++){
            current = plaintext.charAt(i);
            encoded = encode(current);
            ciphertext.append(encoded);
        }
        return ciphertext.toString();
    }

    // Logic to decode a character

    private char decode(int current){
        if (current < starting || current > ending){
            return (char)current;
        } else {
            if (current - shift < starting){
                current = ending - (starting - (current - shift)) + 1;
            } else {
                current = current - shift;
            }
        }
        return (char)current;
    }

    // Using decode logic to return plaintext as a String

    String decoder(String ciphertext){
        int length = ciphertext.length();
        int current;
        char decoded;
        StringBuilder plaintext = new StringBuilder("");
        for (int i=0; i<length; i++){
            current = ciphertext.charAt(i);
            decoded = decode(current);
            plaintext.append(decoded);
        }
        return plaintext.toString();
    }

    // Just for testing purposes

    String multipleEncode(String message, int rep){
        StringBuffer test1 = new StringBuffer(message);
        for (int i=0; i<rep; i++){
            test1.replace(0, test1.length(), encoder(test1.toString()));
        }
        return test1.toString();
    }

    // Just for testing purposes

    String multipleDecode(String message, int rep){
        StringBuffer test1 = new StringBuffer(message);
        for (int i=0; i<rep; i++){
            test1.replace(0, test1.length(), decoder(test1.toString()));
        }
        return test1.toString();
    }

}



class RunCaeser {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter plaintext: ");
        String plaintext = sc.nextLine();
        Ceaser cipherer = new Ceaser(5);
        String ciphertext = cipherer.encoder(plaintext);
        System.out.println(ciphertext);
        String decodedtext = cipherer.decoder(ciphertext);
        System.out.println(decodedtext);
        System.out.println("\n\nNow to test the encoding and decoding loop\n");
        String quack= cipherer.multipleEncode(decodedtext, 3);
        System.out.println(quack);
        System.out.println(cipherer.multipleDecode(quack, 3));
        sc.close();
    }
}


// char inp = 'a';
// int mid = inp;
// char out = (char) mid;
// System.out.println(out);

