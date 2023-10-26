import java.util.ArrayList;
import java.util.Scanner;

public class HillCipher {
    private static int[][] getKeyMatrix() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the key matrix:");
        String key = sc.nextLine();
        double sq = Math.sqrt(key.length());
        if (sq != (long) sq) {
            System.out.println("Cannot form a square matrix");
        }
        int len = (int) sq;
        int[][] keyMatrix = new int[len][len];
        int k = 0;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                keyMatrix[i][j] = ((int) key.charAt(k)) - 97;
                k++;
            }
        }
        return keyMatrix;
    }

    private static void isValidMatrix(int[][] keyMatrix) {
        int det = keyMatrix[0][0] * keyMatrix[1][1] - keyMatrix[0][1] * keyMatrix[1][0];
        if (det == 0) {
            throw new java.lang.Error("Determinant equals zero, invalid key matrix!");
        }
    }

    private static void isValidReverseMatrix(int[][] keyMatrix, int[][] reverseMatrix) {
        int[][] product = new int[2][2];
        product[0][0] = (keyMatrix[0][0] * reverseMatrix[0][0] + keyMatrix[0][1] * reverseMatrix[1][0]) % 26;
        product[0][1] = (keyMatrix[0][0] * reverseMatrix[0][1] + keyMatrix[0][1] * reverseMatrix[1][1]) % 26;
        product[1][0] = (keyMatrix[1][0] * reverseMatrix[0][0] + keyMatrix[1][1] * reverseMatrix[1][0]) % 26;
        product[1][1] = (keyMatrix[1][0] * reverseMatrix[0][1] + keyMatrix[1][1] * reverseMatrix[1][1]) % 26;
        if (product[0][0] != 1 || product[0][1] != 0 || product[1][0] != 0 || product[1][1] != 1) {
            throw new java.lang.Error("Invalid reverse matrix found!");
        }
    }

    private static int[][] reverseMatrix(int[][] keyMatrix) {
        int detmod26 = (keyMatrix[0][0] * keyMatrix[1][1] - keyMatrix[0][1] * keyMatrix[1][0]) % 26;
        int factor;
        int[][] reverseMatrix = new int[2][2];
        for (factor = 1; factor < 26; factor++) {
            if ((detmod26 * factor) % 26 == 1) {
                break;
            }
        }
        reverseMatrix[0][0] = keyMatrix[1][1] * factor % 26;
        reverseMatrix[0][1] = (26 - keyMatrix[0][1]) * factor % 26;
        reverseMatrix[1][0] = (26 - keyMatrix[1][0]) * factor % 26;
        reverseMatrix[1][1] = keyMatrix[0][0] * factor % 26;
        return reverseMatrix;
    }

    private static void echoResult(String label, int adder, ArrayList<Integer> phrase) {
        System.out.print(label + " ");
        for (int i = 0; i < phrase.size(); i++) {
            System.out.print(phrase.get(i));
            if (i < phrase.size() - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    public static void encrypt(String phrase, boolean alphaZero) {
        int adder = alphaZero ? 1 : 0;
        int[][] keyMatrix = getKeyMatrix();
        isValidMatrix(keyMatrix);

        phrase = phrase.replaceAll("[^a-zA-Z]", "").toUpperCase();
        if (phrase.length() % 2 == 1) {
            phrase += "Q";
        }

        ArrayList<Integer> phraseToNum = new ArrayList<>();
        ArrayList<Integer> phraseEncoded = new ArrayList<>();

        for (int i = 0; i < phrase.length(); i++) {
            phraseToNum.add(phrase.charAt(i) - (64 + adder));
        }

        for (int i = 0; i < phraseToNum.size(); i += 2) {
            int x = (keyMatrix[0][0] * phraseToNum.get(i) + keyMatrix[0][1] * phraseToNum.get(i + 1)) % 26;
            int y = (keyMatrix[1][0] * phraseToNum.get(i) + keyMatrix[1][1] * phraseToNum.get(i + 1)) % 26;
            phraseEncoded.add(alphaZero ? x : (x == 0 ? 26 : x));
            phraseEncoded.add(alphaZero ? y : (y == 0 ? 26 : y));
        }

        echoResult("Encrypted phrase:", adder, phraseEncoded);
    }

    public static void decrypt(String phrase, boolean alphaZero) {
        int adder = alphaZero ? 1 : 0;
        int[][] keyMatrix = getKeyMatrix();
        isValidMatrix(keyMatrix);

        phrase = phrase.replaceAll("[^a-zA-Z]", "").toUpperCase();
        ArrayList<Integer> phraseToNum = new ArrayList<>();
        ArrayList<Integer> phraseDecoded = new ArrayList<>();

        for (int i = 0; i < phrase.length(); i++) {
            phraseToNum.add(phrase.charAt(i) - (64 + adder));
        }

        int[][] revKeyMatrix = reverseMatrix(keyMatrix);
        isValidReverseMatrix(keyMatrix, revKeyMatrix);

        for (int i = 0; i < phraseToNum.size(); i += 2) {
            phraseDecoded.add((revKeyMatrix[0][0] * phraseToNum.get(i) + revKeyMatrix[0][1] * phraseToNum.get(i + 1)) % 26);
            phraseDecoded.add((revKeyMatrix[1][0] * phraseToNum.get(i) + revKeyMatrix[1][1] * phraseToNum.get(i + 1)) % 26);
        }

        echoResult("Decrypted phrase:", adder, phraseDecoded);
    }

    public static void main(String[] args) {
        String opt, phrase;
        Scanner sc = new Scanner(System.in);
        System.out.println("Hill Cipher Implementation (2x2)");
        System.out.println("-------------------------");
        System.out.println("1. Encrypt text");
        System.out.println("2. Decrypt text");
        System.out.println();
        System.out.println("Choose an option or enter any other character to exit");
        System.out.println();
        System.out.print("Select your choice: ");
        opt = sc.nextLine();
        switch (opt) {
            case "1":
                System.out.print("Enter the phrase to encrypt: ");
                phrase = sc.nextLine();
                encrypt(phrase, true);
                break;
            case "2":
                System.out.print("Enter the phrase to decrypt: ");
                phrase = sc.nextLine();
                decrypt(phrase, true);
                break;
        }
    }
}
