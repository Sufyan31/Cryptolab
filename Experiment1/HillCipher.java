import java.util.ArrayList;
import java.util.Scanner;

public class HillCipher {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the key (4 characters): ");
        String key = sc.nextLine();
        System.out.print("Enter the message to encrypt: ");
        String message = sc.nextLine();

        String encrypted = encrypt(message, key);
        System.out.println("Encrypted Message: " + encrypted);

        String decrypted = decrypt(encrypted, key);
        System.out.println("Decrypted Message: " + decrypted);
    }

    private static int[][] getKeyMatrix(String key) {
        key = key.replaceAll("[^a-zA-Z]", "").toUpperCase();
        if (key.length() != 4) {
            throw new IllegalArgumentException("Key must be exactly 4 characters long.");
        }

        int[][] keyMatrix = new int[2][2];
        int k = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                keyMatrix[i][j] = key.charAt(k) - 'A';
                k++;
            }
        }
        return keyMatrix;
    }

    private static int[][] getInverseKeyMatrix(int[][] keyMatrix) {
        int det = keyMatrix[0][0] * keyMatrix[1][1] - keyMatrix[0][1] * keyMatrix[1][0];
        int detMod26 = ((det % 26) + 26) % 26;
        int modInverseDet = -1;

        for (int i = 1; i < 26; i++) {
            if ((detMod26 * i) % 26 == 1) {
                modInverseDet = i;
                break;
            }
        }

        int[][] inverseKeyMatrix = new int[2][2];
        inverseKeyMatrix[0][0] = (keyMatrix[1][1] * modInverseDet) % 26;
        inverseKeyMatrix[0][1] = ((26 - keyMatrix[0][1]) * modInverseDet) % 26;
        inverseKeyMatrix[1][0] = ((26 - keyMatrix[1][0]) * modInverseDet) % 26;
        inverseKeyMatrix[1][1] = (keyMatrix[0][0] * modInverseDet) % 26;

        return inverseKeyMatrix;
    }

    private static void isValidMatrix(int[][] keyMatrix) {
        int det = keyMatrix[0][0] * keyMatrix[1][1] - keyMatrix[0][1] * keyMatrix[1][0];
        if (det == 0) {
            throw new IllegalArgumentException("The key matrix is not invertible.");
        }
    }

    public static String encrypt(String message, String key) {
        key = key.replaceAll("[^a-zA-Z]", "").toUpperCase();
        if (key.length() != 4) {
            throw new IllegalArgumentException("Key must be exactly 4 characters long.");
        }

        int[][] keyMatrix = getKeyMatrix(key);
        isValidMatrix(keyMatrix);

        message = message.replaceAll("[^a-zA-Z]", "").toUpperCase();
        while (message.length() % 2 != 0) {
            message += "X"; // Padding with 'X' to make it even
        }

        ArrayList<Integer> encryptedNumbers = new ArrayList<>();
        int len = message.length();

        for (int i = 0; i < len; i += 2) {
            int char1 = message.charAt(i) - 'A';
            int char2 = message.charAt(i + 1) - 'A';

            int result1 = (keyMatrix[0][0] * char1 + keyMatrix[0][1] * char2) % 26;
            int result2 = (keyMatrix[1][0] * char1 + keyMatrix[1][1] * char2) % 26;

            encryptedNumbers.add(result1);
            encryptedNumbers.add(result2);
        }

        StringBuilder encryptedMessage = new StringBuilder();

        for (int num : encryptedNumbers) {
            char ch = (char) (num + 'A');
            encryptedMessage.append(ch);
        }

        return encryptedMessage.toString();
    }

    public static String decrypt(String message, String key) {
        key = key.replaceAll("[^a-zA-Z]", "").toUpperCase();
        if (key.length() != 4) {
            throw new IllegalArgumentException("Key must be exactly 4 characters long.");
        }

        int[][] keyMatrix = getKeyMatrix(key);
        isValidMatrix(keyMatrix);

        int[][] inverseKeyMatrix = getInverseKeyMatrix(keyMatrix);

        message = message.replaceAll("[^a-zA-Z]", "").toUpperCase();
        ArrayList<Integer> decryptedNumbers = new ArrayList<>();
        int len = message.length();

        for (int i = 0; i < len; i += 2) {
            int char1 = message.charAt(i) - 'A';
            int char2 = message.charAt(i + 1) - 'A';

            int result1 = (inverseKeyMatrix[0][0] * char1 + inverseKeyMatrix[0][1] * char2) % 26;
            int result2 = (inverseKeyMatrix[1][0] * char1 + inverseKeyMatrix[1][1] * char2) % 26;

            decryptedNumbers.add(result1);
            decryptedNumbers.add(result2);
        }

        StringBuilder decryptedMessage = new StringBuilder();

        for (int num : decryptedNumbers) {
            char ch = (char) (num + 'A');
            decryptedMessage.append(ch);
        }

        return decryptedMessage.toString();
    }
}
