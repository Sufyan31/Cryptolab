import java.util.Scanner;
public class TranspositionCipher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter text to be encrypted: ");
        String plaintext = scanner.nextLine();

        System.out.print("Enter the encryption key (a string): ");
        String key = scanner.nextLine();

        String encryptedText = encrypt(plaintext, key);
        System.out.println("Encrypted text: " + encryptedText);

        String decryptedText = decrypt(encryptedText, key);
        System.out.println("Decrypted text: " + decryptedText);
    }

    public static String encrypt(String plaintext, String key) {
        int keyLength = key.length();
        int textLength = plaintext.length();
        int numRows = (int) Math.ceil((double) textLength / keyLength);

        char[][] grid = new char[numRows][keyLength];

        int index = 0;

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < keyLength; col++) {
                if (index < textLength) {
                    grid[row][col] = plaintext.charAt(index);
                    index++;
                } else {
                    grid[row][col] = ' ';
                }
            }
        }

        StringBuilder encryptedText = new StringBuilder();

        for (int col = 0; col < keyLength; col++) {
            int currentCol = key.indexOf(key.charAt(col));
            for (int row = 0; row < numRows; row++) {
                encryptedText.append(grid[row][currentCol]);
            }
        }

        return encryptedText.toString();
    }

    public static String decrypt(String ciphertext, String key) {
        int keyLength = key.length();
        int textLength = ciphertext.length();
        int numRows = (int) Math.ceil((double) textLength / keyLength);

        char[][] grid = new char[numRows][keyLength];

        int index = 0;

        for (int col = 0; col < keyLength; col++) {
            int currentCol = key.indexOf(key.charAt(col));
            for (int row = 0; row < numRows; row++) {
                grid[row][currentCol] = ciphertext.charAt(index);
                index++;
            }
        }

        StringBuilder decryptedText = new StringBuilder();

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < keyLength; col++) {
                decryptedText.append(grid[row][col]);
            }
        }

        return decryptedText.toString();
    }
}
