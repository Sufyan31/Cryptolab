import static java.lang.Character.isUpperCase;
import java.util.Scanner;

public class AffineCipher {
    public static void encodeMessage(int a, int b, String secretText) {
        String encodedMessage = "";
        if (findMultiplicativeInverse(a) == -1) {
            System.out.println("This Affine Cipher is a no-go, Agent!");
            return;
        }
        for (int i = 0; i < secretText.length(); i++) {
            int ascii;
            if (isUpperCase(secretText.charAt(i))) {
                ascii = secretText.charAt(i) - 65;
            } else {
                ascii = secretText.charAt(i) - 97;
            }
            int encryptedChar = (((a * ascii) + b) % 26);
            if (isUpperCase(secretText.charAt(i))) {
                encodedMessage = encodedMessage + (char)(encryptedChar + 65);
            } else {
                encodedMessage = encodedMessage + (char)(encryptedChar + 97);
            }
        }
        System.out.println("Encoded message: " + encodedMessage+"\n");
        decodeMessage(a,b,encodedMessage);
    }

    public static void decodeMessage(int a, int b, String secretText) {
        String decodedMessage = "";
        for (int i = 0; i < secretText.length(); i++) {
            int ascii;
            if (isUpperCase(secretText.charAt(i))) {
                ascii = secretText.charAt(i) - 65;
            } else {
                ascii = secretText.charAt(i) - 97;
            }
            if (findMultiplicativeInverse(a) == -1) {
                System.out.println("This Affine Cipher is a no-go, Agent!");
                return;
            }
            int decryptedChar = (findMultiplicativeInverse(a) * (ascii - b)) % 26;
            if (decryptedChar < 0) {
                decryptedChar = decryptedChar + 26;
            }
            if (isUpperCase(secretText.charAt(i))) {
                decodedMessage = decodedMessage + (char)(decryptedChar + 65);
            } else {
                decodedMessage = decodedMessage + (char)(decryptedChar + 97);
            }
        }
        System.out.println("\nDecoded message: " + decodedMessage);
    }

    public static int findMultiplicativeInverse(int r2) {
        if (calculateGCD(26, r2) == 1) {
            int r1 = 26;
            int t1 = 0;
            int t2 = 1;
            int q, r, t;
            while (r2 != 0) {
                q = r1 / r2;
                r = r1 % r2;
                t = t1 - (q * t2);
                r1 = r2;
                r2 = r;
                t1 = t2;
                t2 = t;
            }
            if (t1 < 0) {
                return t1 + 26;
            } else {
                return t1;
            }
        } else {
            return -1;
        }
    }

    public static int calculateGCD(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        if (a == b) {
            return a;
        }
        if (a > b) {
            return calculateGCD(a - b, b);
        } else {
            return calculateGCD(a, b - a);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a,b;
        String message;
        System.out.println("\nEnter a and b:\n");
        a = scanner.nextInt();
        b = scanner.nextInt();
        System.out.println("Enter the secret message:");
        message = scanner.next();
        encodeMessage(a, b, message);
        }
    
}
