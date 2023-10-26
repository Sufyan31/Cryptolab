import java.util.Scanner;

public class AffineCipherCryptanalysis {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ciphertext: ");
        String ciphertext = scanner.nextLine();
        for (int a = 1; a < 26; a++) {
            if (gcd(a, 26) == 1) {
                for (int b = 0; b < 26; b++) {
                    String plaintext = decryptAffineCipher(ciphertext, a, b);
                    System.out.println("a = " + a + ", b = " + b + ": " + plaintext);
                }
            }
        }
        scanner.close();
    }

    public static String decryptAffineCipher(String ciphertext, int a, int b) {
        StringBuilder plaintext = new StringBuilder();
        for (char character : ciphertext.toCharArray()) {
            if (Character.isLetter(character)) {
                boolean isUpperCase = Character.isUpperCase(character);
                char lowerCaseChar = Character.toLowerCase(character);
                int charCode = ((modInverse(a) * (lowerCaseChar - 'a' - b)) % 26 + 26) % 26;
                charCode += 'a';
                if (isUpperCase) {
                    plaintext.append((char) (charCode - 32));
                } else {
                    plaintext.append((char) charCode);
                }
            } else {
                plaintext.append(character);
            }
        }
        return plaintext.toString();
    }

    public static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    public static int modInverse(int r2) {
        if (gcd(26, r2) == 1) {
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
        }
        return -1;
    }
}
