import java.util.Scanner;

public class ShiftCipher {
    String encryptMessage(String message, int shift) {
        String result = "";
        for (char ch : message.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                char encryptedCh = (char) ((ch - base + shift) % 26 + base);
                result += encryptedCh;
            } else {
                result += ch;
            }
        }
        return result;
    }

    String decryptMessage(String message, int shift) {
        String result = "";
        for (char ch : message.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                char decryptedCh = (char) ((ch - base - shift + 26) % 26 + base);
                result += decryptedCh;
            } else {
                result += ch;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoolShiftCipher cipher = new CoolShiftCipher();

        System.out.println("Yo, give me some text to encrypt: ");
        String plaintext = scanner.next();

        System.out.println("How much shift do you want? ");
        int shift = scanner.nextInt();

        String encryptedText = cipher.encryptMessage(plaintext, shift);

        System.out.println("\nHere's your encrypted text: " + encryptedText);

        String decryptedText = cipher.decryptMessage(encryptedText, shift);
        System.out.println("\nAnd here's your decrypted text: " + decryptedText);
    }
}
